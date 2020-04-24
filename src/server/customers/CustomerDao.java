package server.customers;

import java.util.List;


public interface CustomerDao {

	Customer findById(int customer_id);

	List<Customer> getAll();

	byte[] getImage(int customer_id);
	
	int matchDriver(double startLatitude,double startLongitude);
}
