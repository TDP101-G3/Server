package server.customers;

import java.util.List;


public interface CustomerDao {

	Customer findById(int customer_id);

	List<Customer> getAll();
	
	List<Insurance> getInsurances(int customer_id);

	byte[] getImage(int customer_id);
	
	int matchDriver(double startLatitude,double startLongitude);
	
	int updateCustomer(Customer customer, byte[] image);
	
	int updateCar(Customer customer);
	
	int updateInsurance(Insurance insurance, int customer_id, byte[] image);

}
