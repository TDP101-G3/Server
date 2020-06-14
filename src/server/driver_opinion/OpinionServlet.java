package server.driver_opinion;

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
@WebServlet("/OpinionServlet")
public class OpinionServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	OpinionDao opinionDao = null;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		if (opinionDao == null) {
			opinionDao = new OpinionDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();
		if (action.equals("DriveropinionInsert") || action.equals("CustomeropinionInsert")) {
			String opinionJson = jsonObject.get("opinion").getAsString();
			System.out.println("opinionJson = " + opinionJson);
			int count = 0;
			if (action.equals("DriveropinionInsert")) {
				Opinion opinion = gson.fromJson(opinionJson, Opinion.class);
				count = opinionDao.DriveropinionInsert(opinion);
			} else if (action.equals("CustomeropinionInsert")) {
				Opinion1 opinion1 = gson.fromJson(opinionJson, Opinion1.class);
				count = opinionDao.CustomeropinionInsert(opinion1);
			}
			writeText(response, String.valueOf(count));
		}else if (action.equals("DriverfindById")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			List<Opinion> opinion = opinionDao.DriverfindById(driver_id);
			writeText(response, gson.toJson(opinion));
		}else if (action.equals("CustomerfindById")) {
			int customer_id = jsonObject.get("customer_id").getAsInt();
			List<Opinion1> opinion1 = opinionDao.CustomerfindById(customer_id);
			writeText(response, gson.toJson(opinion1));
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		// 將輸出資料列印出來除錯用
		System.out.println("output: " + outText);
	}
	


}
