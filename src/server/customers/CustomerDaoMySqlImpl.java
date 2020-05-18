package server.customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

import javax.sql.DataSource;

import server.drivers.Driver;
import server.main.ServiceLocator;
import server.orders.Order;

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
		String sql = "SELECT customer_name, customer_phone, customer_email, customer_number_plate, customer_car_model, customer_car_color FROM Customer WHERE customer_id = ?;";
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
				String customer_email = rs.getString(3);
				String customer_number_plate = rs.getString(4);
				String customer_car_model = rs.getString(5);
				String customer_car_color = rs.getString(6);
				customer = new Customer(customer_id, customer_name, customer_phone, customer_email, customer_number_plate, customer_car_model, customer_car_color);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return customer;
	}
	
	@Override
	public Customer getUserInfo(String customer_email) {
		String sql = "SELECT customer_id, customer_name FROM Customer WHERE customer_email = ?;";
		Customer customer = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, customer_email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int customer_id = rs.getInt(1);
				String customer_name = rs.getString(2);
				customer = new Customer(customer_id, customer_name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	@Override
	public List<Customer> getAll() {
		String sql = "SELECT customer_id, customer_name, customer_phone, customer_email, customer_number_plate, customer_car_model, customer_car_color, customer_password " 
				+ "FROM Customer;";
		List<Customer> customerList = new ArrayList<Customer>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt(1);
				String customer_name = rs.getString(2);
				String customer_phone = rs.getString(3);
				String customer_email = rs.getString(4);
				String customer_number_plate = rs.getString(5);
				String customer_car_model = rs.getString(6);
				String customer_car_color = rs.getString(7);
				String customer_password = rs.getString(8);
				Customer customer = new Customer(customer_id, customer_name, customer_phone, customer_email, customer_number_plate, customer_car_model, customer_car_color, customer_password);
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
	
	public int updateCustomer(Customer customer) {
		int count = 0;
		String sql = "";	
		sql = "UPDATE Customer SET customer_name = ?, customer_phone = ?, customer_email = ? WHERE customer_id = ?;";
		
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, customer.getCustomer_name());
			ps.setString(2, customer.getCustomer_phone());
			ps.setString(3, customer.getCustomer_email());
			ps.setInt(4, customer.getCustomer_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	public int updateInsurance(Insurance insurance, int customer_id, byte[] image) {
		int count = 0;
		String sql = "";
		String text = insurance.getInsuranceName();
		if(text.equals("carDamage")) {
			sql += "UPDATE Customer SET customer_car_insurance = ?, customer_car_insurance_date = ?, customer_car_insurance_confirm = ? WHERE customer_id = ?;";
		}else if (text.equals("compulsory")) {
			sql += "UPDATE Customer SET customer_compulsory_insurance = ?, customer_compulsory_insurance_date = ?, customer_compulsory_insurance_confirm = ? WHERE customer_id = ?;";
		}else if(text.equals("third")) {
			sql += "UPDATE Customer SET customer_third_insurance = ?, customer_third_insurance_date = ?, customer_third_insurance_confirm = ? WHERE customer_id = ?;";
		}
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setBytes(1, image);
			ps.setString(2, insurance.getExpireDate());
			ps.setInt(3, 0);
			ps.setInt(4, customer_id);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	public int updateCar(Customer customer) {
		int count = 0;
		String sql = "";
		sql = "UPDATE Customer SET customer_number_plate = ?, customer_car_model = ?, customer_car_color = ? WHERE customer_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, customer.getCustomer_number_plate());
			ps.setString(2, customer.getCustomer_car_model());
			ps.setString(3, customer.getCustomer_car_color());
			ps.setInt(4, customer.getCustomer_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	@Override
	public List<Insurance> getInsurances(int customer_id) {
		List<Insurance> insuranceList = new ArrayList<Insurance>();
		String sql = "SELECT customer_car_insurance, customer_car_insurance_date, customer_car_insurance_confirm, customer_compulsory_insurance, customer_compulsory_insurance_date, customer_compulsory_insurance_confirm, customer_third_insurance, customer_third_insurance_date, customer_third_insurance_confirm FROM Customer WHERE customer_id = ?;";
		Insurance carDamage = null, compulsory = null, thirdParty = null;
		
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, customer_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// 1.車體險
				byte[] imgCarDamage = rs.getBytes(1);
				String car_insurance_date = rs.getString(2);
				int car_insurance_confirm = rs.getInt(3);  // 人工驗證結果 0未認證 1認證通過 2沒過
	        	// 審核狀態
				String car_insurance_situation = compareDate(car_insurance_confirm, car_insurance_date, imgCarDamage);
				carDamage = new Insurance(1, "carDamage", car_insurance_date, car_insurance_situation);
				
				// 2.強制險
				byte[] imgCompulsory = rs.getBytes(4);
				String compulsory_insurance_date = rs.getString(5);
				int compulsory_insurance_confirm = rs.getInt(6);  // 人工驗證結果 0未認證 1認證通過 2沒過
				// 審核狀態
				String compulsory_insurance_situation = compareDate(compulsory_insurance_confirm, compulsory_insurance_date, imgCompulsory);
				compulsory = new Insurance(2, "compulsory", compulsory_insurance_date, compulsory_insurance_situation);
				
				// 3.第三方責任險
				byte[] imgThirdInsurance = rs.getBytes(7);
				String third_insurance_date = rs.getString(8);
				int third_insurance_confirm = rs.getInt(9);  // 人工驗證結果 0未認證 1認證通過 2沒過
				// 審核狀態
				String third_insurance_situation = compareDate(third_insurance_confirm, third_insurance_date, imgThirdInsurance);
				thirdParty = new Insurance(3, "third", third_insurance_date, third_insurance_situation);
				
				insuranceList.add(carDamage);
				insuranceList.add(compulsory);
				insuranceList.add(thirdParty);
			} return insuranceList;
		} catch (SQLException | ParseException e) {e.printStackTrace();} 
		return insuranceList;
	}
	// 判斷認證狀態
	private String compareDate(int confirm, String date, byte[] img) throws ParseException {
		String text = "";
		if (date == null) {
        	date = "2000-01-01";
		} 
		// 得到今天日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		Date expireDate = null;
        
        expireDate = format.parse(date);
		if (confirm == 1) {
			if(expireDate.after(today)) {
				text = "success"; // 1 認證成功
			}else {
				text = "expired"; // 1 已過期
			}
			
		} else if (confirm == 0){
			if(img == null) {
				text = "unfinished";  // 0 未認證
			}else {
				text = "processing"; // 0 認證中
			}
		}else {
			text = "failed";  // 2 失敗
		}
		return text;
	}
	

	@Override
	public int loginCheck(String customer_email, String customer_password) {
		int count = 0;
		String email = null;
		String password = null;
		String sql = "SELECT customer_email, customer_password " + " FROM Customer WHERE customer_email = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, customer_email);
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

			if (customer_email.equals(email)) {
				System.out.println(email);
				if (customer_password.equals(password)) {
					System.out.println(password);
					return count = 1; // 登入成功
				} else {
					System.out.println(password);
					System.out.println(customer_password);
					return count = 2; // 密碼錯誤
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count; // 系統錯誤
	}

	@Override
	public int signUp(Customer customer, byte[] idFront, byte[] idBack, byte[] carDamage, byte[] compulsory, byte[] carThirdParty) {
		int count = 0;
		String sql = "INSERT INTO Customer"
				+ "(customer_name, customer_email, customer_password, customer_phone, customer_number_plate, customer_car_model, customer_car_color,customer_identify_front,customer_identify_back,customer_car_insurance,customer_compulsory_insurance,customer_third_insurance) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?,?,?,?,?,?);";
		try (Connection connection = dataSource.getConnection(); // 拿到連線池中的連線
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, customer.getCustomer_name());
			ps.setString(2, customer.getCustomer_email());
			ps.setString(3, customer.getCustomer_password());
			ps.setString(4, customer.getCustomer_phone());
			ps.setString(5, customer.getCustomer_number_plate());
			ps.setString(6, customer.getCustomer_car_model());
			ps.setString(7, customer.getCustomer_car_color());
			ps.setBytes(8, idFront);
			ps.setBytes(9, idBack);
			ps.setBytes(10, carDamage);
			ps.setBytes(11, compulsory);
			ps.setBytes(12, carThirdParty);
			count = ps.executeUpdate();// 異動結果
			System.out.println("count: " + count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<Order> getOrders(int customer_id) {
		String sql = "SELECT order_id, driver_id, order_time, order_start, order_end, driver_score, customer_score, order_money " 
				+ "FROM Order_detail WHERE customer_id = ?;";
		List<Order> orderList = new ArrayList<Order>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, customer_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int order_id = rs.getInt(1);
				int driver_id = rs.getInt(2);
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
}
