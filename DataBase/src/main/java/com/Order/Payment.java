/**
 * The abstract class Payment represents the payment process in the inventory management system.
 * It includes methods for handling customer and admin payments, and attributes related to the payment status.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 21 Feb 2024
 */
package com.Order;
import java.sql.SQLException;

public abstract class Payment {
	private String payment_status;
	
	//getters and setters to retrive the information
	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public Payment() {
		super();
	}
	//Handles the payment process for a customer with the specified username.
	public void customerPayment(String username) throws SQLException {
	
	}
	
	//Handles the payment process for an admin with the specified user ID.
	public void adminPayment(int userID) throws SQLException{
		
	}
}

