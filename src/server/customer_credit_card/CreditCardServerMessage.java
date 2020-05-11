package server.customer_credit_card;

import java.util.List;

public class CreditCardServerMessage {
	public CreditCardServerMessage(int responseCode, String message) {
		this.message = message;
		this.responseCode = responseCode;
	}
	
	public CreditCardServerMessage(int responseCode, String message, List<CreditCard> creditCardList) {
		this.message = message;
		this.responseCode = responseCode;
		this.creditCardList = creditCardList;
	}
	
	private int responseCode;
	private String message;
	private List<CreditCard> creditCardList;
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<CreditCard> getCreditCardList() {
		return creditCardList;
	}

	public void setCreditCardList(List<CreditCard> creditCardList) {
		this.creditCardList = creditCardList;
	}
	
	
}
