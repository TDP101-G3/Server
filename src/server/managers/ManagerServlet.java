package server.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server.customers.Customer;
import server.drivers.Driver;
import server.main.ImageUtil;


@SuppressWarnings("serial")
@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	ManagerDao managerDao = null;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		if (managerDao == null) {
			managerDao = new ManagerDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("loginCheck")) {
			String driver_account = jsonObject.get("account").getAsString();
			String driver_password = jsonObject.get("password").getAsString();
			int driver = managerDao.loginCheck(driver_account, driver_password);
			writeText(response, String.valueOf(driver));
		} else if (action.equals("getDrivers")) {
			List<Driver> drivers = managerDao.getDrivers();
			writeText(response, gson.toJson(drivers));
		} else if (action.equals("getCustomers")) {
			List<Customer> customers = managerDao.getCustomers();
			writeText(response, gson.toJson(customers));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			String role = jsonObject.get("role").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = managerDao.getImage(role,id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("getImageFile")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			String role = jsonObject.get("role").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			String fileName = jsonObject.get("fileName").getAsString();
			byte[] image = managerDao.getImageFile(role,id,fileName);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		} else if (action.equals("getFilesStatus")) {
			int id = jsonObject.get("id").getAsInt();
			String jsonFilesStatus = managerDao.getFilesStatus(id);
			writeText(response, jsonFilesStatus);
		} else if (action.equals("updateDriverData")) {
			String driverJson = jsonObject.get("driver").getAsString();
			Driver driver = gson.fromJson(driverJson, Driver.class);
			int count = 0;
			count = managerDao.updateDriverData(driver);
			writeText(response, String.valueOf(count));
		} else if (action.equals("updateCustomerData")) {
			String driverJson = jsonObject.get("customer").getAsString();
			Customer customer = gson.fromJson(driverJson, Customer.class);
			int count = 0;
			count = managerDao.updateCustomerData(customer);
			writeText(response, String.valueOf(count));
		} else if (action.equals("updateUserPhoto")) {
			int id = jsonObject.get("id").getAsInt();
			String role = jsonObject.get("role").getAsString();
			String image = jsonObject.get("imageBase64").getAsString();
			byte[] userPhoto = null;
			if (image != null && !image.isEmpty()) {
				userPhoto = Base64.getMimeDecoder().decode(image);
			}
			int count = 0;
			count = managerDao.updateUserPhoto(id, role, userPhoto);
			writeText(response, String.valueOf(count));
		} else if (action.equals("deleteUserPhoto")) {
			int id = jsonObject.get("id").getAsInt();
			String role = jsonObject.get("role").getAsString();
			int count = 0;
			count = managerDao.deleteUserPhoto(id, role);
			writeText(response, String.valueOf(count));
		} else {
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
		if (managerDao == null) {
			managerDao = new ManagerDaoMySqlImpl();
		}
		List<Driver> drivers = managerDao.getDrivers();
		writeText(response, new Gson().toJson(drivers));
	}
	

}
