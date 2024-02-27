/**
 * The Product class represents a product in the inventory which is available in admin ,it contains details such as product ID, name,
 * category, price, quantity, and supplier ID. 
 * It provides methods for managing product details,and it is updated in the database.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 18 Feb 2024
 */
package com.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Person.*;
import com.Person.MainClass;

public class Product {
	private int productID;
	private String name;
	private String category;
	private double price;
	private int quantity;
	private int supplierID;
	public Product(int productID, String name, String category, double price, int quantity, int supplierID) {
		super();
		this.productID = productID;
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.supplierID = supplierID;
	}
	//getters and setters to retrive the information
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getcategory() {
		return category;
	}
	public void setcategory(String category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
	@Override
	public String toString() {
		return "ProductID = " + productID + "\nName = " + name + "\nCategory = " + category + "\nPrice=  " + price
				+ "\nQuantity = " + quantity + "\nSupplierID = " + supplierID ;
	}
	
	public static List<Product> productList = new ArrayList<>();
	
	// Fetches product details from the database and populates the productList.
	public static void fetchProductdetails() {
		String dataFetch = "SELECT PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_PRICE,PRODUCT_QUANTITY,SUPPLIER_ID FROM DATABASE.Product";
	    try {
	        Statement state = MainClass.con.createStatement();
	        ResultSet set = state.executeQuery(dataFetch);
	        while (set.next()) {
	            int DBproductID = set.getInt("PRODUCT_ID");
	            String DBproductName = set.getString("PRODUCT_NAME");
	            String DBcategory = set.getString("PRODUCT_CATEGORY");
	            int DBprice = set.getInt("PRODUCT_PRICE");
	            int DBquantity = set.getInt("PRODUCT_QUANTITY");
	            int DBsupplierID = set.getInt("SUPPLIER_ID");
	            
				Product ob = new Product(DBproductID,DBproductName,DBcategory,DBprice,DBquantity,DBsupplierID);
				productList.add(ob);
				
			}
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}
	// Adds a new product to the database and the productList.
	public static void productAddToDatabase(Product product) {
		String insert = "Insert into DATABASE.Product values(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = MainClass.con.prepareStatement(insert);
			ps.setInt(1,product.getProductID());
			ps.setString(2,product.getName());
			ps.setString(3, product.getcategory());
			ps.setDouble(4,product.getPrice());
			ps.setInt(5,product.getQuantity());
			ps.setInt(6,product.getSupplierID());
			ps.executeUpdate();
			System.out.println("Product added into the Database");
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	//Adds a new product to the database and the productList, taking input from the user.
	public static void addProduct() throws SQLException,SQLIntegrityConstraintViolationException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Details");
		System.out.println("Enter the Product ID");
		int productID = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the Product Name");
		String productName = sc.nextLine();
		System.out.println("Enter the Product Category");
		String category = sc.nextLine();
		System.out.println("Enter the Product Price");
		double price = sc.nextDouble();
		System.out.println("Enter the Product Quantity");
		int quantity = sc.nextInt();
		System.out.println("Enter the Supplier ID");
		int supplierID = sc.nextInt();
		
		Product ob = new Product(productID,productName,category,price,quantity,supplierID);
		productList.add(ob);
		productAddToDatabase(ob);
	}
	//finds if the product is available in the list
	public static boolean findProduct(int productID) {
		for(Product ob: productList) {
			if(productID == ob.getProductID()) {
				return true;
			}
		}
		return false;
	}
	
	// Updates the product details in the database based on the provided product object.
	public static void updateToDatabase( Product updateproduct) throws SQLException {
		String update = "UPDATE DATABASE.Product set PRODUCT_NAME=?, PRODUCT_CATEGORY=?, PRODUCT_PRICE=?, PRODUCT_QUANTITY=?, SUPPLIER_ID=? WHERE PRODUCT_ID=?";
		PreparedStatement ps = MainClass.con.prepareStatement(update);
		ps.setString(1, updateproduct.getName());
		ps.setString(2, updateproduct.getcategory());
		ps.setDouble(3,updateproduct.getPrice());
		ps.setInt(4,updateproduct.getQuantity());
		ps.setInt(5,updateproduct.getSupplierID());
		ps.setInt(6,updateproduct.getProductID());
		
		int isUpdated = ps.executeUpdate();
		if(isUpdated > 0) {
			System.out.println("Updated Successfully");
		}
		else {
			System.out.println("Not Updated");
		}
	}
	
	//Updates the details of an existing product, taking input from the user.
	public static void updateProduct() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Details to Update");
		System.out.println("Enter the Product ID");
		int productID = sc.nextInt();
		sc.nextLine();
		boolean isAvailable = findProduct(productID);
		if(isAvailable) {
			System.out.println("Enter the Product Name");
			String productName = sc.nextLine();
			System.out.println("Enter the Product Category");
			String category = sc.nextLine();
			System.out.println("Enter the Product Price");
			double price = sc.nextDouble();
			System.out.println("Enter the Product Quantity");
			int quantity = sc.nextInt();
			System.out.println("Enter the Supplier ID");
			int supplierID = sc.nextInt();
		
			Product updateproduct = new Product(productID,productName,category,price,quantity,supplierID);
			updateToDatabase(updateproduct);
		}
		else {
			System.out.println("Product Not Found");
		}
	}
	//Displays the details of all products in a formatted manner.
	public static void displayProduct() throws SQLException{
		String display = "SELECT * from DATABASE.PRODUCT ORDER BY PRODUCT_ID ASC";
		Statement state = MainClass.con.createStatement();
		ResultSet displayproduct = state.executeQuery(display);
		System.out.println("Product Details");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-15s %-20s %-25s %-15s %-15s %-10s\n",
		        "PRODUCT_ID", "PRODUCT_NAME", "PRODUCT_CATEGORY", "PRODUCT_PRICE", "PRODUCT_QUANTITY", "SUPPLIER_ID");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

		while (displayproduct.next()) {
		    System.out.printf("%-15d %-20s %-25s %-15d %-15d %-15d\n",
		            displayproduct.getInt(1),
		            displayproduct.getString(2),
		            displayproduct.getString(3),
		            displayproduct.getInt(4),
		            displayproduct.getInt(5),
		            displayproduct.getInt(6));
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	//Deletes the details of a product from the database based on the provided product ID.
	public static void deleteProduct() throws SQLException, ClassNotFoundException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Product Details to Delete");
		System.out.println("Enter the Product ID");
		int productID = sc.nextInt();
		
		boolean isAvailable = findProduct(productID);
		if(isAvailable) {
			MainClass.getConnection();
			String delete = "DELETE from DATABASE.Product where PRODUCT_ID=?";
			PreparedStatement ps = MainClass.con.prepareStatement(delete);
			ps.setInt(1, productID);
			int isDeleted = ps.executeUpdate();
			if(isDeleted > 0) {
				System.out.println("Product Details Deleted Successfully");
			}
			else {
				System.out.println("Product Details Not Deleted");
			}
		}
		else {
			System.out.println("Product Details Not Found");
		}
	}
}
