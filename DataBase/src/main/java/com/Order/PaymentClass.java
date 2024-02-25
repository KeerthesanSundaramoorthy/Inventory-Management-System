/**
 * The PaymentClass is a class that represents the payment processing for the orders, 
 * It extends the abstract class Payment and the payment method is different for them.
 * It provides methods to retrieve payment
 * status, update payment status in the database, and handle payment for both customers and admins.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 21 Feb 2024
 */
package com.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.Notification.Notification;
import com.Person.MainClass;


public class PaymentClass extends Payment{
	// it retrieves the payment status for a specific order ID from the order list.
	public static String getPayStatus(int orderID) {
	    for (Order order : Order.orderList) {
	        if (orderID == order.getOrderID()) {
	            return order.getPayment_status();
	        }
	    }
	    return "Order not found";
	}
	
	//it updates the payment status to 'PAID' in the database for a specific order ID.
	public void updateToDB(int orderID) throws SQLException {
		String update = "UPDATE DATABASE.ORDERS SET PAYMENT_STATUS = 'PAID' where ORDER_ID = ?";
		PreparedStatement ps = MainClass.con.prepareStatement(update);
		ps.setInt(1, orderID);
		ps.executeUpdate();
//		int isUpdated =
//		if(isUpdated > 0) {
//			System.out.println("Updated Successfully In Database");
//		}
//		else {
//			System.out.println("Not Updated");
//		}
	}
	
	//it initiates the payment process for an admin by proceeding with Cash On Delivery.
	public void adminPayment(int userID) throws SQLException{
		Scanner sc = new Scanner(System.in);
	    System.out.println("Proceeding to Cash On Delivery");
	    System.out.println("Enter the Order ID for payment");
	    int orderID = sc.nextInt();
	    sc.nextLine();
	    boolean orderFound = false;
	    String select = "SELECT PAYMENT_STATUS FROM DATABASE.ORDERS WHERE ORDER_ID = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	    ps.setInt(1, orderID);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	    	orderFound = true;
	        String paymentStatus = rs.getString("PAYMENT_STATUS");

	            if (paymentStatus.equalsIgnoreCase("UnPaid")) {
	                System.out.println("Payment for this order is pending.");
	                
	                int count = 1;
	                while(count<=3) {
		                System.out.println("Enter your Password:");
		                String password = sc.nextLine();
		                boolean isTrue = Order.validatePassword(userID, password);	             
		                if(isTrue) {
			                updateToDB(orderID);
			                Notification.notifyPayment();
			                break;
			            } 
		                else {
			            	   System.out.println("Incorrect Password.");
			               }
			               count++;
			               if(count == 4) {
			            	   //System.out.println("You Have Reached the Maximum Attempts.");
			            	   break;
			               }
	                }
	            }

		        else {
		        	Notification.notifyPaymentDone();	           
		        }
	     }
	
	    if (!orderFound) {
	        System.out.println("Order ID not found. Please enter a valid Order ID.");
	    }
	}
	//it initiates the payment process for a customer by proceeding with Cash On Delivery.
	public void customerPayment(String username) throws SQLException {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Proceeding to Cash On Delivery");
	    System.out.println("Enter the Order ID for payment");
	    int orderID = sc.nextInt();
	    sc.nextLine();
	    boolean orderFound = false;
	    String select = "SELECT PAYMENT_STATUS, PRICE FROM DATABASE.CUST_ORDER_TABLE WHERE ORDER_ID = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	    ps.setInt(1, orderID);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	    	orderFound = true;
	        String paymentStatus = rs.getString("PAYMENT_STATUS");

	            if (paymentStatus.equalsIgnoreCase("UnPaid")) {
	            	double orderPrice = rs.getDouble("PRICE");
	                System.out.println("Price of the order: " + orderPrice);
	                System.out.println("Payment for this order is pending.");
	            
	            	int count = 1;
	                while(count<=3) {
		                System.out.println("Enter your Password:");
		                String password = sc.nextLine();
		                boolean isTrue = Order.validateCusPassword(username, password);	             
		                if(isTrue) {
	                        updatePaymentStatus(orderID);
			                Notification.notifyPayment();
			                Invoice.generateInvoice(orderID,username);
			                break;
		                }
		                else {
		                	System.out.println("Incorrect Password.");
			               }
			               count++;
			           if(count == 4) {
			        	   //System.out.println("You Have Reached the Maximum Attempts.");
			            	break;
			           }
	                }
	            } 
	            else {
	                Notification.notifyPaymentDone();	            
	            }
	     }
	   
	    if (!orderFound) {
	        System.out.println("Order ID not found. Please enter a valid Order ID.");
	    }
	}
	 // it updates the payment status to 'Paid' in the database for a specific order ID.
	public static void updatePaymentStatus(int orderID) throws SQLException {
	    String updatePaymentQuery = "UPDATE DATABASE.CUST_ORDER_TABLE SET PAYMENT_STATUS = 'Paid' WHERE ORDER_ID = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(updatePaymentQuery);
	        ps.setInt(1, orderID);
	        ps.executeUpdate();
	}
}
