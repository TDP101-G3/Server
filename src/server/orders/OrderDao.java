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

	Order incomeWeekAll(int driver_id, int yearNumber, int weekNumber);
	
	Order getIncomeweekdaily(int driver_id, int yearNumber, int weekNumber,
			int driver_id_2, int yearNumber_2, int weekNumber_2,int driver_id_3, int yearNumber_3, int weekNumber_3,int driver_id_4, int yearNumber_4, int weekNumber_4,
			int driver_id_5, int yearNumber_5, int weekNumber_5,int driver_id_6, int yearNumber_6, int weekNumber_6,int driver_id_7, int yearNumber_7, int weekNumber_7);

}
