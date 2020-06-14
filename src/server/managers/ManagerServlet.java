package server.managers;

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

import server.drivers.Driver;
import server.drivers.DriverDaoMySqlImpl;
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
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = managerDao.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
		} else if (action.equals("getFilesStatus")) {
			int id = jsonObject.get("id").getAsInt();
			String jsonFilesStatus = managerDao.getFilesStatus(id);
			writeText(response, jsonFilesStatus);
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
