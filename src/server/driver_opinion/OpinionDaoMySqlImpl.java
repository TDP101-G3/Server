package server.driver_opinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
import server.main.ServiceLocator;

public class OpinionDaoMySqlImpl implements OpinionDao{
	DataSource dataSource;

	public OpinionDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
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
