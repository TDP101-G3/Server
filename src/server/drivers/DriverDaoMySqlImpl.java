package server.drivers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import server.customers.Customer;
import server.customers.Insurance;
import server.main.ServiceLocator;
import server.orders.Order;

public class DriverDaoMySqlImpl implements DriverDao {
	DataSource dataSource;

	public DriverDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int update(Driver driver) {
		int count = 0;
		String sql = "UPDATE Driver SET driver_status = ?" + " WHERE driver_id = ?;";
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
		String sql = "UPDATE Driver SET driver_longitude = ?, driver_latitude = ? " + " WHERE driver_id = ?;";
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
			/*
			 * 當Statement關閉，ResultSet也會自動關閉， 可以不需要將ResultSet宣告置入try with
			 * resources小括號內，參看ResultSet說明
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
			/*
			 * 當Statement關閉，ResultSet也會自動關閉， 可以不需要將ResultSet宣告置入try with
			 * resources小括號內，參看ResultSet說明
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
	public Driver getUserInfo(String driver_email) {
		String sql = "SELECT driver_id, driver_name FROM Driver WHERE driver_email = ?;";
		Driver driver = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, driver_email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int driver_id = rs.getInt(1);
				String driver_name = rs.getString(2);
				driver = new Driver(driver_id, driver_name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return driver;
	}

	@Override
	public Driver getInformation(int driver_id) {
		String sql = "SELECT driver_name, driver_phone FROM Driver WHERE driver_id = ?;";
		Driver driver = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			/*
			 * 當Statement關閉，ResultSet也會自動關閉， 可以不需要將ResultSet宣告置入try with
			 * resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String driver_name = rs.getString(1);
				String driver_phone = rs.getString(2);
				driver = new Driver(driver_id, driver_name, driver_phone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return driver;
	}

	@Override
	public List<Driver> getAll() {
		String sql = "SELECT driver_id, driver_name, driver_phone, driver_status,driver_longitude,driver_latitude  "
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
				double driver_longitude = rs.getDouble(5);
				double driver_latitude = rs.getDouble(6);
				Driver driver = new Driver(driver_id, driver_name, driver_phone, driver_status, driver_longitude,
						driver_latitude);
				driverList.add(driver);
			}
			return driverList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return driverList;
	}

	@Override
	public int loginCheck(String driver_email, String driver_password) {
		int count = 0;
		String email = null;
		String password = null;
		String sql = "SELECT driver_email, driver_password " + "FROM Driver WHERE driver_email = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, driver_email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				email = rs.getString(1);
				password = rs.getString(2);
				System.out.println("password");
				System.out.println(password);
				System.out.println(email);

			}
			if (email == null) {
				return count; // 未註冊
			}

			if (driver_email.equals(email)) {
				if (driver_password.equals(password)) {
					return count = 1; // 登入成功
				} else {
					System.out.println(password);
					System.out.println(driver_password);
					return count = 2; // 密碼錯誤
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count; // 系統錯誤
	}

	@Override
	public int signUp(Driver driver, byte[] idFront, byte[] idBack, byte[] licenseFront, byte[] licenseBack,
			byte[] driverSecure) {
		int count = 0;
		String sql = "INSERT INTO Driver"
				+ "(driver_name, driver_email, driver_password, driver_phone,driver_bank_name, driver_bank_account, driver_bank_code, driver_identify_front, driver_identify_back, driver_license_front, driver_license_back,driver_liability_insurance) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection(); // 拿到連線池中的連線
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, driver.getDriver_name());
			ps.setString(2, driver.getDriver_email());
			ps.setString(3, driver.getDriver_password());
			ps.setString(4, driver.getDriver_phone());
			ps.setString(5, driver.getDriver_bank_name());
			ps.setString(6, driver.getDriver_bank_account());
			ps.setString(7, driver.getDriver_bank_code());
			ps.setBytes(8, idFront);
			ps.setBytes(9, idBack);
			ps.setBytes(10, licenseFront);
			ps.setBytes(11, licenseBack);
			ps.setBytes(12, driverSecure);

			count = ps.executeUpdate();// 異動結果
			System.out.println("count: " + count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<Order> getOrders(int driver_id) {
		String sql = "SELECT order_id, customer_id, order_time, order_start, order_end, driver_score, customer_score, order_money " 
				+ "FROM Order_detail WHERE driver_id = ?;";
		List<Order> orderList = new ArrayList<Order>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int order_id = rs.getInt(1);
				int customer_id = rs.getInt(2);
				String order_time = rs.getString(3);
				String order_start = rs.getString(4);
				String order_end = rs.getString(5);
				double driver_score = rs.getDouble(6);
				double customer_score = rs.getDouble(7);
				double order_money = rs.getDouble(8);
				Order order = new Order(order_id, customer_id, driver_id, order_time, order_start, order_end, driver_score, customer_score, order_money);
				orderList.add(order);
			}
			return orderList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return orderList;
	}
	
	@Override
	public int updateUserData (Driver driver) {
		int count = 0;
		String sql = "";	
		sql = "UPDATE Driver SET driver_name = ?, driver_phone = ?, driver_email = ? WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, driver.getDriver_name());
			ps.setString(2, driver.getDriver_phone());
			ps.setString(3, driver.getDriver_email());
			ps.setInt(4, driver.getDriver_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	@Override
	public int updateUserPhoto (int driver_id, byte[] userPhoto) {
		int count = 0;
		String sql = "";
		sql = "UPDATE Driver SET driver_photo = ? WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setBytes(1, userPhoto);
			ps.setInt(2, driver_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int updateInsurance(int driver_id, byte[] insurancePhoto, String expireDate) {
		int count = 0;
		String sql = "";
		System.out.println("update insurance expireDate: " + expireDate);
		sql = "UPDATE Driver SET driver_liability_insurance = ?, driver_liability_insurance_date = ? WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setBytes(1, insurancePhoto);
			ps.setString(2, expireDate);
			ps.setInt(3, driver_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int updateTwoPhoto(int driver_id, byte[] imageFront, byte[] imageBack, String action) {
		int count = 0;
		String sql = "";
		if (action.equals("updateId")) {
			sql = "UPDATE Driver SET driver_identify_front = ?, driver_identify_back = ? WHERE driver_id = ?;";
		} else if (action.equals("updateDriverLicence")) {
			sql = "UPDATE Driver SET driver_license_front = ?, driver_license_back = ? WHERE driver_id = ?;";
		} 
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setBytes(1, imageFront);
			ps.setBytes(2, imageBack);
			ps.setInt(3, driver_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public String[] getStatus(int driver_id, String[] status) {
		String sql = "SELECT driver_identify_front, driver_identify_back, driver_license_front, driver_license_back, driver_liability_insurance, driver_liability_insurance_date FROM Driver WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				byte[] identify_front = rs.getBytes(1);
				byte[] identify_back = rs.getBytes(2);
				byte[] license_front = rs.getBytes(3);
				byte[] license_back = rs.getBytes(4);
				byte[] insurance = rs.getBytes(5);
				String expireDate = rs.getString(6);
				if( identify_front != null && identify_back != null ) {
					status[0] = "success";
				} else {
					status[0] = "unfinished";
				}
				if( license_front != null && license_back != null ) {
					status[1] = "success";
				} else {
					status[1] = "unfinished";
				}
				if( insurance != null && expireDate != null ) {
					status[2] = "success";
					status[3] = expireDate;
				} else {
					status[2] = "unfinished";
				}
				
			} return status;
		} catch (SQLException e) {e.printStackTrace();} 
		return status;
	}
	
	@Override
	public Driver findUserById(int driver_id) {
		String sql = "SELECT driver_name, driver_phone, driver_email FROM Driver WHERE driver_id = ?;";
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
				String driver_email = rs.getString(3);
				driver = new Driver(driver_id, driver_name, driver_phone, driver_email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return driver;
	}
}
