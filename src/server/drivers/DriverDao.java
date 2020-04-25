package server.drivers;

import java.util.List;


public interface DriverDao {
	int update(Driver driver);
	
	int locationupdate(Driver driver);
	
	Driver getLocation(int driver_id);

	Driver findById(int driver_id);
	
	Driver getInformation(int driver_id);
	
	List<Driver> getAll();
	
	byte[] getImage(int driver_id);
}