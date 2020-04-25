package server.orders;

public class Order2 {
	private int order_id;
	private double driver_score;
	
	public Order2(int order_id, double driver_score) {
		this.order_id = order_id;
		this.driver_score = driver_score;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public double getDriver_score() {
		return driver_score;
	}
	public void setDriver_score(double driver_score) {
		this.driver_score = driver_score;
	}
	
}
