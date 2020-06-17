package server.managers;

import java.util.List;

import server.customers.Customer;
import server.drivers.Driver;

public interface ManagerDao {
	int loginCheck (String manager_account, String manager_password);

	List<Driver> getDrivers();
	
	List<Customer> getCustomers();
	
	byte[] getImage(String role, int id);

	String getFilesStatus(int id);

	int updateUserPhoto(int id, String role, byte[] userPhoto);

	int deleteUserPhoto(int id, String role);

	byte[] getImageFile(String role, int id, String fileName);

	int updateDriverData(Driver driver);

	int updateCustomerData(Customer customer);

	
}
