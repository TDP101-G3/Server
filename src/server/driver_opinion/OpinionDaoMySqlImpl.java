package server.driver_opinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import server.main.ServiceLocator;

public class OpinionDaoMySqlImpl implements OpinionDao{
	DataSource dataSource;

	public OpinionDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	@Override
	public List<Opinion> DriverfindById(int driver_id) {
		String sql = "SELECT driver_opinion_question, driver_opinion_answer FROM Driver_opinion WHERE driver_id = ?;";
		List<Opinion> opinionList = new ArrayList<Opinion>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, driver_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String driver_opinion_question = rs.getString(1);
				String driver_opinion_answer = rs.getString(2);
				Opinion opinion = new Opinion(driver_opinion_question, driver_opinion_answer);
				opinionList.add(opinion);
			}
			return opinionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return opinionList;
	}
	
	@Override
	public List<Opinion1> CustomerfindById(int customer_id) {
		String sql = "SELECT customer_opinion_question, customer_opinion_answer FROM Customer_opinion WHERE customer_id = ?;";
		List<Opinion1> opinionList = new ArrayList<Opinion1>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, customer_id);
			/* 當Statement關閉，ResultSet也會自動關閉，
			 * 可以不需要將ResultSet宣告置入try with resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String customer_opinion_question = rs.getString(1);
				String customer_opinion_answer = rs.getString(2);
				Opinion1 opinion1 = new Opinion1(customer_opinion_question, customer_opinion_answer);
				opinionList.add(opinion1);
			}
			return opinionList;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return opinionList;
	}
	
	@Override
	public int DriveropinionInsert(Opinion opinion) {
		int count = 0;
		String sql = "INSERT INTO Driver_opinion" + 
				"(driver_id, driver_opinion_question) "	+ 
				"VALUES(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, opinion.getDriver_id());
			ps.setString(2, opinion.getDriver_opinion_question());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int CustomeropinionInsert(Opinion1 opinion1) {
		int count = 0;
		String sql = "INSERT INTO Customer_opinion" + 
				"(customer_id, customer_opinion_question) "	+ 
				"VALUES(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, opinion1.getCustomer_id());
			ps.setString(2, opinion1.getCustomer_opinion_question());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int update(Opinion opinion) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Opinion> findById(int driver_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
