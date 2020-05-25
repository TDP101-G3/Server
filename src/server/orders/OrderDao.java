package server.orders;

import java.util.List;


public interface OrderDao {
	
	Order findById(int order_id);
	
	int insert(Order order);
	
	List<Order> getAll();

	List<Order> getOrders(int id, String role);
	
	int getOrderId(int driver_id);
	
	int updateCustomer_score(Order order);
	
	int updateDriver_score(Order2 order2);
	
	Order getCustomer_score(int customer_id);
	
	Order2 getDriver_score(int driver_id);
}
