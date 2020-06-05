package server.orders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



@SuppressWarnings("serial")
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	OrderDao orderDao = null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		// 將輸入資料列印出來除錯用
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (orderDao == null) {
			orderDao = new OrderDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Order> orders = orderDao.getAll();
			writeText(response, gson.toJson(orders));
		} else if (action.equals("getOrderId")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			int order_id = orderDao.getOrderId(driver_id);
			writeText(response, String.valueOf(order_id));
		} else if (action.equals("orderInsert") || action.equals("customer_scoreUpdate")) {
			String orderJson = jsonObject.get("order").getAsString();
			System.out.println("orderJson = " + orderJson);
			Order order = gson.fromJson(orderJson, Order.class);
			int count = 0;
			if (action.equals("orderInsert")) {
				count = orderDao.orderinsert(order);
			} else if (action.equals("customer_scoreUpdate")) {
				count = orderDao.updateCustomer_score(order);
			}
			writeText(response, String.valueOf(count));
		} else if(action.equals("driver_scoreUpdate")) {
			String orderJson = jsonObject.get("order").getAsString();
			System.out.println("orderJson = " + orderJson);
			Order2 order2 = gson.fromJson(orderJson, Order2.class);
			int count = 0;
			count = orderDao.updateDriver_score(order2);
			writeText(response, String.valueOf(count));
		} else if (action.equals("findById")) {
			int order_id = jsonObject.get("order_id").getAsInt();
			Order order = orderDao.findById(order_id);
			writeText(response, gson.toJson(order));
		} else if(action.equals("getCustomer_score")) {
			int customer_id = jsonObject.get("customer_id").getAsInt();
			Order order = orderDao.getCustomer_score(customer_id);
			writeText(response, gson.toJson(order));
		} else if(action.equals("getDriver_score")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Order2 order2 = orderDao.getDriver_score(driver_id);
			writeText(response, gson.toJson(order2));
		} else if(action.equals("getOrders")){
			int id = jsonObject.get("id").getAsInt();
			String role = jsonObject.get("role").getAsString();
			List<Order> orders = orderDao.getOrders(id,role);
			writeText(response, gson.toJson(orders));
		} else if (action.equals("incomeWeekAll")) {
		    int driver_id = jsonObject.get("driver_id").getAsInt();
		    int yearNumber = jsonObject.get("yearNumber").getAsInt();
		    int weekNumber = jsonObject.get("weekNumber").getAsInt();
			Order order = orderDao.incomeWeekAll(driver_id, yearNumber, weekNumber);
			writeText(response, gson.toJson(order));
		}  else if (action.equals("getIncomeweekdaily")) {
		    int driver_id = jsonObject.get("driver_id").getAsInt();
		    int yearNumber = jsonObject.get("yearNumber").getAsInt();
		    int weekNumber = jsonObject.get("weekNumber").getAsInt();
		    int driver_id_2 = jsonObject.get("driver_id_2").getAsInt();
		    int yearNumber_2 = jsonObject.get("yearNumber_2").getAsInt();
		    int weekNumber_2 = jsonObject.get("weekNumber_2").getAsInt();
		    int driver_id_3 = jsonObject.get("driver_id_3").getAsInt();
		    int yearNumber_3 = jsonObject.get("yearNumber_3").getAsInt();
		    int weekNumber_3 = jsonObject.get("weekNumber_3").getAsInt();
		    int driver_id_4 = jsonObject.get("driver_id_4").getAsInt();
		    int yearNumber_4 = jsonObject.get("yearNumber_4").getAsInt();
		    int weekNumber_4 = jsonObject.get("weekNumber_4").getAsInt();
		    int driver_id_5 = jsonObject.get("driver_id_5").getAsInt();
		    int yearNumber_5 = jsonObject.get("yearNumber_5").getAsInt();
		    int weekNumber_5 = jsonObject.get("weekNumber_5").getAsInt();
		    int driver_id_6 = jsonObject.get("driver_id_6").getAsInt();
		    int yearNumber_6 = jsonObject.get("yearNumber_6").getAsInt();
		    int weekNumber_6 = jsonObject.get("weekNumber_6").getAsInt();
		    int driver_id_7 = jsonObject.get("driver_id_7").getAsInt();
		    int yearNumber_7 = jsonObject.get("yearNumber_7").getAsInt();
		    int weekNumber_7 = jsonObject.get("weekNumber_7").getAsInt();
			Order order = orderDao.getIncomeweekdaily(driver_id, yearNumber, weekNumber,driver_id_2, yearNumber_2, weekNumber_2,driver_id_3, yearNumber_3, weekNumber_3, driver_id_4, yearNumber_4, weekNumber_4, driver_id_5,  yearNumber_5,  weekNumber_5, driver_id_6,  yearNumber_6,  weekNumber_6, driver_id_7,  yearNumber_7, weekNumber_7);
			writeText(response, gson.toJson(order));
		}else {
			writeText(response, "");
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + outText);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (orderDao == null) {
			orderDao = new OrderDaoMySqlImpl();
		}
		List<Order> orders = orderDao.getAll();
		writeText(response, new Gson().toJson(orders));
	}
	
}
