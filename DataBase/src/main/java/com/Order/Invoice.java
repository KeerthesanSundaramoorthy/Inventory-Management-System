/**
 * The Invoice class represents an invoice containing details such as invoice number, date.
 * It is used when the customer orders a product and the invoice is generated.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 23 Feb 2024
 */
package com.Order;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import com.Person.MainClass;

public class Invoice {
	private int invoiceNo;
    private Date date;
    private int productID;
    private String productName;
    private double unitPrice;
    private double totalPrice;
    private int quantity;
    private int orderID;
    private String customerName;
    
    //getters and setters to retrive the information
	public String getcustomerName() {
		return customerName;
	}
	public void setcustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public Invoice(int invoiceNo, Date date, int productId, String productName, double unitPrice, double totalPrice,
			int quantity, int orderID) {
		super();
		this.invoiceNo = invoiceNo;
		this.date = Date.valueOf(LocalDate.now());
		this.productID = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.quantity = quantity;
		this.orderID = orderID;
	
	}
	//method to generate an invoice for the specified order ID and customer.
	public static void generateInvoice(int orderID, String userName) throws SQLException {
	    String select = "SELECT P.PRODUCT_ID, P.PRODUCT_NAME, P.PRODUCT_PRICE, O.QUANTITY, O.PRICE FROM DATABASE.Product P JOIN DATABASE.cust_order_table O ON P.PRODUCT_ID = O.PRODUCT_ID WHERE O.ORDER_ID = ?";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	    ps.setInt(1, orderID);
	    String username = userName;
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        int productId = rs.getInt("PRODUCT_ID");
	        String productName = rs.getString("PRODUCT_NAME");
	        double unitPrice = rs.getDouble("PRODUCT_PRICE");
	        int quantity = rs.getInt("QUANTITY");
	        double totalPrice = rs.getDouble("PRICE");
	        System.out.println("Invoice Generated Successfully.");
	        updateToDB(productId, productName, unitPrice, totalPrice, quantity, username, orderID);
	    }
	}
	//method to Update the invoice details in the database after generating the invoice.
	public static void updateToDB(int productId, String productName, double unitPrice, double totalPrice, int quantity, String username, int orderID) throws SQLException {
		String insert = "INSERT INTO DATABASE.INVOICE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement ps = MainClass.con.prepareStatement(insert)) {
	        ps.setInt(1, 100);
	        ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
	        ps.setInt(3, productId);
	        ps.setString(4, productName);
	        ps.setDouble(5, unitPrice);
	        ps.setDouble(6, totalPrice);
	        ps.setInt(7, quantity);
	        ps.setInt(8, orderID);
	        ps.setString(9, username);

	        int success = ps.executeUpdate();
	        if (success > 0) {
	        	int invoiceid = getInvoiceIdFromDatabase(orderID);
	        		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
	        		System.out.printf("%-12s %-15s %-12s %-12s %-15s %-15s %-10s %-12s %-20s\n",
	        	        "Invoice ID", "Invoice Date", "Order ID", "Product ID", "Product Name", "Unit Price", "Quantity", "Total Price", "Customer User Name");
	        		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

	        		System.out.printf("%-12s %-15s %-12s %-12s %-15s %-15.2f %-10d %-12.2f %-20s\n",
	        		        invoiceid, LocalDate.now(), orderID, productId, productName, unitPrice, quantity, totalPrice, username);

	        		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
	            }
	        
	        	else {
	                System.out.println("Invoice ID not retrieved.");
	            }
	        }
	        
	    }
	//method to retrieve the invoice ID from the database based on the order ID.
	private static int getInvoiceIdFromDatabase(int orderID) throws SQLException {
		 String select = "SELECT INVOICE_ID FROM DATABASE.Invoice WHERE ORDER_ID = ?";
		 PreparedStatement ps = MainClass.con.prepareStatement(select);
		 ps.setInt(1, orderID);
		 ResultSet rs = ps.executeQuery();
	     if (rs.next()) {
	        int invoiceID = rs.getInt(1);
	        return invoiceID ; // If count is greater than 0, order ID exists
	     }
	     return -1;     
	}
}
	


