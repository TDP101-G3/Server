package server.managers;

import java.util.List;

import server.drivers.Driver;

public interface ManagerDao {
	int loginCheck (String manager_account, String manager_password);

	List<Driver> getDrivers();
	
	byte[] getImage(int id);

	String getFilesStatus(int id);
}
