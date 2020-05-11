package server.customer_credit_card;

import java.util.List;

public interface CreditCardDao {
	
	CreditCardServerMessage insert(CreditCard creditCard);
	
	CreditCardServerMessage update(CreditCard creditCard);
		
	CreditCardServerMessage getAllByCreditCardState(int customerId, int creditCardState);
}
