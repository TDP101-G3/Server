package server.orders;

public class Order2 {
	private int order_id;
	private int driver_id;
	private double driver_score;
	private double customer_score;
	
	public Order2(int order_id, double driver_score) {
		this.order_id = order_id;
		this.driver_score = driver_score;
	}
	
	public Order2(int driver_id, double driver_score, double customer_score) {
		this.driver_id = driver_id;
		this.driver_score = driver_score;
		this.customer_score = customer_score;	
	}
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public double getDriver_score() {
		return driver_score;
	}
	public void setDriver_score(double driver_score) {
		this.driver_score = driver_score;
	}
	public double getCustomer_score() {
		return customer_score;
	}
	public void setCustomer_score(double customer_score) {
		this.customer_score = customer_score;
	}

}
