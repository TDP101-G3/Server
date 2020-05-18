package server.drivers;

import java.util.List;

import server.orders.Order;


public interface DriverDao {
	int update(Driver driver);
	
	int locationupdate(Driver driver);
	
	Driver getLocation(int driver_id);

	Driver findById(int driver_id);
	
	Driver findUserById(int driver_id);
	
	Driver getUserInfo(String driver_email);
	
	Driver getInformation(int driver_id);
	
	List<Driver> getAll();
	
	List<Order> getOrders(int driver_id);
	
	byte[] getImage(int driver_id);
	
	int loginCheck (String driver_email, String driver_password);
	
	int signUp (Driver driver, byte[] idFront, byte[] idBack, byte[] licenseFront, byte[] licenseBack, byte[] driverSecure);
	
	int updateUserData (Driver driver);
	
	int updateUserPhoto(int driver_id, byte[] userPhoto);

	int updateInsurance(int driver_id, byte[] insurancePhoto, String expireDate);
	
	int updateTwoPhoto(int driver_id, byte[] imageFront, byte[] imageBack, String action);
	
	String[] getStatus(int driver_id, String[] status);
	
}