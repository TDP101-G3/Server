package server.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server.drivers.Driver;
import server.main.ServiceLocator;

public class ManagerDaoMySqlImpl implements ManagerDao {
	DataSource dataSource;

	public ManagerDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public int loginCheck(String manager_account, String manager_password) {
		int count = 0;
		String account = null;
		String password = null;
		String sql = "SELECT manager_account, manager_password " + "FROM Manager WHERE manager_account = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, manager_account);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				account = rs.getString(1);
				password = rs.getString(2);
				System.out.println("password");
				System.out.println(password);
				System.out.println(account);

			}
			if (account == null) {
				return count; // 未註冊
			}

			if (manager_account.equals(account)) {
				if (manager_password.equals(password)) {
					return count = 1; // 登入成功
				} else {
					System.out.println(password);
					System.out.println(manager_password);
					return count = 2; // 密碼錯誤
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count; // 系統錯誤
	}
	
	@Override
	public List<Driver> getDrivers() {
		List<Driver> drivers = new ArrayList<Driver>();
		String sql = "SELECT driver_id, driver_account_status, driver_name, driver_phone, driver_email FROM Driver;";
		try(Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int driver_id = rs.getInt(1);
				int driver_status = rs.getInt(2);
				//System.out.println("getDrivers driver_status: " + driver_status );
				String driver_name = rs.getString(3);
				String driver_phone = rs.getString(4);
				String driver_email = rs.getString(5);
				Driver driver = new Driver(driver_id, driver_status, driver_name, driver_phone, driver_email);
//				Gson gson = new Gson();
				//System.out.println("getDrivers driver id " + driver_id + ": " + gson.toJson(driver));
				drivers.add(driver);
			}
			return drivers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drivers;
	}
	
	@Override
	public byte[] getImage(int id) {
		String sql = "SELECT driver_photo FROM Driver WHERE driver_id = ?;";
		byte[] image = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public String getFilesStatus(int id) {
		String sql = "SELECT driver_identify_front, driver_identify_back, driver_license_front, driver_license_back, driver_liability_insurance FROM Driver WHERE driver_id = ?;";
		String[] keys = {"driver_identify_front", "driver_identify_back", "driver_license_front", "driver_license_back", "driver_liability_insurance"};
		JsonObject jsonObject = new JsonObject();
        
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				for(int i=1; i<=5; i++) {
					byte[] image = null;
					image = rs.getBytes(i);
					if(image==null) {
						jsonObject.addProperty(keys[i-1], 0);
					}else {
						jsonObject.addProperty(keys[i-1], 1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("getFilesStatus jsonObject: " + jsonObject.toString());
		return jsonObject.toString();
	}
}
