/**
 * The Customer class represents a customer in the inventory management system. It is extended from the Person class
 * and includes details of the customer.Customer buys a product from the admin.
 * It provides methods for customer-specific actions, such as searching for products,
 * placing orders, making payments, canceling orders,
 * updating profiles, and displaying order details.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 17 Feb 2024
 */package com.Person;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Order.Order;
import com.Order.Payment;
import com.Order.PaymentClass;
import com.Order.Product;
import com.Order.Stock;
import com.Search.*;

public class Customer extends Person{
	private String fullName;
	private String userName;
	private Long conNumber;
	private String email;
	private String address;
	private static Customer LoggedInCustomer;
	static Search search = new SearchClass();
	static Payment payment = new PaymentClass();
	public Customer(int userID, String password) {
		super(userID, password);
	}
	
	//getters and setters to retrive the information
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getConNumber() {
		return conNumber;
	}

	public void setConNumber(Long conNumber) {
		this.conNumber = conNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public static Customer getLoggedInCustomer() {
		return LoggedInCustomer;
	}
	public static void setLoggedInCustomer(Customer customer) {
		LoggedInCustomer = customer;
	}

	public Customer() throws ClassNotFoundException, SQLException {
		MainClass.getConnection();
		Account account = new Account();
		account.register();
	}

	public Customer( String userName,String password,String fullName, Long conNumber, String email, String address) {
		this.password = password;
		this.fullName = fullName;
		this.userName = userName;
		this.conNumber = conNumber;
		this.email = email;
		this.address = address;
	}
	public static List<Customer> customerList = new ArrayList<>();

    //Fetches customer details from the database and populates the customerList.
	public static void fetchCustomerDetails() throws SQLException {
		String dataFetch = "SELECT CUSTOMER_ID,PASSWORD,FULLNAME,USERNAME,CONNUMBER,EMAIL,ADDRESS FROM DATABASE.Cus";
		Statement state = MainClass.con.createStatement();
        ResultSet set = state.executeQuery(dataFetch);
        while (set.next()) {
            int DBcustomerID = set.getInt("CUSTOMER_ID");
            String DBpassword = set.getString("PASSWORD");
            String DBfullName = set.getString("FULLNAME");
            String DBuserName = set.getString("USERNAME");
            Long DBnumber= set.getLong("CONNUMBER");
            String DBemail= set.getString("EMAIL");
            String DBaddress = set.getString("ADDRESS");
            
            Customer ob = new Customer(DBuserName,DBpassword,DBfullName,DBnumber,DBemail,DBaddress);
            customerList.add(ob);
        }
	}
	//Adds a new customer to the database and the customerList.
	public static void customerAddToDB( Customer customer) throws SQLException {
		String insert = "Insert into DATABASE.cus(PASSWORD,FULLNAME,USERNAME,CONNUMBER,EMAIL,ADDRESS) values(?,?,?,?,?,?)";
		PreparedStatement ps = MainClass.con.prepareStatement(insert);
		ps.setString(1,customer.getPassword());
		ps.setString(2,customer.getFullName());
		ps.setString(3, customer.getUserName());
		ps.setLong(4,customer.getConNumber());
		ps.setString(5,customer.getEmail());
		ps.setString(6,customer.getAddress());
		ps.executeUpdate();
	}
	
	// Displays the main menu options for customers, allowing them to perform various actions.
	public static void customerMenu() throws SQLException {
		Scanner sc = new Scanner(System.in);
		boolean exitCustomer = false;
			while(!exitCustomer) {
				System.out.println("----------------------------------------");
				System.out.println("|         Main Menu Options            |");
				System.out.println("|--------------------------------------|");
				System.out.println("|  1. Search a Product                 |");
				System.out.println("|  2. Place a Order                    |");
				System.out.println("|  3. Pay for a Order                  |");
				System.out.println("|  4. Cancel a Order                   |");
				System.out.println("|  5. Update Profile                   |");
				System.out.println("|  6. Return to Previous Menu          |");
				System.out.println("|  7. Exit                             |");
				System.out.println("----------------------------------------");

				System.out.println("Enter Your Choice");
				int choice = sc.nextInt();
					switch(choice) {
					case 1:
						searchProduct();
						break;
					case 2:
						Order.cusPlaceOrder();
						break;
					case 3:
						payment.customerPayment(LoggedInCustomer.getUserName());
						break;
					case 4:
						Order.cancelCusOrder();
						break;
					case 5:
						updateProfile();
						break;
					case 6:
						System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
			            int returnChoice = sc.nextInt();
			            if (returnChoice == 1) {
			            	System.out.println("Redirecting");
			            	exitCustomer = true;
			            } 
			            else if (returnChoice == 2) {
			                exitCustomer = false;
			                //exitLogin = true;
			            } 
			            else {
			                System.out.println("Invalid Choice.");
			            }
			            break;
			       case 7:
			    	    System.out.println("Exiting The Application.\nThank You!");
	                	System.exit(0);
	                	break;
			       default:
			       	    System.out.println("Invalid choice. Please enter a valid option.");
					}
			}
	}
	
	//Initiates a product search based on customer input.
	public static void searchProduct() throws SQLException {
		boolean exitSearch = false;
		while(!exitSearch)
			try {
				{
					System.out.println("-----------------------------------------");
					System.out.println("|           Product Search              |");
					System.out.println("|---------------------------------------|");
					System.out.println("|  1. Search by Price                   |");
					System.out.println("|  2. Search by Category                |");
					System.out.println("|  3. Return to Previous Menu           |");
					System.out.println("|  4. Exit                              |");
					System.out.println("-----------------------------------------");

					Scanner sc = new Scanner(System.in);
				    System.out.println("Enter Your Choice");
				    int choice = sc.nextInt();
				    switch(choice) {
						case 1:
						try {
							search.searchByPrice();
						} 
						catch (Exception e) {
							e.printStackTrace();
						}
							break;
						case 2:
							search.searchByCategory();
							break;
						case 3:
							System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
				            int returnChoice = sc.nextInt();
				            if (returnChoice == 1) {
				            	System.out.println("Redirecting");
				            	exitSearch = true;
				            } 
				            else if (returnChoice == 2) {
				                exitSearch = false;
				                //exitLogin = true;
				            } 
				            else {
				                System.out.println("Invalid Choice.");
				            }
				            break;
				       case 4:
				    	   	System.out.println("Exiting The Application.\nThank You!");
		                	System.exit(0);
		                	break;
				       default:
				       	System.out.println("Invalid choice. Please enter a valid option.");
						}
				    }
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	//Updates the customer's order details in the database and adjusts the stock quantity.
	public static void updateCusOrderToDB(int productID, int quantity, String location, double totalPrice, String UserName, String payment_status) throws SQLException {
		String insert = "INSERT INTO DATABASE.CUST_ORDER_TABLE VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = MainClass.con.prepareStatement(insert);
		ps.setInt(1, 100);
		ps.setDate(2, Date.valueOf(LocalDate.now()));
		ps.setInt(3, productID);
		ps.setInt(4, quantity);
		ps.setString(5, location);
		ps.setDouble(6, totalPrice);
		ps.setString(7, payment_status);
		ps.setString(8, LoggedInCustomer.getUserName()); // Assuming getUserName returns the username
		ps.setString(9, "Confirmed");
		ps.executeUpdate();

    	
	    String updateStockQuery = "UPDATE DATABASE.Stocks SET STOCK_QUANTITY = STOCK_QUANTITY - ? WHERE PRODUCT_ID = ?";
	    PreparedStatement ps1 = MainClass.con.prepareStatement(updateStockQuery);
	    	ps1.setInt(1, quantity);
	    	ps1.setInt(2, productID);
	    	ps1.executeUpdate();
	}
//	    int isUpdated1 = ps.executeUpdate();
//	    if (isUpdated > 0) {
//	        System.out.println("Updated Successfully");
//	    }
//	    else {
//	        System.out.println("Not Updated");
//	    }
	
	//Updates the customer's profile details in the database.
	public static void updateProfile()throws SQLException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Password");
		String password = sc.nextLine();
			while(!Account.validatePassword(password)) {
				System.out.println("Invalid Password");
				System.out.println("Password should contain minimum 8 chatacters,one Uppercase,one Lowercase, one number and one special character is allowed\nPlease Enter valid Password:");
				password = sc.nextLine();
			}
					
			System.out.println("Enter Your Contact Number:");
			Long conNum = sc.nextLong();
			while(!Account.validateNumber(conNum)) {
				System.out.println("Invalid Number");
				System.out.println("Number should contain 10 digits.\nPlease enter valid number");
				conNum = sc.nextLong();
			}
			sc.nextLine();
			System.out.println("Enter Your E-Mail");
			String eMail = sc.nextLine();
			while(!Account.validateEmail(eMail)) {
				System.out.println("Invalid E-Mail ID");
				System.out.println("E-Mail should contain only lowercase.Please enter E-mail");
				eMail = sc.nextLine();
			}
			System.out.println("Enter the  Address");
			String address = sc.nextLine();
			updateCusToDB(LoggedInCustomer.getUserName(),password,conNum,eMail,address);
			System.out.println("Updated Successfully");
	}
	
	//Updates the customer's details in the database.
	public static void updateCusToDB(String userName, String password,Long conNum, String email, String address) throws SQLException {
	    String update = "UPDATE DATABASE.CUS SET PASSWORD = ?, CONNUMBER = ?, EMAIL = ?, ADDRESS = ? WHERE USERNAME = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(update);
	        ps.setString(1, password);
	        ps.setDouble(2, conNum);
	        ps.setString(3, email);
	        ps.setString(4, address);
	        ps.setString(5, userName);
	        ps.executeUpdate();
	   }
	
	//Displays details of all customers in a formatted manner.
	public static void displayAllCustomers() throws SQLException{
		String display = "SELECT CUSTOMER_ID,FULLNAME,USERNAME,CONNUMBER,EMAIL,ADDRESS from DATABASE.cus";
		Statement state = MainClass.con.createStatement();
		ResultSet display1 = state.executeQuery(display);
		System.out.println("Customer Details");
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-15s %-20s %-15s %-15s %-30s %-20s\n",
		        "CUSTOMER_ID", "FULLNAME", "USERNAME", "CONNUMBER", "EMAIL", "ADDRESS");
		System.out.println("----------------------------------------------------------------------------------------------------------------------");

		while (display1.next()) {
		    System.out.printf("%-15d %-20s %-15s %-15d %-30s %-20s\n",
		            display1.getInt(1),
		            display1.getString(2),
		            display1.getString(3),
		            display1.getLong(4),
		            display1.getString(5),
		            display1.getString(6));
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	 //Displays details of customer orders
	public static void viewOrders() throws SQLException {
		String select = "Select * from DATABASE.CUST_ORDER_TABLE";
		Statement state = MainClass.con.createStatement();
		ResultSet display = state.executeQuery(select);
		System.out.println("Customer Order Details");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s %-15s %-12s %-8s %-15s %-10s %-10s %-20s %-15s\n",
		        "ORDER_ID", "ORDER_DATE", "PRODUCT_ID", "QUANTITY", "LOCATION", "PRICE", "PAYMENT_STATUS", "CUSTOMER_NAME", "ORDER_STATUS");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

		while (display.next()) {
		    System.out.printf("%-10s %-15s %-12s %-8s %-15s %-10.2f %-15s %-20s %-15s\n",
		            display.getInt(1), display.getDate(2).toString(), display.getInt(3), display.getInt(4),
		            display.getString(5), display.getDouble(6), display.getString(7), display.getString(8), display.getString(9));
		}

		System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
	}
}
		
		
	
	

	

