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

    private int yearNumber;
    private int weekNumber;
    private int week_order_number;
    private int week_order_money;
    private int monday_order_number;
    private int tuesday_order_number;
    private int wednesday_order_number;
    private int thursday_order_number;
    private int friday_order_number;
    private int saturday_order_number;
    private int sunday_order_number;
    
    private int driver_id_2;
    private int yearNumber_2;
    private int weekNumber_2;
    private int driver_id_3;
    private int yearNumber_3; 
    private int weekNumber_3;
    private int driver_id_4;
    private int yearNumber_4;
    private int weekNumber_4;
    private int driver_id_5;
    private int yearNumber_5;
    private int weekNumber_5;
    private int driver_id_6;
    private int yearNumber_6;
    private int weekNumber_6;
    private int driver_id_7;
    private int yearNumber_7;
    private int weekNumber_7;
	
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

   public Order(int driver_id, int yearNumber, int weekNumber){
        this.driver_id = driver_id;
        this.yearNumber = yearNumber;
        this.weekNumber = weekNumber;
    }
    
    public Order(int week_order_number, int week_order_money) {
    	this.week_order_number = week_order_number;
    	this.week_order_money = week_order_money;
    }
    
    public Order(int monday_order_number, int tuesday_order_number, int wednesday_order_number, int thursday_order_number, int friday_order_number, int saturday_order_number, int sunday_order_number) {
    	this.monday_order_number = monday_order_number;
    	this.tuesday_order_number = tuesday_order_number;
    	this.wednesday_order_number = wednesday_order_number;
    	this.thursday_order_number = thursday_order_number;
    	this.friday_order_number = friday_order_number;
    	this.saturday_order_number = saturday_order_number;
    	this.sunday_order_number = sunday_order_number;
    }
    
    public Order(int driver_id, int yearNumber, int weekNumber,
			int driver_id_2, int yearNumber_2, int weekNumber_2,int driver_id_3, int yearNumber_3, int weekNumber_3,int driver_id_4, int yearNumber_4, int weekNumber_4,
			int driver_id_5, int yearNumber_5, int weekNumber_5,int driver_id_6, int yearNumber_6, int weekNumber_6,int driver_id_7, int yearNumber_7, int weekNumber_7) {
        this.driver_id = driver_id;
        this.yearNumber = yearNumber;
        this.weekNumber = weekNumber;
        this.driver_id_2 = driver_id_2;
        this.yearNumber_2 = yearNumber_2;
        this.weekNumber_2 = weekNumber_2;
        this.driver_id_3 = driver_id_3;
        this.yearNumber_3 = yearNumber_3;
        this.weekNumber_3 = weekNumber_3;
        this.driver_id_4 = driver_id_4;
        this.yearNumber_4 = yearNumber_4;
        this.weekNumber_4 = weekNumber_4;
        this.driver_id_5 = driver_id_5;
        this.yearNumber_5 = yearNumber_5;
        this.weekNumber_5 = weekNumber_5;
        this.driver_id_6 = driver_id_6;
        this.yearNumber_6 = yearNumber_6;
        this.weekNumber_6 = weekNumber_6;
        this.driver_id_7 = driver_id_7;
        this.yearNumber_7 = yearNumber_7;
        this.weekNumber_7 = weekNumber_7;
    	
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
    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

	public int getWeek_order_number() {
		return week_order_number;
	}

	public void setWeek_order_number(int week_order_number) {
		this.week_order_number = week_order_number;
	}

	public int getWeek_order_money() {
		return week_order_money;
	}

	public void setWeek_order_money(int week_order_money) {
		this.week_order_money = week_order_money;
	}

	public int getMonday_order_number() {
		return monday_order_number;
	}

	public void setMonday_order_number(int monday_order_number) {
		this.monday_order_number = monday_order_number;
	}

	public int getTuesday_order_number() {
		return tuesday_order_number;
	}

	public void setTuesday_order_number(int tuesday_order_number) {
		this.tuesday_order_number = tuesday_order_number;
	}

	public int getWednesday_order_number() {
		return wednesday_order_number;
	}

	public void setWednesday_order_number(int wednesday_order_number) {
		this.wednesday_order_number = wednesday_order_number;
	}

	public int getThursday_order_number() {
		return thursday_order_number;
	}

	public void setThursday_order_number(int thursday_order_number) {
		this.thursday_order_number = thursday_order_number;
	}

	public int getFriday_order_number() {
		return friday_order_number;
	}

	public void setFriday_order_number(int friday_order_number) {
		this.friday_order_number = friday_order_number;
	}

	public int getSaturday_order_number() {
		return saturday_order_number;
	}

	public void setSaturday_order_number(int saturday_order_number) {
		this.saturday_order_number = saturday_order_number;
	}

	public int getSunday_order_number() {
		return sunday_order_number;
	}

	public void setSunday_order_number(int sunday_order_number) {
		this.sunday_order_number = sunday_order_number;
	}

	public int getDriver_id_2() {
		return driver_id_2;
	}

	public void setDriver_id_2(int driver_id_2) {
		this.driver_id_2 = driver_id_2;
	}

	public int getYearNumber_2() {
		return yearNumber_2;
	}

	public void setYearNumber_2(int yearNumber_2) {
		this.yearNumber_2 = yearNumber_2;
	}

	public int getWeekNumber_2() {
		return weekNumber_2;
	}

	public void setWeekNumber_2(int weekNumber_2) {
		this.weekNumber_2 = weekNumber_2;
	}

	public int getDriver_id_3() {
		return driver_id_3;
	}

	public void setDriver_id_3(int driver_id_3) {
		this.driver_id_3 = driver_id_3;
	}

	public int getYearNumber_3() {
		return yearNumber_3;
	}

	public void setYearNumber_3(int yearNumber_3) {
		this.yearNumber_3 = yearNumber_3;
	}

	public int getWeekNumber_3() {
		return weekNumber_3;
	}

	public void setWeekNumber_3(int weekNumber_3) {
		this.weekNumber_3 = weekNumber_3;
	}

	public int getDriver_id_4() {
		return driver_id_4;
	}

	public void setDriver_id_4(int driver_id_4) {
		this.driver_id_4 = driver_id_4;
	}

	public int getYearNumber_4() {
		return yearNumber_4;
	}

	public void setYearNumber_4(int yearNumber_4) {
		this.yearNumber_4 = yearNumber_4;
	}

	public int getWeekNumber_4() {
		return weekNumber_4;
	}

	public void setWeekNumber_4(int weekNumber_4) {
		this.weekNumber_4 = weekNumber_4;
	}

	public int getDriver_id_5() {
		return driver_id_5;
	}

	public void setDriver_id_5(int driver_id_5) {
		this.driver_id_5 = driver_id_5;
	}

	public int getYearNumber_5() {
		return yearNumber_5;
	}

	public void setYearNumber_5(int yearNumber_5) {
		this.yearNumber_5 = yearNumber_5;
	}

	public int getWeekNumber_5() {
		return weekNumber_5;
	}

	public void setWeekNumber_5(int weekNumber_5) {
		this.weekNumber_5 = weekNumber_5;
	}

	public int getDriver_id_6() {
		return driver_id_6;
	}

	public void setDriver_id_6(int driver_id_6) {
		this.driver_id_6 = driver_id_6;
	}

	public int getYearNumber_6() {
		return yearNumber_6;
	}

	public void setYearNumber_6(int yearNumber_6) {
		this.yearNumber_6 = yearNumber_6;
	}

	public int getWeekNumber_6() {
		return weekNumber_6;
	}

	public void setWeekNumber_6(int weekNumber_6) {
		this.weekNumber_6 = weekNumber_6;
	}

	public int getDriver_id_7() {
		return driver_id_7;
	}

	public void setDriver_id_7(int driver_id_7) {
		this.driver_id_7 = driver_id_7;
	}

	public int getYearNumber_7() {
		return yearNumber_7;
	}

	public void setYearNumber_7(int yearNumber_7) {
		this.yearNumber_7 = yearNumber_7;
	}

	public int getWeekNumber_7() {
		return weekNumber_7;
	}

	public void setWeekNumber_7(int weekNumber_7) {
		this.weekNumber_7 = weekNumber_7;
	}

}
