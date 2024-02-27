/**
 * The Notification class provides methods to display various notifications related to orders and payments.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 23 Feb 2024
 */
package com.Notification;

public class Notification {
	
	// method to notify the user that the order has been successfully placed.
   	public static void notifyOrder(){
		System.out.println("Order has been Successfully Placed.");
	}
   	
   	//method to notify the user that the payment was successful.
	public static void notifyPayment() {
		System.out.println("Payment successful! Thank you for your purchase.");
	}
	
	//method to notify the user that the payment for the order has been already completed.
	public static void notifyPaymentDone() {
		System.out.println("Payment for this order has already been completed.");
	}
}
