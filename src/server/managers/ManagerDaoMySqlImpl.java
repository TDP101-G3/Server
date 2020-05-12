package server.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

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
}
