package server.customer_credit_card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import server.main.ServiceLocator;


public class CreditCardDaoMySqlImpl  implements CreditCardDao{
	DataSource dataSource;
	
	public CreditCardDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();	
	}
	
	@Override
	public CreditCardServerMessage insert(CreditCard creditCard) {
		int count = 0;
		CreditCard checkCreditCard = getCreditCardByCardNumber(creditCard.getCreditCardNumber());
		
		if (checkCreditCard != null) {
			if (checkCreditCard.getCreditCardState() == 0) {
				creditCard.setCreditCardId(checkCreditCard.getCreditCardId());
				count = update(creditCard).getResponseCode();
			}
		} else {
			String sql = "INSERT INTO Customer_credit_card (customer_id , credit_card_number , credit_card_date , "
					+ "credit_card_securitycode , credit_card_state ) VALUES(?,?,?,?,?);";
			
			try (Connection connection = dataSource.getConnection();
			      PreparedStatement ps = connection.prepareStatement(sql);){
				ps.setInt(1, creditCard.getCustomerId());
				ps.setString(2, creditCard.getCreditCardNumber());
				ps.setString(3, creditCard.getCreditCardDate());
				ps.setString(4 , creditCard.getCreditCardSecurityCode());
				ps.setInt(5 , creditCard.getCreditCardState());
				count = ps.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (checkCreditCard != null && count == 0) {
			return new CreditCardServerMessage(2, "此卡已重複被新增");
		} else if (count == 0) {
			return new CreditCardServerMessage(0, "新增失敗");
		} else {
			return new CreditCardServerMessage(1, "新增成功");
		}
	}
	
	@Override
	public CreditCardServerMessage update(CreditCard creditCard) {
		int count = 0;
		String sql = "UPDATE Customer_credit_card SET credit_card_state = ? WHERE credit_card_id = ?;";
		try(Connection connection = dataSource.getConnection();
			      PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1,  creditCard.getCreditCardState());
			ps.setInt(2 , creditCard.getCreditCardId());
			count = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if (count == 0) {
			return new CreditCardServerMessage(0, "修改失敗");
		} else {
			return new CreditCardServerMessage(1, "修改成功");
		}
	}
	
	@Override 
	public CreditCardServerMessage getAllByCreditCardState(int customerId, int creditCardState){
		String sql = "SELECT credit_card_id, credit_card_number , credit_card_date , credit_card_securitycode "
				+ "FROM Customer_credit_card WHERE customer_id = ? AND credit_card_state = ?;";
		List<CreditCard> creditcardList = new ArrayList<CreditCard>();
		boolean hasCreditCard = false;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1,  customerId);
			ps.setInt(2 , creditCardState);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				hasCreditCard = true;
				int creditCardId = rs.getInt(1);
				String creditCardNumber = rs.getString(2);
				String creditCardDate = rs.getString(3);
				String creditCardSecurityCode = rs.getString(4);
				CreditCard creditCard = new CreditCard(creditCardId , customerId , creditCardNumber , creditCardDate , creditCardSecurityCode , creditCardState) ;
				creditcardList.add(creditCard);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if (hasCreditCard) {
			return new CreditCardServerMessage(1, "", creditcardList);
		} else {
			return new CreditCardServerMessage(0, "該會員無信用卡資料");
		}
 	}
	
	private CreditCard getCreditCardByCardNumber(String cardNumber) {
		CreditCard creditCard = null;
		String sql = "SELECT credit_card_id , customer_id , credit_card_number , credit_card_date , credit_card_securitycode , credit_card_state "
				+ "FROM Customer_credit_card WHERE credit_card_number = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, cardNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int creditCardId = rs.getInt(1);
				int customerId = rs.getInt(2);
				String creditCardNumber = rs.getString(3);
				String creditCardDate = rs.getString(4);
				String creditCardSecurityCode = rs.getString(5);
				int creditCardState = rs.getInt(6);
				creditCard = new CreditCard(
								creditCardId, 
								customerId, 
								creditCardNumber, 
								creditCardDate, 
								creditCardSecurityCode, 
								creditCardState
								) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return creditCard;
	}
	
}
