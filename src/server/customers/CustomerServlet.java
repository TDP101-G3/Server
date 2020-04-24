package server.customers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server.main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	CustomerDao customerDao = null;
	
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
		if (customerDao == null) {
			customerDao = new CustomerDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Customer> customers = customerDao.getAll();
			writeText(response, gson.toJson(customers));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int customer_id = jsonObject.get("customer_id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = customerDao.getImage(customer_id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("findById")) {
			int customer_id = jsonObject.get("customer_id").getAsInt();
			Customer customer = customerDao.findById(customer_id);
			writeText(response, gson.toJson(customer));
		} else if(action.equals("matchDriver")){
			double startLatitude = jsonObject.get("startLatitude").getAsDouble();
			double startLongitude = jsonObject.get("startLongitude").getAsDouble();
			int driver_id = customerDao.matchDriver(startLatitude,startLongitude);
			writeText(response, gson.toJson(driver_id));
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
		if (customerDao == null) {
			customerDao = new CustomerDaoMySqlImpl();
		}
		List<Customer> customers = customerDao.getAll();
		writeText(response, new Gson().toJson(customers));
	}
	
}
