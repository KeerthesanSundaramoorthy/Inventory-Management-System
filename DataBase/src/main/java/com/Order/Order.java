/**
 * The Order class represents an order in the inventory management system. 
 * The order can be placed by the sAdmin as well as the customer.
 * It contains methods and attributes
 * related to order processing, such as order details, payment status, and order history.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 20 Feb 2024
 */
package com.Order;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.Notification.Notification;
import com.Person.Admin;
import com.Person.Customer;
import com.Person.MainClass;


public class Order {
	private int productID;
	private String location;
	private double price;
	private String username;
	private Payment payStatus;
	private String payment_status;
	
	
	private int orderID;
	private Date orderDate;
	private int supplierID;
	private String productName;
	private Date deliveryDate;
	private int quantity;
	private int adminID;
	private static String orderStatus;
	
		
	public Order(int orderID,Date orderDate, int supplierID, String productName, Date deliveryDate,
			int quantity, int adminID,String orderStatus, String paymentStatus) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.supplierID = supplierID;
		this.productName = productName;
		this.deliveryDate = deliveryDate;
		this.quantity = quantity;
		this.adminID = adminID;
		this.orderStatus = orderStatus;
		this.payment_status = paymentStatus;
	}
	
	public Order(String productName, int quantity, int supplierID) {
		this.productName = productName;
		this.quantity = quantity;
		this.supplierID = supplierID;
		this.orderDate =Date.valueOf(LocalDate.now());
		this.deliveryDate = Date.valueOf(LocalDate.now().plusDays(4));
	}
	
	public Order(String productName, int quantity, int supplierID, int userID, String payment_status) {
		this.productName = productName;
		this.quantity = quantity;
		this.supplierID = supplierID;
		this.adminID = userID;
		this.orderDate =Date.valueOf(LocalDate.now());
		this.deliveryDate = Date.valueOf(LocalDate.now().plusDays(4));
		this.payment_status = payment_status;
	}

	public Order(int productID, int quantity, String location,double price,String username,Payment payStatus) {
		this.productID = productID;
		this.quantity = quantity;
		this.location =  location;
		this.price = price;
		this.orderDate =Date.valueOf(LocalDate.now());
		this.username = username;
		this.payStatus = payStatus;
	}
	
	//getters and setters to retrive the information
	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAdminID() {
		return adminID;
	}
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
	
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
	public static String getOrderStatus() {
        return orderStatus;
    }
	@Override
	public String toString() {
		return "OrderID = " + orderID + "\nOrder Date = " + orderDate + "\nSupplier ID = " + supplierID + "\nProduct Name = "
				+ productName + "\nDelivery Date=" + deliveryDate + "\nProduct Quantity = " + quantity +"\nAdmin ID = "+adminID+"\nOrder Status = "+orderStatus;
	}
	static List<Order> orderList = new ArrayList<>();
	
	// it Fetches the order details from the database and populates the orderList.
    
	public static void fetchOrderdetails() throws SQLException{
		String dataFetch = "SELECT * from DATABASE.Orders";
		Statement state = MainClass.con.createStatement();
        ResultSet set = state.executeQuery(dataFetch);
        while(set.next()) {
        	int DBorderID = set.getInt("ORDER_ID");
        	Date DBorderDate = set.getDate("ORDER_DATE");
        	int DBsupplierID = set.getInt("SUPPLIER_ID");
        	String DBproductName = set.getString("PRODUCT_Name");
        	Date DBdeliveryDate = set.getDate("DELIVERY_DATE");
        	int DBquantity = set.getInt("QUANTITY");
        	int DBadminID = set.getInt("ADMIN_ID");
        	String DBorderStatus = set.getString("ORDER_STATUS");
        	String DBPaymentStatus = set.getString("PAYMENT_STATUS");
        	
        	Order ob = new Order(DBorderID,DBorderDate,DBsupplierID,DBproductName,DBdeliveryDate,DBquantity,DBadminID,DBorderStatus,DBPaymentStatus);
        	orderList.add(ob);
        }
    }
	// it adds an order to the database.
	public static void addOrderToDB(Order ob) throws SQLException{
		String insert = "Insert into DATABASE.Orders values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = MainClass.con.prepareStatement(insert);
		ps.setInt(1,ob.getOrderID());
		ps.setDate(2,ob.getOrderDate());
		ps.setInt(3, ob.getSupplierID());
        ps.setString(4, ob.getProductName());
        ps.setDate(5, ob.getDeliveryDate());
        ps.setInt(6, ob.getQuantity());
        ps.setInt(7, ob.getAdminID());
        ps.setString(8, getOrderStatus());
        ps.setString(9,ob.getPayment_status());
		
        ps.executeUpdate();
        //int isInserted =
//        if(isInserted > 0 ) {
//        	System.out.println("Order added to the Data base successfully.");
//        } 
//        else {
//            System.out.println("Order Not Added to Data base");
//        }
		
	}
	
	//Places a new order, allowing the admin to proceed to online payment or COD.
	public static void placeOrder() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Name");
		String productName = sc.nextLine();
		System.out.println("Enter the Product Quantity");
		int quantity = sc.nextInt();
		System.out.println("Enter the Supplier ID");
		int supplierID = sc.nextInt();
		Admin loggedInAdmin = Admin.getLoggedInAdmin();
		System.out.println("Do you want to continue to Payment (1-YES, 2-No)");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Proceeding to Online Payment");
                int count = 1;
                while(count<=3) {
	                System.out.println("Enter your Password:");
	                String password = sc.nextLine();
	               
	                boolean isTrue = validatePassword(loggedInAdmin.getUserID(), password);
	                if(isTrue) {
	            	    String payment_status = "PAID";
	               
	            	    Notification.notifyPayment();
		                
		                Order ob = new Order(productName, quantity, supplierID, loggedInAdmin.getUserID(),payment_status);
		        		orderList.add(ob);
		        		
		        		ob.setOrderStatus("Confirmed");
		        		Notification.notifyOrder();
		        	    addOrderToDB(ob);
		        	    orderList.add(ob);
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
        	    
                break;
            case 2:
            	String payment_Status = "UNPAID";
            	Notification.notifyOrder();
            	Order ob1 = new Order(productName, quantity, supplierID, loggedInAdmin.getUserID(),payment_Status);
            	addOrderToDB(ob1);
        		orderList.add(ob1);
        		
        		//exitOrder = true;
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    } 
		//Validates the admin password during the payment process for a order.
	static boolean validatePassword(int userID, String password) throws SQLException {
	    for (Admin admin : Admin.adminList) {
	        if (userID == admin.getUserID() && password.equals(admin.getPassword())) {
	            return true;
	        }
	    }
	    return false;
	}
	//checks the payment status for the order id 
	private static boolean checkPaymentStatus(int orderId) throws SQLException {
	    String select = "SELECT PAYMENT_STATUS FROM DATABASE.ORDERS WHERE ORDER_ID = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	    ps.setInt(1, orderId);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
            String paymentStatus = rs.getString("PAYMENT_STATUS");

            if ("paid".equalsIgnoreCase(paymentStatus)) {
                return true;
            } 
            else {
                return false;
            }
        } 
	    else {
            // If the order ID is not found, you might want to handle this case accordingly.
            System.out.println("Order ID not found.");
            return false;
        }
	}
	
	//Finds an order in the orderList based on the order ID.
	public static boolean findOrderID(int orderId) {
		for(Order ob:orderList) {
			if(orderId == ob.getOrderID()) {
				return true;
			}			
		}
		return false;
	}
	
	//Cancels an order in the orderList based on the order ID.
	public static boolean cancelOrderID(int orderId) {
		for(Order ob : orderList) {
			if(orderId == ob.getOrderID()) {
				ob.setOrderStatus("Cancelled");
				return true;
			}			
		}
		return false;
	}
	
	//Updates the order status in the database for a canceled order.
	public static void updateOrderToDB(int orderId) throws SQLException {
		String update = "Update DATABASE.ORDERS set ORDER_STATUS ='Cancelled' where ORDER_ID=?";
		PreparedStatement ps = MainClass.con.prepareStatement(update);
		ps.setInt(1, orderId);
		ps.execute();
	}
	 //Cancels an order, handling payment status and database updates.
	public static void cancelOrder() throws SQLException {
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter the Order ID to cancel:");
	    int orderId = sc.nextInt();

	    // Check if the order exists
	    boolean isAvailable = findOrderID(orderId);

	    if (isAvailable) {
	        // Check the payment status
	        boolean isPaid = checkPaymentStatus(orderId);

	        if (isPaid) {
	            // If paid, attempt to cancel and refund
	            boolean isCanceled = cancelOrderID(orderId);

	            if (isCanceled) {
	                System.out.println("Order Cancelled and Refunded Initiated.");
	                updateOrderToDB(orderId);  // Update order status in the database
	            } else {
	                System.out.println("Failed to cancel the order. Please try again later.");
	            }
	        } 
	        else {
	            // If unpaid, cancel the order without refund
	            boolean isCancelled = cancelOrderID(orderId);

	            if (isCancelled) {
	                System.out.println("Order cancelled successfully.");
	                updateOrderToDB(orderId);  // Update order status in the database
	            } 
	            else {
	                System.out.println("Failed to cancel the order. Please try again later.");
	            }
	        }
	    } 
	    else {
	        System.out.println("Order with ID not found.");
	    }
	}
// method to view the order history by the admin from the database
	public static void viewHistory() throws SQLException {
		String display = "SELECT * from DATABASE.Orders ORDER BY ORDER_ID DESC";
		Statement state = MainClass.con.createStatement();
		ResultSet displayOrder = state.executeQuery(display);
		System.out.println("Order History");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s %-12s %-12s %-20s %-15s %-8s %-8s %-15s %-15s\n", "ORDER_ID", "ORDER_DATE", "SUPPLIER_ID", "PRODUCT_NAME", "DELIVERY_DATE", "QUANTITY", "ADMIN_ID", "ORDER_STATUS", "PAYMENT_STATUS");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

		while (displayOrder.next()) {
			System.out.printf("%-10d %-12tF %-12d %-20s %-15tF %-8d %-8d %-15s %-15s\n",
			        displayOrder.getInt(1), 
			        displayOrder.getDate(2), 
			        displayOrder.getInt(3),
			        displayOrder.getString(4), 
			        displayOrder.getDate(5), 
			        displayOrder.getInt(6),
			        displayOrder.getInt(7), 
			        displayOrder.getString(8),
			        displayOrder.getString(9));
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
}
	
	//method to place the order for an customer using the online and COD payment options
	public static void cusPlaceOrder() throws SQLException {
		Scanner sc = new Scanner(System.in);
		try {
		    Stock.fetchStockDetails();
		    boolean exitOrder = false;
		    Customer LoggedInCustomer = Customer.getLoggedInCustomer(); 

		    while (!exitOrder) {
		    	Stock.displayStock();
		        System.out.println("Enter the Product ID");
		        int productID = sc.nextInt();
		        boolean productFound = false; 

		        for (Stock stock : Stock.stockList) {
		            if (productID == stock.getProductID()) {
		                productFound = true;
		                boolean quantityAvailable = false;

		                while (!quantityAvailable) {
		                    System.out.println("Enter the Quantity to Buy");
		                    int quantity = sc.nextInt();
		                    sc.nextLine();

		                    if (quantity > 0 && quantity <= stock.getStockQuantity()) {
		                        quantityAvailable = true; // Set the flag to true when the quantity is within bounds
		                        System.out.println("Enter the Location");
		                        String location = sc.nextLine();
		                        double totalPrice = quantity * getProductPrice(productID);
		                        System.out.println("Total Cost for the Order is " + totalPrice);
		                        int val = stock.getStockQuantity() - quantity;
                                //System.out.println(val);
                                stock.setStockQuantity(val);
		                        System.out.println("Do you want to continue to Payment (1-YES, 2-No)");
		                        int choice = sc.nextInt();
		                        sc.nextLine();

		                        switch (choice) {
		                            case 1:
		                            	System.out.println("Proceeding to Online Payment");
		                                int count = 1;
		                                while(count<=3) {
		                	                System.out.println("Enter your Password:");
		                	                String password = sc.nextLine();
		                	               
		                	                boolean isTrue = validateCusPassword(LoggedInCustomer.getUserName(), password);
		                	                if(isTrue) {
		                                
				                                String payment_status = "Paid";
				        	            	    Notification.notifyPayment();
				                                Notification.notifyOrder();				                                
				                                Customer.updateCusOrderToDB(productID,quantity,location,totalPrice,LoggedInCustomer.getUserName(),payment_status);
				                                int orderID = getOrderIdFromDatabase();
				                                System.out.println("Order ID: " + orderID);
				                                System.out.println("The Delivery Date is "+getDeliveryDate(orderID));
				                                Invoice.generateInvoice(orderID,LoggedInCustomer.getUserName());
				                                exitOrder = true;
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
		                		               break;
		                	            }
		                                break;
		                            case 2:
		                            	String payment_Status = "UnPaid";
		                            	Notification.notifyOrder();	
		                            	Customer.updateCusOrderToDB(productID,quantity,location,totalPrice,LoggedInCustomer.getUserName(),payment_Status);
		                            	int orderId = getOrderIdFromDatabase();
		                                System.out.println("Order ID: " + orderId);
		                                System.out.println("The Delivery Date is "+getDeliveryDate(orderId));

		                            	//Invoice.generateInvoice(orderId,LoggedInCustomer.getUserName());
		                            	exitOrder = true;
		                                break;
		                            default:
		                                System.out.println("Invalid Choice");
		                                break;
		                        }
		                        break;
		                    } 
		                    else {
		                        System.out.println("Invalid Quantity or Out Of Stock. Please try again.");
		                        quantityAvailable = true;
		                    }
		                }
		            }
		        }

		        if (!productFound) {
		            System.out.println("Product ID not found. Please enter a valid ID.");
		            exitOrder = false;
		        }
		    }
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}

	}
	
	public static LocalDate getDeliveryDate(int orderID) throws SQLException {
	    LocalDate deliveryDate = LocalDate.of(2024, 2, 22); // Set a default date

	    String select = "SELECT DELIVERY_DATE FROM DATABASE.CUST_ORDER_TABLE WHERE ORDER_ID = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	    ps.setInt(1, orderID);
	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
	        deliveryDate = rs.getDate("DELIVERY_DATE").toLocalDate();
	    }

	    return deliveryDate;
	}

	//validates the customer password for payment process
	static boolean validateCusPassword(String userName, String password) {
		for (Customer ob : Customer.customerList) {
	        if (userName.equals(ob.getUserName()) && password.equals(ob.getPassword())) {
	            return true;
	        }
	    }
	    return false;
	}

	//get the order ID of admin from database for an order
	private static int getOrderIdFromDatabase() throws SQLException {
		 String select = "SELECT * FROM DATABASE.Cust_Order_Table WHERE ROWNUM = 1 ORDER BY ORDER_ID DESC";
		 int orderID = -1;
		 PreparedStatement ps = MainClass.con.prepareStatement(select);
		 ResultSet rs = ps.executeQuery();

             if (rs.next()) {
                 orderID = rs.getInt("ORDER_ID");
             }
		return orderID;
		 
		         
	}

	//it retrives the price of the product based on the product id
	private static double getProductPrice(int productID) throws SQLException, ClassNotFoundException {
		MainClass.getConnection();
	    String select = "SELECT PRODUCT_PRICE FROM DATABASE.Product WHERE PRODUCT_ID = :1";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	        ps.setInt(1, productID);

	        try (ResultSet resultSet = ps.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getDouble("PRODUCT_PRICE");
	            } 
	            else {
	                return -1;
	            }
	        }
	    }
	// get the order ID of customer from database for an order
	public static boolean findCusOrderID(int orderId) throws SQLException {
		String select = "SELECT COUNT(*) FROM DATABASE.CUST_ORDER_TABLE WHERE ORDER_ID = ?";

		PreparedStatement ps = MainClass.con.prepareStatement(select);
		ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
           int count = rs.getInt(1);
           return count > 0; // If count is greater than 0, order ID exists
        }
        return false;
     }
       
		
		
	//checks the payment status of the specific order
	public static boolean checkCusPaymentStatus(int orderId) throws SQLException {
			String select = "SELECT PAYMENT_STATUS FROM DATABASE.CUST_ORDER_TABLE WHERE ORDER_ID = ?";
		    PreparedStatement ps = MainClass.con.prepareStatement(select);
		    ps.setInt(1, orderId);
		    ResultSet rs = ps.executeQuery();
		    if (rs.next()) {
	            String paymentStatus = rs.getString("PAYMENT_STATUS");

	            if ("paid".equalsIgnoreCase(paymentStatus)) {
	                return true;
	            } 
	            else {
	                return false;
	            }
	        } 
		    else {
	            // If the order ID is not found, you might want to handle this case accordingly.
	            System.out.println("Order with ID not found.");
	            return false;
	        }
	}
	
	// updates the order to database if the customer places it
	public static void updateCusOrderToDB(int orderId) throws SQLException {
		String update = "Update DATABASE.CUST_ORDER_TABLE set ORDER_STATUS ='Cancelled' where ORDER_ID=?";
		PreparedStatement ps = MainClass.con.prepareStatement(update);
		ps.setInt(1, orderId);
		ps.execute();		
	}
	
	//cancels the order placed by the customer
	public static void cancelCusOrder() throws SQLException{
		Scanner sc = new Scanner(System.in);
	    System.out.println("Enter the Order ID to cancel:");
	    int orderId = sc.nextInt();

	    // Check if the order exists
	    boolean isAvailable = findCusOrderID(orderId);

	    if (isAvailable) {
	        // Check the payment status
	        boolean isPaid = checkCusPaymentStatus(orderId);
	        if (isPaid) {
	        	System.out.println("Payment is Done.");
	            System.out.println("Order cancelled and refunded initiated.");
	            updateCusOrderToDB(orderId);  // Update order status in the database
	        }
	        else {
	        	System.out.println("Payment is Not Done.");
	        	System.out.println("Order cancelled successfully.");
	        	updateCusOrderToDB(orderId);
	        }
	    } 
	    else {
	        System.out.println("Order with ID not found.");
	    }
	}
}
