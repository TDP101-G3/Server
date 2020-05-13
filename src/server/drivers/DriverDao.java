package server.drivers;

import java.util.List;


public interface DriverDao {
	int update(Driver driver);
	
	int locationupdate(Driver driver);
	
	Driver getLocation(int driver_id);

	Driver findById(int driver_id);
	
	Driver getUserInfo(String driver_email);
	
	Driver getInformation(int driver_id);
	
	List<Driver> getAll();
	
	byte[] getImage(int driver_id);
	
	int loginCheck (String driver_email, String driver_password);
	
	int signUp (Driver driver, byte[] idFront, byte[] idBack, byte[] licenseFront, byte[] licenseBack, byte[] driverSecure);
}