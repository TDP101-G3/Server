package server.drivers;

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
		} else if(action.equals("locationUpdate")) {
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
		} else if (action.equals("getLocation")){
			int driver_id = jsonObject.get("driver_id").getAsInt();
			Driver driver = driverDao.getLocation(driver_id);
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