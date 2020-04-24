package server.drivers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import server.main.ServiceLocator;

public class DriverDaoMySqlImpl implements DriverDao{
	DataSource dataSource;

	public DriverDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public int update(Driver driver) {
		int count = 0;
		String sql ="UPDATE Driver SET driver_status = ?" 
				+ " WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver.getDriver_status());
			ps.setInt(2, driver.getDriver_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	@Override
	public int locationupdate(Driver driver) {
		int count = 0;
		String sql ="UPDATE Driver SET driver_longitude = ?, driver_latitude = ? " 
				+ " WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setDouble(1, driver.getDriver_longitude());
			ps.setDouble(2, driver.getDriver_latitude());
			ps.setInt(3, driver.getDriver_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	@Override
	public byte[] getImage(int driver_id) {
		String sql = "SELECT driver_photo FROM Driver WHERE driver_id = ?;";
		byte[] image = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
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
	public Driver getLocation(int driver_id) {
		String sql = "SELECT driver_longitude, driver_latitude FROM Driver WHERE driver_id = ?;";
		Driver driver = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				double driver_longitude = rs.getDouble(1);
				double driver_latitude = rs.getDouble(2);
				driver = new Driver(driver_id, driver_longitude, driver_latitude);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return driver;
	}
	
	@Override
	public Driver findById(int driver_id) {
		String sql = "SELECT driver_name, driver_phone, driver_status FROM Driver WHERE driver_id = ?;";
		Driver driver = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String driver_name = rs.getString(1);
				String driver_phone = rs.getString(2);
				int driver_status = rs.getInt(3);
				driver = new Driver(driver_id, driver_name, driver_phone, driver_status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return driver;
	}
	
	@Override
	public List<Driver> getAll() {
		String sql = "SELECT driver_id, driver_name, driver_phone, driver_status " 
				+ "FROM Driver;";
		List<Driver> driverList = new ArrayList<Driver>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int driver_id = rs.getInt(1);
				String driver_name = rs.getString(2);
				String driver_phone = rs.getString(3);
				int driver_status = rs.getInt(4);
				Driver driver = new Driver(driver_id, driver_name, driver_phone, driver_status);
				driverList.add(driver);
			}
			return driverList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return driverList;
	}
}
