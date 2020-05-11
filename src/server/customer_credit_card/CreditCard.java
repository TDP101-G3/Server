package server.customer_credit_card;

public class CreditCard {

	private int creditCardId;
	private int customerId;
	private String creditCardNumber;
	private String creditCardDate;
	private String creditCardSecurityCode;
	private int creditCardState;
	
	public CreditCard(int creditCardId, int customerId, String creditCardNumber, String creditCardDate,
			String creditCardSecurityCode, int creditCardState) {
		super();
		this.creditCardId = creditCardId;
		this.customerId = customerId;
		this.creditCardNumber = creditCardNumber;
		this.creditCardDate = creditCardDate;
		this.creditCardSecurityCode = creditCardSecurityCode;
		this.creditCardState = creditCardState;
	}
	
	

	public int getCreditCardId() {
		return creditCardId;
	}



	public void setCreditCardId(int creditCardId) {
		this.creditCardId = creditCardId;
	}



	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public String getCreditCardNumber() {
		return creditCardNumber;
	}



	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}



	public String getCreditCardDate() {
		return creditCardDate;
	}



	public void setCreditCardDate(String creditCardDate) {
		this.creditCardDate = creditCardDate;
	}



	public String getCreditCardSecurityCode() {
		return creditCardSecurityCode;
	}



	public void setCreditCardSecurityCode(String creditCardSecurityCode) {
		this.creditCardSecurityCode = creditCardSecurityCode;
	}



	public int getCreditCardState() {
		return creditCardState;
	}



	public void setCreditCardState(int creditCardState) {
		this.creditCardState = creditCardState;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardId=" + creditCardId + ", customerId=" + customerId + ", creditCardNumber="
				+ creditCardNumber + ", creditCardDate=" + creditCardDate + ", creditCardSecurityCode="
				+ creditCardSecurityCode + ", creditCardState=" + creditCardState + "]";
	}

	

}
