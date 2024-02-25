/**
 * The Person class represents all the users of the Inventory Management System,all the users are extending the Person class.
 * It has the attributes userID and Password which is common for all the users.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 16 Feb 2024
 */
package com.Person;

import java.util.Scanner;

public class Person {
	//public static Object ob;
	public int userID;
	protected String password;
	
	public Person(){
		
	}
	public Person(int userID, String password) {
		this.userID = userID;
		this.password = password;
		
	}
	//getters and setters to retrive the information
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
