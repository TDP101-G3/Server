package server.customers;

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

import server.drivers.Driver;
import server.main.ImageUtil;
import server.orders.Order;

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
		} else if (action.equals("getUserInfo")) {
			String customer_email = jsonObject.get("email").getAsString();
			Customer customer = customerDao.getUserInfo(customer_email);
			writeText(response, gson.toJson(customer));
		} else if(action.equals("matchDriver")){
			double startLatitude = jsonObject.get("startLatitude").getAsDouble();
			double startLongitude = jsonObject.get("startLongitude").getAsDouble();
			int driver_id = customerDao.matchDriver(startLatitude,startLongitude);
			writeText(response, gson.toJson(driver_id));
		} else if (action.equals("getInsurances")) {
			int customer_id = jsonObject.get("customer_id").getAsInt();
			List<Insurance> insurances = customerDao.getInsurances(customer_id);
			writeText(response, gson.toJson(insurances));
		} else if (action.equals("updateCustomer")) {
			String customerJson = jsonObject.get("customer").getAsString();
			Customer customer = gson.fromJson(customerJson, Customer.class);
			int count = 0;
			count = customerDao.updateCustomer(customer);
			writeText(response, String.valueOf(count));
		}else if (action.equals("updateCar")) {
			String customerJson = jsonObject.get("customer").getAsString();
			Customer customer = gson.fromJson(customerJson, Customer.class);
			int count = 0;
			count = customerDao.updateCar(customer);
			writeText(response, String.valueOf(count));
			
		}else if (action.equals("updateInsurance")) {
			String insuranceJson = jsonObject.get("insurance").getAsString();
			Insurance insurance = gson.fromJson(insuranceJson, Insurance.class);
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = Base64.getMimeDecoder().decode(imageBase64);
			int customer_id = jsonObject.get("customer_id").getAsInt();
			System.out.println("updateInsurance customer_id: " + customer_id);
			int count = 0;
			count = customerDao.updateInsurance(insurance, customer_id, image);
			writeText(response, String.valueOf(count));
		}else if (action.equals("loginCheck")) {
			String customer_email = jsonObject.get("email").getAsString();
			String customer_password = jsonObject.get("password").getAsString();
			int count = 0;
			count = customerDao.loginCheck(customer_email, customer_password);
			writeText(response, String.valueOf(count));
		} else if (action.equals("signUp")) {// 新增跟更新
			String customerJson = jsonObject.get("customer").getAsString();
			System.out.println("customerJson = " + customerJson);
			Customer customer = gson.fromJson(customerJson, Customer.class);// 一次轉回spot物件
			byte[] idFront = null;
			byte[] idBack = null;
			byte[] carDamage = null;
			byte[] compulsory = null;
			byte[] carThirdParty = null;
			byte[] userPhoto = null;
			// 檢查是否有上傳圖片
			if (jsonObject.get("imageBase64") != null) {// 有圖不是空值
				String imageBase64 = jsonObject.get("imageBase64").getAsString();// 取出來
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					idFront = Base64.getMimeDecoder().decode(imageBase64);// 解析還原，得到byte陣列
				}
			}
			if (jsonObject.get("idBackBase64") != null) {
				String idBackBase64 = jsonObject.get("idBackBase64").getAsString();
				if (idBackBase64 != null && !idBackBase64.isEmpty()) {
					idBack = Base64.getMimeDecoder().decode(idBackBase64);
				}
			}
			if (jsonObject.get("carDamageBase64") != null) {
				String carDamageBase64 = jsonObject.get("carDamageBase64").getAsString();
				if (carDamageBase64 != null && !carDamageBase64.isEmpty()) {
					carDamage = Base64.getMimeDecoder().decode(carDamageBase64);
				}
			}
			if (jsonObject.get("compulsoryBase64") != null) {
				String compulsoryBase64 = jsonObject.get("compulsoryBase64").getAsString();
				if (compulsoryBase64 != null && !compulsoryBase64.isEmpty()) {
					compulsory = Base64.getMimeDecoder().decode(compulsoryBase64);
				}
			}
			if (jsonObject.get("carThirdPartyBase64") != null) {
				String carThirdPartyBase64 = jsonObject.get("carThirdPartyBase64").getAsString();
				if (carThirdPartyBase64 != null && !carThirdPartyBase64.isEmpty()) {
					carThirdParty = Base64.getMimeDecoder().decode(carThirdPartyBase64);
				}
			}
			if (jsonObject.get("userPhotoBase64") != null) {
				String userPhotoBase64 = jsonObject.get("userPhotoBase64").getAsString();
				if (userPhotoBase64 != null && !userPhotoBase64.isEmpty()) {
					userPhoto = Base64.getMimeDecoder().decode(userPhotoBase64);
				}
			}
			int count = 0;
			count = customerDao.signUp(customer, idFront, idBack, carDamage, compulsory, carThirdParty, userPhoto);
			writeText(response, String.valueOf(count));// 轉成字串送回去使用者那邊insertfragment

		} else if(action.equals("getOrders")){
			int customer_id = jsonObject.get("customer_id").getAsInt();
			List<Order> orders = customerDao.getOrders(customer_id);
			writeText(response, gson.toJson(orders));
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
