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
	public List<Opinion> findById(int driver_id) {
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
	public int insert(Opinion opinion) {
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
	public int update(Opinion opinion) {
		int count = 0;
		String sql = "UPDATE Driver_opinion SET driver_opinion_answer = ?" + " WHERE driver_id = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, opinion.getDriver_opinion_answer());
			ps.setInt(2, opinion.getDriver_id());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
