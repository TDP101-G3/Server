package server.drivers;

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
import server.main.ImageUtil;
import server.orders.Order;

@SuppressWarnings("serial")
@WebServlet("/DriverServlet")
public class DriverServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	DriverDao driverDao = null;

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
		if (driverDao == null) {
			driverDao = new DriverDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Driver> drivers = driverDao.getAll();
			writeText(response, gson.toJson(drivers));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int driver_id = jsonObject.get("driver_id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = driverDao.getImage(driver_id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("driverUpdate")) {
			String driverJson = jsonObject.get("driver").getAsString();
			System.out.println("driverJson = " + driverJson);
			Driver driver = gson.fromJson(driverJson, Driver.class);
			int count = 0;
			count = driverDao.update(driver);
			writeText(response, String.valueOf(count));
		} else if (action.equals("locationUpdate")) {
			String driverJson = jsonObject.get("driver").getAsString();
			System.out.println("driverJson = " + driverJson);
			Driver driver = gson.fromJson(driverJson, Driver.class);
			int count = 0;
			count = driverDao.locationupdate(driver);
			writeText(response, String.valueOf(count));
		} else if (action.equals("findById")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Driver driver = driverDao.findById(driver_id);
			writeText(response, gson.toJson(driver));
		} else if (action.equals("getUserInfo")) {
			String driver_email = jsonObject.get("email").getAsString();
			Driver driver = driverDao.getUserInfo(driver_email);
			writeText(response, gson.toJson(driver));
		} else if (action.equals("getInformation")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Driver driver = driverDao.getInformation(driver_id);
			writeText(response, gson.toJson(driver));
		} else if (action.equals("getBankInformation")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Driver driver = driverDao.getBankInformation(driver_id);
			writeText(response, gson.toJson(driver));
		}else if (action.equals("getLocation")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Driver driver = driverDao.getLocation(driver_id);
			writeText(response, gson.toJson(driver));
		} else if (action.equals("loginCheck")) {
			String driver_email = jsonObject.get("email").getAsString();
			String driver_password = jsonObject.get("password").getAsString();
			int driver = driverDao.loginCheck(driver_email, driver_password);
			writeText(response, String.valueOf(driver));
		} else if (action.equals("signUp")) {// 新增跟更新
			String driverJson = jsonObject.get("driver").getAsString();
			System.out.println("driverJson = " + driverJson);
			Driver driver = gson.fromJson(driverJson, Driver.class);// 一次轉回spot物件
			byte[] idFront = null;
			byte[] idBack = null;
			byte[] licenseFront = null;
			byte[] licenseBack = null;
			byte[] driverSecure = null;
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
			if (jsonObject.get("licenseFrontBase64") != null) {
				String licenseFrontBase64 = jsonObject.get("licenseFrontBase64").getAsString();
				if (licenseFrontBase64 != null && !licenseFrontBase64.isEmpty()) {
					licenseFront = Base64.getMimeDecoder().decode(licenseFrontBase64);
				}
			}
			if (jsonObject.get("licenseBackBase64") != null) {
				String licenseBackBase64 = jsonObject.get("licenseBackBase64").getAsString();
				if (licenseBackBase64 != null && !licenseBackBase64.isEmpty()) {
					licenseBack = Base64.getMimeDecoder().decode(licenseBackBase64);
				}
			}
			if (jsonObject.get("driverSecureBase64") != null) {
				String driverSecureBase64 = jsonObject.get("driverSecureBase64").getAsString();
				if (driverSecureBase64 != null && !driverSecureBase64.isEmpty()) {
					driverSecure = Base64.getMimeDecoder().decode(driverSecureBase64);
				}
			}
			if (jsonObject.get("userPhotoBase64") != null) {
				String userPhotoBase64 = jsonObject.get("userPhotoBase64").getAsString();
				if (userPhotoBase64 != null && !userPhotoBase64.isEmpty()) {
					userPhoto = Base64.getMimeDecoder().decode(userPhotoBase64);
				}
			}
			int count = 0;
			count = driverDao.signUp(driver, idFront, idBack, licenseFront, licenseBack, driverSecure, userPhoto);
			writeText(response, String.valueOf(count));// 轉成字串送回去使用者那邊insertfragment

		} else if (action.equals("getOrders")) {
			int driver_id = jsonObject.get("id").getAsInt();
			List<Order> orders = driverDao.getOrders(driver_id);
			writeText(response, gson.toJson(orders));
		} else if (action.equals("updateUserData")) {
			String driverJson = jsonObject.get("driver").getAsString();
			Driver driver = gson.fromJson(driverJson, Driver.class);
			int count = 0;
			count = driverDao.updateUserData(driver);
			writeText(response, String.valueOf(count));
		} else if (action.equals("updateUserBankData")) {
			String driverJson = jsonObject.get("driver").getAsString();
			Driver driver = gson.fromJson(driverJson, Driver.class);
			int count = 0;
			count = driverDao.updateUserBankData(driver);
			writeText(response, String.valueOf(count));
		} else if (action.equals("updateUserPhoto")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			String image = jsonObject.get("imageBase64").getAsString();
			byte[] userPhoto = null;
			if (image != null && !image.isEmpty()) {
				userPhoto = Base64.getMimeDecoder().decode(image);
			}
			int count = 0;
			count = driverDao.updateUserPhoto(driver_id, userPhoto);
			writeText(response, String.valueOf(count));
		} else if (action.equals("updateInsurance")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			String image = jsonObject.get("imageBase64").getAsString();
			String expireDate = jsonObject.get("expireDate").getAsString();
			byte[] insurancePhoto = null;
			if (image != null && !image.isEmpty()) {
				insurancePhoto = Base64.getMimeDecoder().decode(image);
			}
			int count = 0;
			count = driverDao.updateInsurance(driver_id, insurancePhoto, expireDate);
			writeText(response, String.valueOf(count));
		} else if (action.equals("updateId") || action.equals("updateDriverLicence")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			String imageFront = jsonObject.get("imageFront").getAsString();
			String imageBack = jsonObject.get("imageBack").getAsString();
			byte[] photoFront = null;
			byte[] photoBack = null;
			if (imageFront != null && !imageFront.isEmpty() && imageBack != null && !imageBack.isEmpty()) {
				photoFront = Base64.getMimeDecoder().decode(imageFront);
				photoBack = Base64.getMimeDecoder().decode(imageBack);
			}
			int count = 0;
			count = driverDao.updateTwoPhoto(driver_id, photoFront, photoBack, action);
			writeText(response, String.valueOf(count));
		} else if (action.equals("getStatus")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			String[] status = { "", "", "", "" };
			status = driverDao.getStatus(driver_id, status);
			writeText(response, gson.toJson(status));
		} else if (action.equals("findUserById")) {
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Driver driver = driverDao.findUserById(driver_id);
			writeText(response, gson.toJson(driver));
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
		if (driverDao == null) {
			driverDao = new DriverDaoMySqlImpl();
		}
		List<Driver> drivers = driverDao.getAll();
		writeText(response, new Gson().toJson(drivers));
	}

}