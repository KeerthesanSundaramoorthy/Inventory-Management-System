/**
 * The MainClass serves as the entry point for the ABC Inventory Management System.
 * It provides a menu for users to choose their role (Admin, Supplier, Customer) or exit the application.
 * The class establishes a connection to the database and handles all the user.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 17 Feb 2024
 * */
package com.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MainClass {
	private static boolean exit = false;
	static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	static String username = "system";
	static String password = "keerthisan";
	public static Connection con;
	
	//Establishes a connection to the Oracle database.
	public static void getConnection() throws ClassNotFoundException, SQLException {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    con = DriverManager.getConnection(URL, username, password);
	}
	//main method
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Scanner sc = new Scanner(System.in);
		while(!exit) {
			try {
				System.out.println("-----------------------------------");
				System.out.println("|  Welcome to the ABC Inventory   |");
				System.out.println("|        Management System        |");
				System.out.println("-----------------------------------");
				System.out.println("|     Enter Application as:       |");
				System.out.println("|  1. Admin                       |");
				System.out.println("|  2. Supplier                    |");
				System.out.println("|  3. Customer                    |");
				System.out.println("|  4. Exit                        |");
				System.out.println("-----------------------------------");
				System.out.println("Enter Your Choice");
				int choice = sc.nextInt();
			    switch (choice) {
			    case 1:
			       Admin admin = new Admin();
			       //Account.adminLogin();
			       break;
			            
			    case 2:
				   Supplier supplier = new Supplier();
				   
				   break;
				
				case 3:
					Customer customer  = new Customer();
					break;
				case 4:
					System.out.println("Exiting The Application.\nThank You!");
	                System.exit(0);
					break;
	            default:
	                System.out.println("Invalid choice. Please enter a valid option.");
	                break;
				}
			}
			catch (InputMismatchException e) {
                System.out.println("Invalid input.Please enter an integer.");
                sc.nextLine();
			}
		}
	}
}
