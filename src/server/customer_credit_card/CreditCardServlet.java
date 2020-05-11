package server.customer_credit_card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/CreditCardServlet") //網址名稱

public class CreditCardServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	CreditCardDao creditCardDao = null;
	private Gson gson = new Gson();
	
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
		request.setCharacterEncoding("utf-8");
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString() , JsonObject.class);
		if(creditCardDao ==null) {
			creditCardDao = new CreditCardDaoMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();

		CreditCardServerMessage message = new CreditCardServerMessage(-1, "請求參數有誤");
			if(action.equals("credit_card_Insert") || action.equals("credit_card_Update")){
				if (jsonObject.get("customer_credit_card") != null) {String credit_cardJson = jsonObject.get("customer_credit_card").getAsString();
				System.out.print("credit_cardJson = " + credit_cardJson);
				CreditCard customer_credit_card = gson.fromJson(credit_cardJson, CreditCard.class);	
				
				if(action.equals("credit_card_Insert")) {
					message = creditCardDao.insert(customer_credit_card);
				}else if(action.equals("credit_card_Update")) {
					message = creditCardDao.update(customer_credit_card);
				}
			}
		}else if(action.equals("getAll")) {
			if (jsonObject.get("customerid") != null && jsonObject.get("creditcard_state") != null) {
				int customerid = jsonObject.get("customerid").getAsInt();
				int creditcard_state = jsonObject.get("creditcard_state").getAsInt();
				message = creditCardDao.getAllByCreditCardState(customerid, creditcard_state);
			}
		}
		writeText(response , gson.toJson(message));
	}
		
		private void writeText(HttpServletResponse response, String outText) throws IOException {
			response.setContentType(CONTENT_TYPE); //取得編碼方式
			PrintWriter out = response.getWriter(); //從response.getWriter()取得PrintWruter物件，夠過該物件對客戶端進行字元輸出
			out.print(outText);
			System.out.println("output: " + outText);	
		}
		
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if(creditCardDao == null) {
				creditCardDao = new CreditCardDaoMySqlImpl();
			}
			CreditCardServerMessage cardServerMessage = creditCardDao.getAllByCreditCardState(1, 1);
//			CreditCardServerMessage cardServerMessage = creditCardDao.insert(new CreditCard(0, 1, "1111111111111111", "12/23", "123", 1));
//			CreditCardServerMessage cardServerMessage = creditCardDao.update(new CreditCard(3, 1, "1111111111111111", "12/23", "123", 0));
			writeText(response , gson.toJson(cardServerMessage));
		}



}
