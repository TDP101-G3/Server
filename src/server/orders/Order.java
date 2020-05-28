package server.orders;

public class Order {
	private int order_id;
	private int customer_id;
	private int driver_id;
	private String order_time;
	private String order_start;
	private String order_end;
	private double customer_score;
	private double driver_score;
	private double order_money;
	private double start_longitude;
    private double start_latitude;
    private double end_longitude;
    private double end_latitude;
	
	public Order(int order_id) {
		this.order_id = order_id;
	}
	
	public Order(int order_id, double customer_score) {
		this.order_id = order_id;
		this.customer_score = customer_score;
	}
	
	public Order(int customer_id, double customer_score, double driver_score) {
		this.customer_id = customer_id;
		this.customer_score = customer_score;
		this.driver_score = driver_score;
	}
	
	public Order(String order_start, String order_end) {
		this.order_start = order_start;
	    this.order_end = order_end;
	}
	
	public Order(int order_id, int customer_id, int driver_id, String order_time, String order_start, String order_end, double driver_score, double customer_score, double order_money){
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.driver_id = driver_id;
        this.order_time = order_time;
        this.order_start = order_start;
        this.order_end = order_end;
        this.driver_score = driver_score;
        this.customer_score = customer_score;
        this.order_money = order_money;
    }
	
	
	public Order(int customer_id, int driver_id, String order_start, String order_end){
        this.customer_id = customer_id;
        this.driver_id = driver_id;
        this.order_start = order_start;
        this.order_end = order_end;
    }
	
	public Order(int order_id, int driver_id, int customer_id, String order_start, String order_end, double driver_score, double customer_score, double order_money, String order_time, double start_longitude, double start_latitude, double end_longitude, double end_latitude) {
        this.order_id = order_id;
        this.driver_id = driver_id;
        this.customer_id = customer_id;
        this.order_start = order_start;
        this.order_end = order_end;
        this.driver_score = driver_score;
        this.customer_score = customer_score;
        this.order_money = order_money;
        this.order_time = order_time;
        this.start_longitude = start_longitude;
        this.start_latitude = start_latitude;
        this.end_longitude = end_longitude;
        this.end_latitude = end_latitude;
    }
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public String getOrder_start() {
		return order_start;
	}
	public void setOrder_start(String order_start) {
		this.order_start = order_start;
	}
	public String getOrder_end() {
		return order_end;
	}
	public void setOrder_end(String order_end) {
		this.order_end = order_end;
	}
	public double getOrder_money() {
		return order_money;
	}
	public void setOrder_money(double order_money) {
		this.order_money = order_money;
	}
	public double getCustomer_score() {
		return customer_score;
	}
	public void setCustomer_score(double customer_score) {
		this.customer_score = customer_score;
	}
	public double getDriver_score() {
		return driver_score;
	}
	public void setDriver_score(double driver_score) {
		this.driver_score = driver_score;
	}
	public double getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(double start_longitude) {
        this.start_longitude = start_longitude;
    }

    public double getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public double getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(double end_longitude) {
        this.end_longitude = end_longitude;
    }

    public double getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(double end_latitude) {
        this.end_latitude = end_latitude;
    }
    public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
}
