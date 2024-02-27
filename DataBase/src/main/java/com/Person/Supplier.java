/**
 * The Supplier class represents a supplier in the Inventory Management System, extending the Person class.
 * It includes attributes such as supplierName, contact person, contact number, email, and address.
 * Additionally, it provides functionalities of managing,updating supplier details
 * and interacts with the database for fetching the information.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 16 Feb 2024
 */
package com.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.Order.Order;

public class Supplier extends Person{
	private String supplierName;
	private String conPerson;
	private Long conNumber;
	private String email;
	private String address;
	private static Supplier loggedInSupplier;
	
	
	//getters and setters to retrive the information
	public Supplier getLoggedInSupplier() {
		return loggedInSupplier;
	}
	public static void setLoggedInSupplier(Supplier loggedInSupplier) {
		Supplier.loggedInSupplier = loggedInSupplier;
	}
	public Supplier() throws InputMismatchException, ClassNotFoundException, SQLException {
		fetchSupplierdetails();
		Account account = new Account();
		account.supplierRegister();
	}
	public Supplier(int userID,String password,String supplierName, String conPerson, Long conNumber, String email, String address) {
		super(userID, password);
		this.supplierName = supplierName;
		this.conPerson = conPerson;
		this.conNumber = conNumber;
		this.email = email;
		this.address = address;
	}
	
	public Supplier(int userID,String password, String conPerson, Long conNumber, String address) {
		super(userID, password);
		this.conPerson = conPerson;
		this.conNumber = conNumber;
		this.address = address;
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getConPerson() {
		return conPerson;
	}

	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
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

	@Override
	public String toString() {
		return "SupplierName = " + supplierName + "\nContact Person = " + conPerson + "\nContact Number = " + conNumber
				+ "\nEmail = " + email + "\nAddress = " + address;
	}
	
	static List<Supplier> supplierList = new ArrayList<>();
	
	//Displays the menu for supplier management, allowing options like updating details, returning to the previous menu, or exiting.
	public static void supplierMenu() throws SQLException, ClassNotFoundException,InputMismatchException {
		 Scanner sc = new Scanner(System.in);
	     boolean exitSupplier = false;
	     while(!exitSupplier) {
			System.out.println("------------------------------------------");
			System.out.println("|          Supplier Management           |");
			System.out.println("|----------------------------------------|");
			System.out.println("|  1. Update Details                     |");
			System.out.println("|  2. Return to Previous Menu            |");
	    	System.out.println("|  3. Exit                               |");
			System.out.println("------------------------------------------");
			System.out.println("Enter Your Choice");
            int loginChoice = sc.nextInt();
            switch (loginChoice) {
				case 1:
					updateSupplier();
					break;
				case 2:
	            	System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	                int returnChoice = sc.nextInt();
	                if (returnChoice == 1) {
	                	System.out.println("Redirecting");
	                	exitSupplier = true;
	                } 
	                else if (returnChoice == 2) {
	                	exitSupplier = false;
	                } 
	                else {
	                    System.out.println("Invalid Choice.");
	                }
	                break;
	            case 3:
	                System.out.println("Exiting The Application.\nThank You!");
	                System.exit(0);
	                break;
	            default:
	            	System.out.println("Invalid choice. Please enter a valid option.");
	            	break;
	        }
	     }
	}
	
	//Fetches supplier details from the database and populates the supplierList.
	public static void fetchSupplierdetails() throws ClassNotFoundException, SQLException {
		MainClass.getConnection();
		String dataFetch = "SELECT SUPPLIER_ID,COMPANY_NAME,CONTACT_PERSON,CONTACT_NUMBER,EMAIL,ADDRESS,SUPPLIER_PASSWORD FROM DATABASE.Supplier";
	    try {
	        Statement state = MainClass.con.createStatement();
	        ResultSet set = state.executeQuery(dataFetch);
	        while (set.next()) {
	            int DBsupplierID = set.getInt("SUPPLIER_ID");
	            String DBsupplierName = set.getString("COMPANY_NAME");
	            String DBPerson = set.getString("CONTACT_PERSON");
	            Long DBNumber = set.getLong("CONTACT_NUMBER");
	            String DBemail= set.getString("EMAIL");
	            String DBaddress = set.getString("ADDRESS");
	            String DBsupplierPassword = set.getString("SUPPLIER_PASSWORD");
	            
				Supplier ob = new Supplier(DBsupplierID,DBsupplierPassword,DBsupplierName,DBPerson,DBNumber,DBemail,DBaddress);
				supplierList.add(ob);
				
			}
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//Adds a new supplier to the database 
	public static void supplierAddToDatabase(Supplier supplier) throws SQLException{
		String insert = "Insert into DATABASE.Supplier values(?,?,?,?,?,?,?)";
			PreparedStatement ps = MainClass.con.prepareStatement(insert);
			ps.setInt(1,supplier.getUserID());
			ps.setString(2,supplier.getSupplierName());
			ps.setString(3, supplier.getConPerson());
			ps.setLong(4,supplier.getConNumber());
			ps.setString(5,supplier.getEmail());
			ps.setString(6,supplier.getAddress());
			ps.setString(7,supplier.getPassword());
			ps.executeUpdate();
			//System.out.println("Supplier added into the Database");
	}
	
	//Adds a new supplier to the database and the supplierList.
	public static void addSupplier() throws SQLException{
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the Supplier Details");
			System.out.println("Enter the Supplier ID");
			int supplierID = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter the Supplier Password");
			String password = sc.nextLine();
			while(!Account.validatePassword(password)) {
				System.out.println("Invalid Password");
				System.out.println("Password should contain minimum 8 chatacters,one Uppercase,one Lowercase, one number and one special character is allowed\nPlease Enter valid Password:");
				password = sc.nextLine();
			}
			System.out.println("Enter the Supplier Name");
			String supplierName = sc.nextLine();
			System.out.println("Enter the Contact Person");
			String conPerson = sc.nextLine();
			System.out.println("Enter the Contact Number");
			Long conNumber = sc.nextLong();
			sc.nextLine();
			while(!Account.validateNumber(conNumber)) {
				System.out.println("Invalid Number");
				System.out.println("Number should contain 10 digits.\nPlease enter valid number");
				conNumber = sc.nextLong();
				sc.nextLine();
			}
			System.out.println("Enter the Supplier Email");
			String eMail = sc.nextLine();
			while(!Account.validateEmail(eMail)) {
				System.out.println("Invalid E-Mail ID");
				System.out.println("E-Mail should contain only lowercase.Please enter E-mail");
				eMail = sc.nextLine();
			}
			System.out.println("Enter the Supplier Address");
			String address = sc.nextLine();
			
			Supplier ob = new Supplier(supplierID,password,supplierName,conPerson,conNumber,eMail,address);
			supplierList.add(ob);
			supplierAddToDatabase(ob);
			System.out.println("Account Created Successfully");
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("ID is Already used by existing User.Try Another ID.");
		}
	}
	
	//finds the supplier in list 
	public static boolean findSupplier(int supplierID) {
	    for (Supplier ob : supplierList) {
	        if (supplierID == ob.getUserID()) {
	            return true;
	        }
	    }
	    return false;
	}
	
	//Updates the supplier details in the database based on the provided supplier object.
	public static void supplierUpdateToDB(Supplier updateDB) throws SQLException{
		String update = "UPDATE DATABASE.Supplier set  CONTACT_PERSON=?, CONTACT_NUMBER=?, ADDRESS=?,SUPPLIER_PASSWORD = ? WHERE SUPPLIER_ID=?";
		PreparedStatement ps = MainClass.con.prepareStatement(update);
		ps.setString(1, updateDB.getConPerson());
		ps.setLong(2,updateDB.getConNumber());
		ps.setString(3,updateDB.getAddress());
		ps.setString(4,updateDB.getPassword());
		ps.setInt(5,updateDB.getUserID());
		
		int isUpdated = ps.executeUpdate();
		if(isUpdated > 0) {
			System.out.println("Updated Successfully");
		}
		else {
			System.out.println("Not Updated");
		}
	}
	
	//updates the supplier details done by admin
	public static void adminUpdateSupplier()throws SQLException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Supplier ID");
		int userID = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the Supplier Password");
		String password = sc.nextLine();
		while(!Account.validatePassword(password)) {
			System.out.println("Invalid Password");
			System.out.println("Password should contain minimum 8 chatacters,one Uppercase,one Lowercase, one number and one special character is allowed\nPlease Enter valid Password:");
			password = sc.nextLine();
		}
		System.out.println("Enter the Contact Person");
		String conPerson = sc.nextLine();
		System.out.println("Enter the Contact Number");
		Long conNumber = sc.nextLong();
		sc.nextLine();
		while(!Account.validateNumber(conNumber)) {
			System.out.println("Invalid Number");
			System.out.println("Number should contain 10 digits.\nPlease enter valid number");
			conNumber = sc.nextLong();
		}
		System.out.println("Enter the Supplier Address");
		String address = sc.nextLine();
		
		Supplier update = new Supplier(userID,password,conPerson,conNumber,address);
		supplierUpdateToDB(update);
	}
	
	//updates the supplier details done by supplier
	public static void updateSupplier() throws SQLException{
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the Supplier Password");
			String password = sc.nextLine();
			while(!Account.validatePassword(password)) {
				System.out.println("Invalid Password");
				System.out.println("Password should contain minimum 8 chatacters,one Uppercase,one Lowercase, one number and one special character is allowed\nPlease Enter valid Password:");
				password = sc.nextLine();
			}
			System.out.println("Enter the Contact Person");
			String conPerson = sc.nextLine();
			System.out.println("Enter the Contact Number");
			Long conNumber = sc.nextLong();
			sc.nextLine();
			while(!Account.validateNumber(conNumber)) {
				System.out.println("Invalid Number");
				System.out.println("Number should contain 10 digits.\nPlease enter valid number");
				conNumber = sc.nextLong();
				sc.nextLine();
			}
			System.out.println("Enter the Supplier Address");
			String address = sc.nextLine();
			
			Supplier update = new Supplier(loggedInSupplier.getUserID(),password,conPerson,conNumber,address);
			supplierUpdateToDB(update);
		}
		
	//Displays the details of all suppliers in a formatted manner.
	public static void displaySupplier() throws SQLException {
	    String display = "SELECT SUPPLIER_ID,COMPANY_NAME,CONTACT_PERSON,CONTACT_NUMBER,EMAIL,ADDRESS FROM DATABASE.Supplier ORDER BY SUPPLIER_ID ASC";
	    Statement state = MainClass.con.createStatement();
	    ResultSet displaysupplier = state.executeQuery(display);
	    System.out.println("Supplier Details");
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    System.out.printf("%-12s %-20s %-18s %-17s %-30s %-25s\n",
	            "SUPPLIER_ID", "COMPANY_NAME", "CONTACT_PERSON", "CONTACT_NUMBER", "EMAIL", "ADDRESS");
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

	    while (displaysupplier.next()) {
	        System.out.printf("%-12d %-20s %-18s %-17d %-30s %-25s\n",
	                displaysupplier.getInt(1),
	                displaysupplier.getString(2),
	                displaysupplier.getString(3),
	                displaysupplier.getLong(4),
	                displaysupplier.getString(5),
	                displaysupplier.getString(6));
	    }
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

}
