package server.driver_opinion;

import java.util.List;

public interface OpinionDao {
	int DriveropinionInsert(Opinion opinion);
	
	int CustomeropinionInsert(Opinion1 opinion1);
	
	List<Opinion> DriverfindById(int driver_id);
	
	List<Opinion1> CustomerfindById(int customer_id);

	int update(Opinion opinion);
	
	List<Opinion> findById(int driver_id);
}
