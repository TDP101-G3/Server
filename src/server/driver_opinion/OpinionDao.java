package server.driver_opinion;

import java.util.List;

public interface OpinionDao {
	int insert(Opinion opinion);
	
	int update(Opinion opinion);
	
	List<Opinion> findById(int driver_id);
}
