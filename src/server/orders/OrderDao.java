package server.orders;

import java.util.List;


public interface OrderDao {
	
	Order findById(int order_id);
	
	int insert(Order order);
	
	List<Order> getAll();
	
	int getOrderId(int driver_id);
	
	int updateCustomer_score(Order order);
	
	int updateDriver_score(Order2 order2);
}
