package server.customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import server.main.ServiceLocator;

public class CustomerDaoMySqlImpl implements CustomerDao{
	DataSource dataSource;

	public CustomerDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public byte[] getImage(int customer_id) {
		String sql = "SELECT customer_photo FROM Customer WHERE customer_id = ?;";
		byte[] image = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, customer_id);
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
	public Customer findById(int customer_id) {
		String sql = "SELECT customer_name, customer_phone, customer_number_plate, customer_car_model, customer_car_color FROM Customer WHERE customer_id = ?;";
		Customer customer = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, customer_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String customer_name = rs.getString(1);
				String customer_phone = rs.getString(2);
				String customer_number_plate = rs.getString(3);
				String customer_car_model = rs.getString(4);
				String customer_car_color = rs.getString(5);
				customer = new Customer(customer_id, customer_name, customer_phone, customer_number_plate, customer_car_model, customer_car_color);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return customer;
	}
	
	@Override
	public List<Customer> getAll() {
		String sql = "SELECT customer_id, customer_name, customer_phone, customer_number_plate, customer_car_model, customer_car_color " 
				+ "FROM Customer;";
		List<Customer> customerList = new ArrayList<Customer>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt(1);
				String customer_name = rs.getString(2);
				String customer_phone = rs.getString(3);
				String customer_number_plate = rs.getString(4);
				String customer_car_model = rs.getString(5);
				String customer_car_color = rs.getString(6);
				Customer customer = new Customer(customer_id, customer_name, customer_phone, customer_number_plate, customer_car_model, customer_car_color);
				customerList.add(customer);
			}
			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return customerList;
	}
	
	@Override
	public int matchDriver(double startLatitude,double startLongitude) {
		double a = 0,b=0,c=1000000;
		int driver_id=0;
		String sql = "SELECT driver_id, driver_longitude, driver_latitude " 
				+ "FROM Driver WHERE driver_status = 1;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				double driver_longitude = rs.getDouble(2);
				double driver_latitude = rs.getDouble(3);
				if(driver_longitude >= startLongitude) {
					a = driver_longitude - startLongitude;
				}
				if(driver_longitude < startLongitude) {
					a = startLongitude - driver_longitude;
				}
				if(driver_latitude >= startLongitude) {
					b = driver_latitude - startLongitude;
				}
				if(driver_latitude < startLongitude) {
					b = startLongitude - driver_latitude;
				}
				if(c>a+b) {
					c = a+b;
					driver_id = id;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return driver_id;
	}
}
