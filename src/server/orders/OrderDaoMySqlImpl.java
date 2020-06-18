package server.orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import server.main.ServiceLocator;

public class OrderDaoMySqlImpl implements OrderDao{
	DataSource dataSource;

	public OrderDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public Order findById(int order_id) {
		String sql = "SELECT order_start, order_end FROM Order_detail WHERE order_id = ?;";
		Order order = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, order_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String order_start = rs.getString(1);
				String order_end = rs.getString(2);
				order = new Order(order_start, order_end);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return order;
	}
	
	@Override
	public Order getCustomer_score(int customer_id) {
		String sql = "SELECT customer_score, driver_score FROM Order_detail WHERE customer_id = ?;";
		Order order = null;
		double customer_score = 0,driver_score = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, customer_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			int c = 0;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				customer_score += rs.getDouble(1);
				driver_score += rs.getDouble(2);
				if(rs.getDouble(1) != 0) {
					c++;
				}
			}
			customer_score = customer_score/c;
			driver_score = driver_score/c;
			order = new Order(customer_id,customer_score, driver_score);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return order;
	}
	
	@Override
	public Order2 getDriver_score(int driver_id) {
		String sql = "SELECT customer_score, driver_score FROM Order_detail WHERE driver_id = ?;";
		Order2 order2 = null;
		double customer_score = 0,driver_score = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			int c = 0;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				customer_score += rs.getDouble(1);
				driver_score += rs.getDouble(2);
				if(rs.getDouble(2) != 0) {
					c++;
				}
			}
			customer_score = customer_score/c;
			driver_score = driver_score/c;
			order2 = new Order2(driver_id, driver_score, customer_score);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return order2;
	}
	
	@Override
	public int updateCustomer_score(Order order) {
		int count = 0;
		String sql = "";
			sql = "UPDATE Order_detail SET customer_score = ?" 
					+ " WHERE order_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setDouble(1, order.getCustomer_score());
			ps.setInt(2, order.getOrder_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	@Override
	public int updateDriver_score(Order2 order2) {
		int count = 0;
		String sql = "";
			sql = "UPDATE Order_detail SET driver_score = ?" 
					+ " WHERE order_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setDouble(1, order2.getDriver_score());
			ps.setInt(2, order2.getOrder_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}
	
	@Override
	public int getOrderId(int driver_id) {
		String sql = "select max(order_id)from Order_detail " + 
				"where order_time<=\"2030-03-29 19:30:36\" AND driver_id = ? " + 
				"group by driver_id;";
		int order_id = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				order_id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order_id;
	}
	
	
	@Override
	public int insert(Order order) {
		int count = 0;
		String sql = "INSERT INTO Order_detail" + 
				"(customer_id, driver_id, order_start, order_end) "	+ 
				"VALUES(?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, order.getCustomer_id());
			ps.setInt(2, order.getDriver_id());
			ps.setString(3, order.getOrder_start());
			ps.setString(4, order.getOrder_end());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int orderinsert(Order order) {
		int count = 0;
		String sql = "INSERT INTO Order_detail" + 
				"(customer_id, driver_id, order_start, order_end, order_money, driver_income, start_longitude, start_latitude, end_longitude, end_latitude) "	+ 
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, order.getCustomer_id());
			ps.setInt(2, order.getDriver_id());
			ps.setString(3, order.getOrder_start());
			ps.setString(4, order.getOrder_end());
			ps.setDouble(5, order.getOrder_money());
			ps.setDouble(6, order.getDriver_income());
			ps.setDouble(7, order.getStart_longitude());
			ps.setDouble(8, order.getStart_latitude());
			ps.setDouble(9, order.getEnd_longitude());
			ps.setDouble(10, order.getEnd_latitude());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public List<Order> getAll() {
		String sql = "SELECT customer_id, driver_id, order_start, order_end " 
				+ "FROM Order_detail;";
		List<Order> orderList = new ArrayList<Order>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt(1);
				int driver_id = rs.getInt(2);
				String order_start = rs.getString(3);
				String order_end = rs.getString(4);
				Order order = new Order( customer_id, driver_id, order_start, order_end);
				orderList.add(order);
			}
			return orderList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return orderList;
	}
	@Override
	public List<Order> getOrders(int id, String role) {
		String sql = "";
		if(role.equals("driver")) {
			sql = "SELECT order_id, driver_id, customer_id, order_time, order_start, order_end, driver_score, customer_score, driver_income, start_longitude, start_latitude, end_longitude, end_latitude ";
			sql += "FROM Order_detail WHERE driver_id = ?;";
		} else {
			sql = "SELECT order_id, driver_id, customer_id, order_time, order_start, order_end, driver_score, customer_score, order_money, start_longitude, start_latitude, end_longitude, end_latitude ";
			sql += "FROM Order_detail WHERE customer_id = ?;";
		}
		List<Order> orderList = new ArrayList<Order>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int order_id = rs.getInt(1);
				int driver_id = rs.getInt(2);
				int customer_id = rs.getInt(3);
				String order_time = rs.getString(4);
				String order_start = rs.getString(5);
				String order_end = rs.getString(6);
				double driver_score = rs.getDouble(7);
				double customer_score = rs.getDouble(8);
				if(customer_score<=0) {
					System.out.println("order id " + order_id + " is empty.");
					continue;
				}
				double order_money = rs.getDouble(9);
				double start_longitude = rs.getDouble(10);
			    double start_latitude = rs.getDouble(11);
			    double end_longitude = rs.getDouble(12);
			    double end_latitude = rs.getDouble(13);
			    
				Order order = new Order(order_id, driver_id, customer_id, order_start, order_end, driver_score, customer_score
							, order_money, order_time, start_longitude, start_latitude, end_longitude, end_latitude);
				orderList.add(order);
			}
			return orderList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return orderList;
	}

	@Override
	public Order incomeWeekAll(int driver_id, int yearNumber, int weekNumber) {
		String sql = "select count(order_id) as week_order_number, sum(driver_income) as week_order_money from Order_detail where  driver_id= ? and year(order_time) = ? and week(order_time,1)= ? and driver_score != 0;";
		Order order = null;
//		System.out.println("driver_id"+ driver_id);
//		System.out.println("yearNumber"+ yearNumber);
//		System.out.println("weekNumber"+ weekNumber);
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			ps.setInt(2, yearNumber);
			ps.setInt(3, weekNumber);
			/*
			 * 當Statement關閉，ResultSet也會自動關閉， 可以不需要將ResultSet宣告置入try with
			 * resources小括號內，參看ResultSet說明
			 */

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int week_order_number = rs.getInt(1);
				int week_order_money = rs.getInt(2);
//				System.out.println("week_order_number"+ week_order_number);
//				System.out.println("week_order_money"+ week_order_money);
				order = new Order(week_order_number, week_order_money);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	@Override
	public Order getIncomeweekdaily(int driver_id, int yearNumber, int weekNumber,int driver_id_2, int yearNumber_2, int weekNumber_2,int driver_id_3, int yearNumber_3, int weekNumber_3,
			int driver_id_4, int yearNumber_4, int weekNumber_4,
			int driver_id_5, int yearNumber_5, int weekNumber_5,int driver_id_6, int yearNumber_6, int weekNumber_6,int driver_id_7, int yearNumber_7, int weekNumber_7) {
		String sql = "select"+
				"(count(case when weekday(order_time) = 0 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ? and driver_score != 0 then 0 else null end)) as monday_order_number,"+
				"(count(case when weekday(order_time) = 1 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ?  and driver_score != 0 then 0 else null end)) as tuesday_order_number,"+
				"(count(case when weekday(order_time) = 2 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ?  and driver_score != 0 then 0 else null end)) as wednesday_order_number,"+
				"(count(case when weekday(order_time) = 3 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ?  and driver_score != 0 then 0 else null end)) as thursday_order_number,"+
				"(count(case when weekday(order_time) = 4 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ?  and driver_score != 0 then 0 else null end)) as friday_order_number,"+
				"(count(case when weekday(order_time) = 5 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ?  and driver_score != 0 then 0 else null end)) as saturday_order_number,"+
				"(count(case when weekday(order_time) = 6 and driver_id= ? and year(order_time) = ? and week(order_time,1)= ?  and driver_score != 0 then 0 else null end)) as sunday_order_number from Order_detail;";
		Order order = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			ps.setInt(2, yearNumber);
			ps.setInt(3, weekNumber);
			ps.setInt(4, driver_id_2);
			ps.setInt(5, yearNumber_2);
			ps.setInt(6, weekNumber_2);
			ps.setInt(7, driver_id_3);
			ps.setInt(8, yearNumber_3);
			ps.setInt(9, weekNumber_3);
			ps.setInt(10, driver_id_4);
			ps.setInt(11, yearNumber_4);
			ps.setInt(12, weekNumber_4);
			ps.setInt(13, driver_id_5);
			ps.setInt(14, yearNumber_5);
			ps.setInt(15, weekNumber_5);
			ps.setInt(16, driver_id_6);
			ps.setInt(17, yearNumber_6);
			ps.setInt(18, weekNumber_6);
			ps.setInt(19, driver_id_7);
			ps.setInt(20, yearNumber_7);
			ps.setInt(21, weekNumber_7);
			/*
			 * 當Statement關閉，ResultSet也會自動關閉， 可以不需要將ResultSet宣告置入try with
			 * resources小括號內，參看ResultSet說明
			 */

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int monday_order_number = rs.getInt(1);
				int tuesday_order_number = rs.getInt(2);
				int wednesday_order_number = rs.getInt(3);
				int thursday_order_number = rs.getInt(4);
				int friday_order_number = rs.getInt(5);
				int saturday_order_number = rs.getInt(6);
				int sunday_order_number = rs.getInt(7);
				System.out.println("monday_order_number"+ monday_order_number);
				System.out.println("tuesday_order_number"+ tuesday_order_number);
				order = new Order(monday_order_number, tuesday_order_number,wednesday_order_number,thursday_order_number,friday_order_number,saturday_order_number,sunday_order_number);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

}
