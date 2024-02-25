/**
 * The Admin class represents a owner in the Inventory Management System, extending the Person class.
 * Admin has all the proficiency in his Inventory Management System .
 * Admin performs the action of managing,updating supplier and customer details.
 * and interacts with the database for fetching the information.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 16 Feb 2024
 */
package com.Person;
import com.Order.*;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Admin extends Person{
	private String userName;
	private String fullName;
	private String eMail;
	public static Admin loggedInAdmin;
	static Payment payment = new PaymentClass();
	
	public Admin(int userID, String password,String userName,String eMail) {
		super(userID, password);
		this.userName = userName;
		this.eMail = eMail;
	}
	public Admin(int userID, String password,String userName,String fullName,String eMail) {
		super(userID, password);
		this.userName = userName;
		this.fullName = fullName;
		this.eMail = eMail;
		
	}
	public Admin() throws ClassNotFoundException, SQLException {
		fetchAdminData();
		Account account = new Account();
		account.adminLogin();
	}
	
	//getters and setters to retrive the information
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public static Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }

    public static void setLoggedInAdmin(Admin admin) {
        loggedInAdmin = admin;
    }

	@Override
	public String toString() {
		return "Admin \n userID = " + userID+"\nuserName = " + userName + "\nfullName=" + fullName + "\neMail=" + eMail + "\npassword=" + password;
	}

	public static List<Admin> adminList = new ArrayList<>();
	
	public void fetchAdminData() throws ClassNotFoundException, SQLException {
		MainClass.getConnection();
		String dataFetch = "SELECT ADMIN_ID,PASSWORD,USERNAME,FULL_NAME,EMAIL FROM DATABASE.Admin";
	    try {
	        Statement state = MainClass.con.createStatement();
	        ResultSet set = state.executeQuery(dataFetch);
	        while (set.next()) {
	            int DBuserid = set.getInt("admin_id");
	            String DBpassword = set.getString("password");
	            String DBuserName = set.getString("username");
	            String DBfullName = set.getString("full_name");
	            String DBemail = set.getString("email");
	          
				Admin ob = new Admin(DBuserid,DBpassword,DBuserName,DBfullName,DBemail);
				adminList.add(ob);
				
			}
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public static void adminMenu() throws SQLException, ClassNotFoundException,InputMismatchException {
        Scanner sc = new Scanner(System.in);
        boolean exitLogin = false;
        while(!exitLogin) {
        	System.out.println("------------------------------------------");
        	System.out.println("|  			 Management Menu		     |");
        	System.out.println("------------------------------------------");
        	System.out.println("|  1. Inventory Management               |");
        	System.out.println("|  2. Supplier Management                |");
        	System.out.println("|  3. Customer Management                |");
        	System.out.println("|  4. Profile Management                 |");
        	System.out.println("|  5. Order Management                   |");
        	System.out.println("|  6. Return to Previous Menu            |");
        	System.out.println("|  7. Exit                               |");
        	System.out.println("------------------------------------------");

	        System.out.println("Enter Your Choice");
	        int mainChoice = sc.nextInt();
	
	        switch (mainChoice) {
	            case 1:
	            	boolean exitInventoryLoop = false;
	            	while (!exitInventoryLoop) {
	            		Product.fetchProductdetails();
	            		System.out.println("------------------------------------------");
	            		System.out.println("|          Product Management            |");
	            		System.out.println("|----------------------------------------|");
	            		System.out.println("|  1. Add a Product                      |");
	            		System.out.println("|  2. Update a Product                   |");
	            		System.out.println("|  3. Display a Product                  |");
	            		System.out.println("|  4. Delete a Product                   |");
	            		System.out.println("|  5. Return to Previous Menu            |");
	            		System.out.println("|  6. Exit                               |");
	            		System.out.println("------------------------------------------");

	                    System.out.println("Enter Your Choice");
		                int inventoryChoice = sc.nextInt();
		                
		                switch (inventoryChoice) {
		                    case 1:
		                    	Product.addProduct();
		                        break;
		                    case 2:
		                    	Product.updateProduct();
		                        break;
		                    case 3:
		                    	Product.displayProduct();
		                        break;
		                    case 4:
		                    	Product.deleteProduct();
		                        break;
		                    case 5:
		                        System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
		                        int returnChoice = sc.nextInt();
		                        if (returnChoice == 1) {
		                        	System.out.println("Redirecting");
		                            exitInventoryLoop = true;
		                        } 
		                        else if (returnChoice == 2) {
		                            exitInventoryLoop = false;
		                            //exitLogin = true;
		                        }
		                        else {
		                            System.out.println("Invalid Choice.");
		                        }
		                        break;
		                    case 6:
		                    	System.out.println("Exiting The Application.\nThank You!");
		                        System.exit(0);
		                        break;
		                    default:
		                    	System.out.println("Invalid Choice.");
		                }
	            	}
                break;
            case 2:
            	boolean exitSupplierLoop = false;
            	while (!exitSupplierLoop) {
            		Supplier.fetchSupplierdetails();
            		System.out.println("------------------------------------------");
            		System.out.println("|         Supplier Management            |");
            		System.out.println("|----------------------------------------|");
            		System.out.println("|  1. Add a Supplier                     |");
            		System.out.println("|  2. Update a Supplier                  |");
            		System.out.println("|  3. Display a Supplier                 |");
            		//System.out.println("|  4. Delete a Supplier                  |");
            		System.out.println("|  4. Return to Previous Menu            |");
            		System.out.println("|  5. Exit                               |");
            		System.out.println("------------------------------------------");
            		System.out.println("Enter Your Choice");
	                int supplierChoice = sc.nextInt();
	                switch (supplierChoice) {
	                    case 1:
	                    	Supplier.addSupplier();
	                        break;
	                    case 2:
	                    	Supplier.adminUpdateSupplier();
	                        break;
	                    case 3:
	                    	Supplier.displaySupplier();
	                        break;
//	                    case 4:
//	                    	Supplier.deleteSupplier();
//	                        break;
	                    case 4:
	                        System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	                        int returnChoice = sc.nextInt();
	                        if (returnChoice == 1) {
	                        	System.out.println("Redirecting");
	                        	exitSupplierLoop = true;
	                        } 
	                        else if (returnChoice == 2) {
	                            exitSupplierLoop = false;
	                        } 
	                        else {
	                            System.out.println("Invalid Choice.");
	                        }
	                        break;
	                    case 5:
	                    	System.out.println("Exiting The Application.\nThank You!");
	                        System.exit(0);
	                        break;
	                    default:
	                    	System.out.println("Invalid Choice.");
	                }
            	}
                break;
            case 3:
            	boolean exitCustomer = false;
            	while(!exitCustomer) {
            		Customer.fetchCustomerDetails();
            		System.out.println("------------------------------------------");
            		System.out.println("|            Customer Management         |");
            		System.out.println("|----------------------------------------|");
            		System.out.println("|  1. Display All Customers              |");
            		System.out.println("|  2. View Customers Orders              |");
            		System.out.println("|  3. Return to Previous Menu            |");
            		System.out.println("|  4. Exit                               |");
            		System.out.println("------------------------------------------");
            		System.out.println("Enter Your Choice");
	                int cusChoice = sc.nextInt();
	                switch(cusChoice) {
	                case 1:
	                	Customer.displayAllCustomers();
	                	break;
	                case 2:
	                	Customer.viewOrders();
	                	break;
	                case 3:
	                	 System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	                	 int returnChoice = sc.nextInt();
	                     if (returnChoice == 1) {
	                         System.out.println("Redirecting");
	                         exitCustomer = true;
	                     } 
	                     else if (returnChoice == 2) {
	                         exitCustomer = false;
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
	                	System.out.println("Invalid Choice.Please enter a valid option.");
	                }
            	}
            	break;
            case 4:
            	boolean exitAdmin = false;
            	while(!exitAdmin) {
            		System.out.println("------------------------------------------");
            		System.out.println("|           Profile Management           |");
            		System.out.println("|----------------------------------------|");
            		System.out.println("|  1. Update Details                     |");
            		System.out.println("|  2. Return to Previous Menu            |");
            		System.out.println("|  3. Exit                               |");
            		System.out.println("------------------------------------------");
            		System.out.println("Enter Your Choice");
	                int profileChoice = sc.nextInt();
	                switch (profileChoice) {
	                case 1:
	                  	updateAdminDetails();
		                break;
//	                case 2:
//	                	displayAllAdmin();
//	                	break;
	                case 2:
	                	System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	                    int returnChoice = sc.nextInt();
	                    if (returnChoice == 1) {
	                    	System.out.println("Redirecting");
	                    	exitAdmin = true;
	                    } 
	                    else if (returnChoice == 2) {
	                        exitAdmin = false;
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
	                }
	                }
            	
                break;
            
                case 5:
                	boolean exitOrder = false;
                	while(!exitOrder) {
	            	Order.fetchOrderdetails();
	            	System.out.println("------------------------------------------");
	            	System.out.println("|           Order Management             |");
	            	System.out.println("|----------------------------------------|");
	            	System.out.println("|  1. Place a new Order                  |");
	            	System.out.println("|  2. Pay for an Order                   |");
	            	System.out.println("|  3. Cancel an Order                    |");
	            	System.out.println("|  4. View Order History                 |");
	            	System.out.println("|  5. Return to Previous Menu            |");
	            	System.out.println("|  6. Exit                               |");
	            	System.out.println("------------------------------------------");
	            	System.out.println("Enter Your Choice");
	            	int orderChoice = sc.nextInt();
	                switch (orderChoice) {
	                    case 1:
	                        Order.placeOrder();
	                        break;
	                    case 2:
	                        payment.adminPayment(loggedInAdmin.getUserID());
	                        break;
	                    case 3:
	                        Order.cancelOrder();
	                        break;
	                    case 4:
	                        Order.viewHistory();
	                        break;
	                    case 5:
	                        System.out.println("Do you want to return to the previous menu? (Enter 1 for Yes, 2 for No)");
	                        int returnChoice = sc.nextInt();
	                        if (returnChoice == 1) {
	                            System.out.println("Redirecting");
	                            exitOrder = true;
	                        } 
	                        else if (returnChoice == 2) {
	                            exitOrder = false;
	                        } 
	                        else {
	                            System.out.println("Invalid Choice.");
	                        }
	                        break;
	                    case 6:
	                        System.out.println("Exiting The Application.\nThank You!");
	                        System.exit(0);
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Please enter a valid option.");
	                        break;
	                }
                	}
                break;
            case 6:
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
            case 7:
                System.out.println("Exiting The Application.\nThank You!");
                System.exit(0);
                break;
            default:
            	System.out.println("Invalid choice. Please enter a valid option.");
            	break;
        }
        }
      }
    
	
	
	public static void adminUpdateToDB(Admin ob) throws SQLException{
		String update = "UPDATE DATABASE.Admin SET USERNAME=?, PASSWORD=?, EMAIL=? WHERE ADMIN_ID=?";
		PreparedStatement ps = MainClass.con.prepareStatement(update);
		ps.setString(1, ob.getUserName());
		ps.setString(2, ob.getPassword());
		ps.setString(3, ob.geteMail());
		ps.setInt(4, ob.getUserID());

		
		int isInserted = ps.executeUpdate();
		if(isInserted > 0) {
			System.out.println("Updated Successfully");
		}
		else {
			System.out.println("Not Updated");
		}
	}
	public static boolean findAdmin( int adminID) {
		 for (Admin ob : adminList) {
		        if (adminID == ob.getUserID()) {
		            return true;
		        }
		    }
		    return false;
		
	}
	public static void updateAdminDetails()throws SQLException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Admin Details");
		System.out.println("Enter the Admin ID");
		int adminID = sc.nextInt();
		sc.nextLine();
		boolean isAvailable = findAdmin(adminID);
		if(isAvailable) {
			System.out.println("Enter the Admin Password");
			String password = sc.nextLine();
			System.out.println("Enter the Admin Username");
			String userName = sc.nextLine();
			System.out.println("Enter the Admin Email");
			String email = sc.nextLine();
					
			Admin ob = new Admin(adminID,password,userName,email);
			adminUpdateToDB(ob);
		}
		else {
			System.out.println("Admin Not Found");
		}
	}
//	public static void displayAllAdmin() throws SQLException{
//		String display = "SELECT * from DATABASE.Admin";
//		Statement state = MainClass.con.createStatement();
//		ResultSet displayadmin = state.executeQuery(display);
//		System.out.println("Admin Details");
//		System.out.println("ADMIN_ID		USERNAME		PASSWORD		FULL_NAME		EMAIL");
//		while(displayadmin.next()) {
//			System.out.println(displayadmin.getInt(1)+"		"+displayadmin.getString(2)+"		"+displayadmin.getString(3)+"		"+displayadmin.getString(4)+"		"+displayadmin.getString(5));
//		}
	//}

}
