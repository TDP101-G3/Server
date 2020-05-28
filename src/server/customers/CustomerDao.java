package server.customers;

import java.util.List;

import server.orders.Order;


public interface CustomerDao {

	Customer findById(int customer_id);
	
	Customer getUserInfo(String customer_email);

	List<Customer> getAll();
	
	List<Insurance> getInsurances(int customer_id);
	
	List<Order> getOrders(int customer_id);

	byte[] getImage(int customer_id);
	
	int matchDriver(double startLatitude,double startLongitude);
	
	int updateCustomer(Customer customer);
	
	int updateCar(Customer customer);
	
	int updateInsurance(Insurance insurance, int customer_id, byte[] image);
	
	int loginCheck (String customer_email, String customer_password);
	
	int signUp (Customer customer, byte[] idFront, byte[] idBack, byte[] carDamage, byte[] compulsory, byte[] carThirdParty, byte[] userPhoto);

}
