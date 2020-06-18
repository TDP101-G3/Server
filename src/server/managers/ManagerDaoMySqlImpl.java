package server.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.google.gson.JsonObject;

import server.customers.Customer;
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
				String driver_name = rs.getString(3);
				String driver_phone = rs.getString(4);
				String driver_email = rs.getString(5);
				Driver driver = new Driver(driver_id, driver_status, driver_name, driver_phone, driver_email);
				drivers.add(driver);
			}
			return drivers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drivers;
	}
	
	@Override
	public List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT customer_id, customer_account_status, customer_name, customer_phone, customer_email FROM Customer;";
		try(Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt(1);
				int customer_status = rs.getInt(2);
				String customer_name = rs.getString(3);
				String customer_phone = rs.getString(4);
				String customer_email = rs.getString(5);
				Customer customer = new Customer(customer_id, customer_status, customer_name, customer_phone, customer_email);
				customers.add(customer);
			}
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	@Override
	public byte[] getImage(String role, int id) {
		String sql = "";
		if (role.equals("driver")) {
			sql = "SELECT driver_photo FROM Driver WHERE driver_id = ?;";
		} else if (role.equals("customer")) {
			sql = "SELECT customer_photo FROM Customer WHERE customer_id = ?;";
		}
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
	public byte[] getImageFile(String role, int id, String fileName) {
		String sql = "";
		if (role.equals("driver")) {
			if (fileName.equals("driver_identify_front")) {
				sql = "SELECT driver_identify_front FROM Driver WHERE driver_id = ?;";
			} else if (fileName.equals("driver_identify_back")) {
				sql = "SELECT driver_identify_back FROM Driver WHERE driver_id = ?;";
			} else if (fileName.equals("driver_license_front")) {
				sql = "SELECT driver_license_front FROM Driver WHERE driver_id = ?;";
			} else if (fileName.equals("driver_license_back")) {
				sql = "SELECT driver_license_back FROM Driver WHERE driver_id = ?;";
			} else if (fileName.equals("driver_liability_insurance")) {
				sql = "SELECT driver_liability_insurance FROM Driver WHERE driver_id = ?;";
			}
		} else if (role.equals("customer")) {
			if (fileName.equals("customer_identify_front")) {
				sql = "SELECT customer_identify_front FROM Customer WHERE customer_id = ?;";
			} else if (fileName.equals("customer_identify_back")) {
				sql = "SELECT customer_identify_back FROM Customer WHERE customer_id = ?;";
			} else if (fileName.equals("customer_car_insurance")) {
				sql = "SELECT customer_car_insurance FROM Customer WHERE customer_id = ?;";
			} else if (fileName.equals("customer_compulsory_insurance")) {
				sql = "SELECT customer_compulsory_insurance FROM Customer WHERE customer_id = ?;";
			} else if (fileName.equals("customer_third_insurance")) {
				sql = "SELECT customer_third_insurance FROM Customer WHERE customer_id = ?;";
			}
		}
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

	@Override
	public int updateUserPhoto(int id, String role, byte[] userPhoto) {
		int count = 0;
		String sql = "";
		if (role.equals("driver")) {
			sql = "UPDATE Driver SET driver_photo = ? WHERE driver_id = ?;";
		} else {
			sql = "UPDATE Customer SET customer_photo = ? WHERE customer_id = ?;";
		}
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setBytes(1, userPhoto);
			ps.setInt(2, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteUserPhoto(int id, String role) {
		int count = 0;
		String sql = "";
		if (role.equals("driver")) {
			sql = "UPDATE Driver SET driver_photo = null WHERE driver_id = ?;";
		} else {
			sql = "UPDATE Customer SET customer_photo = null WHERE customer_id = ?;";
		}
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateDriverData(Driver driver) {
		int count = 0;
		String sql = "";	
		sql = "UPDATE Driver SET driver_name = ?, driver_phone = ?, driver_email = ?, driver_account_status = ? WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, driver.getDriver_name());
			ps.setString(2, driver.getDriver_phone());
			ps.setString(3, driver.getDriver_email());
			ps.setInt(4, driver.getDriver_status());
			ps.setInt(5, driver.getDriver_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}

	@Override
	public int updateCustomerData(Customer customer) {
		int count = 0;
		String sql = "";	
		sql = "UPDATE Customer SET customer_name = ?, customer_phone = ?, customer_email = ?, customer_account_status = ? WHERE customer_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, customer.getCustomer_name());
			ps.setString(2, customer.getCustomer_phone());
			ps.setString(3, customer.getCustomer_email());
			ps.setInt(4, customer.getCustomer_status());
			ps.setInt(5, customer.getCustomer_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}

	

	
}
