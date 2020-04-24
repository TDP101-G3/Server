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
}
