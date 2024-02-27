/**
 * The Account class provides methods for admin, supplier, and customer account management, including login and registration.
 * All the person in the System needs an account to use the services.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 17 Feb 2024
 */
package com.Person;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
	//Performs the login process for admin accounts.
	public static void adminLogin() throws SQLException, ClassNotFoundException,InputMismatchException {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter Your User Name:");
	    String userID = sc.next();
	    sc.nextLine();
	    System.out.println("Enter Your Password:");
	    String password = sc.nextLine();

	    int count = 0;
	    for (Admin admin : Admin.adminList) {
	        if (userID.equals(admin.getUserName()) && password.equals(admin.getPassword())) {
	            System.out.println("Login Successful");
	            System.out.println("Welcome " + admin.getFullName());
	            count++;
	            Admin.setLoggedInAdmin(admin); 
	            Admin.adminMenu(); //redirected to admin menu if registration is completed.
	            break;
	        }
	    }
	    if (count == 0) {
	        System.out.println("Invalid Credentials");
	    }
	}
	//Manages the registration of the supplier 
	public static void supplierRegister() throws InputMismatchException, ClassNotFoundException, SQLException{
		Scanner sc = new Scanner(System.in);
		boolean exitLogin = false;
		while(!exitLogin) {
			System.out.println("------------------------------------------");
			System.out.println("|           Supplier Management          |");
			System.out.println("|----------------------------------------|");
			System.out.println("|  1. Create a new Account               |");
			System.out.println("|  2. Login                              |");
			System.out.println("|  3. Return to Previous Menu            |");
			System.out.println("|  4. Exit                             	 |");
			System.out.println("------------------------------------------");
			System.out.println("Enter Your Choice:");
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				Supplier.addSupplier();
				break;
			case 2:
				supplierLogin();
				break;
			case 3:
				System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	            int returnChoice = sc.nextInt();
	            if (returnChoice == 1) {
	                System.out.println("Redirecting");
	                exitLogin = true;
	            } 
	            else if (returnChoice == 2) {
	            	exitLogin = false;
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
	            break;
			}
		}
	}
	//Performs the login process for supplier accounts.
	public static void supplierLogin() throws InputMismatchException, ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
	    System.out.println("Enter Your Company ID:");
	    int userID = sc.nextInt();
	    sc.nextLine();
	    System.out.println("Enter Your Password:");
	    String password = sc.nextLine();
	    int count = 0;
	    for (Supplier supplier : Supplier.supplierList) {
	        if (userID == supplier.getUserID() && password.equals(supplier.getPassword())) {
	            System.out.println("Login Successful");
	            System.out.println("Welcome " + supplier.getConPerson());
	            count++;
	            Supplier.setLoggedInSupplier(supplier); 
	            Supplier.supplierMenu();
	            break;
	        }
	    }

	    if (count == 0) {
	        System.out.println("Invalid Credentials");
	    }
	}
	//Manages the registration and login of customer accounts.
	public static void register() throws SQLException, ClassNotFoundException,InputMismatchException {
		Scanner sc = new Scanner(System.in);
		boolean exitLogin = false;
		while(!exitLogin) {
			System.out.println("------------------------------------------");
			System.out.println("|            Account Management          |");
			System.out.println("|----------------------------------------|");
			System.out.println("|  1. Create a new Account               |");
			System.out.println("|  2. Login                              |");
			System.out.println("|  3. Return to Previous Menu            |");
			System.out.println("|  4. Exit                             	 |");
			System.out.println("------------------------------------------");
			System.out.println("Enter Your Choice:");
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				newAccount();
				break;
			case 2:
				customerLogin(); // if customer has a account already.
				break;
			case 3:
				System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	            int returnChoice = sc.nextInt();
	            if (returnChoice == 1) {
	                System.out.println("Redirecting");
	                exitLogin = true;
	            } 
	            else if (returnChoice == 2) {
	            	exitLogin = false;
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
	            break;
			}
		}
	}
	// method to validate the format of a user name.
	public static boolean validateUserName(String userName) {
		  String regex = "^\\w{10,}";
	      Pattern pattern = Pattern.compile(regex);
	      Matcher matcher = pattern.matcher(userName);
	      if(matcher.matches()) {
	    	  return true;
	      }
	      else {
	    	  return false;
	      }
	}
	// method to validate the format of a password.
	public static boolean validatePassword(String password) {
		  String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[_@#$%&]).{8,}$";
	      Pattern pattern = Pattern.compile(regex);
	      Matcher matcher = pattern.matcher(password);
	      if(matcher.matches()) {
	    	  return true;
	      }
	      else {
	    	  return false;
	      }
	}
	// method to validate the format of a phone number.
	public static boolean validateNumber(Long conNum) {
		String conNumStr = String.valueOf(conNum);
		String regex = "^[6-9][0-9]{9}";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(conNumStr);
	    if(matcher.matches()) {
	      return true;
	    }
	    else {
	      return false;
	    }
	}
	// method to validate the format of an email address.
	public static boolean validateEmail(String eMail) {
		String regex = "[a-z0-9]+@[[a-z\\.]+]+";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(eMail);
	    if(matcher.matches()) {
	      return true;
	    }
	    else {
	      return false;
	    }
	}
	
	//method for creating a new customer account.
	public static void newAccount() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Your User Name:");
		String userName = sc.nextLine();
		while(!validateUserName(userName)) {
			System.out.println("Invalid User Name");
			System.out.println("User Name should contain minimum 10 chatacters,Alphanumeric and underscore is allowed\nPlease Enter valid User Name:");
			userName = sc.nextLine();
		}
		System.out.println("Enter Your Password:");
		String password = sc.nextLine();
		while(!validatePassword(password)) {
			System.out.println("Invalid Password");
			System.out.println("Password should contain minimum 8 chatacters,one Uppercase,one Lowercase, one number and one special character is allowed\nPlease Enter valid Password:");
			password = sc.nextLine();
		}
		System.out.println("Enter Your Full Name:");
		String fullName = sc.nextLine();
		System.out.println("Enter Your Contact Number:");
		Long conNum = sc.nextLong();
		while(!validateNumber(conNum)) {
			System.out.println("Invalid Number");
			System.out.println("Number should contain 10 digits.\nPlease enter valid number");
			conNum = sc.nextLong();
		}
		sc.nextLine();
		System.out.println("Enter Your E-Mail:");
		String eMail = sc.nextLine();
		while(!validateEmail(eMail)) {
			System.out.println("Invalid E-Mail ID");
			System.out.println("E-Mail should contain only lowercase.Please enter E-mail");
			eMail = sc.nextLine();
		}
		System.out.println("Enter Your Address:");
		String address = sc.nextLine();
		Customer ob = new Customer(userName,password,fullName,conNum,eMail,address);
		ob.customerAddToDB(ob);
		System.out.println("Account Created Successfully");
	}
	//Performs the login process for customer accounts.
	public static void customerLogin() throws SQLException {
		Scanner sc = new Scanner(System.in);
		Customer.fetchCustomerDetails();
		System.out.println("Enter the User Name:");
		String username = sc.nextLine();
		System.out.println("Enter Your Password:");
		String password = sc.nextLine();
		int count = 0;
	    for (Customer ob : Customer.customerList) {
	        if (username.equals(ob.getUserName()) && password.equals(ob.getPassword())) {
	            System.out.println("Login Successful");
	            System.out.println("Welcome " + ob.getFullName());
	            count++;
	            Customer.setLoggedInCustomer(ob); 
	            Customer.customerMenu();
	            break;
	        }
	    }
	    if (count == 0) {
	        System.out.println("Invalid Credentials");
	    }
	}
}
