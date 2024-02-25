/**
 * The Stock class represents the stock information of products, including details such as product ID.
 * The Products in the stock is available to the customers and they can purchase it.
 * It provides methods for fetching and displaying stock details from the database.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 20 Feb 2024
 */
package com.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Person.Customer;
import com.Person.MainClass;

public class Stock {
	private int stockID;
	private int productID;
	private String productCategory;
	private int orderID;
	private int stockQuantity;
	private int minQuantity;
	
	public Stock(int productID, int orderID, int stockQuantity, int minQuantity,String productCategory) {
		super();
		this.productID = productID;
		this.orderID = orderID;
		this.stockQuantity = stockQuantity;
		this.minQuantity = minQuantity;
		this.productCategory = productCategory;
	}
	//getters and setters to retrive the information
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public int getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}
	@Override
	public String toString() {
		return "Stock ID = "+stockID+"\nProduct ID = " + productID + "\nOrder ID = " + orderID + "\nStock Quantity = " + stockQuantity
				+ "\nMinimum Quantity = " + minQuantity+"\nProduct Category = "+productCategory;
	}
	public static List<Stock> stockList = new ArrayList<>();
	
	//Fetches stock details from the database and populates the stockList.
	public static void fetchStockDetails() throws SQLException {
		String dataFetch = "SELECT STOCK_ID,PRODUCT_ID,ORDER_ID,STOCK_QUANTITY,MIN_QUANTITY,PRODUCT_CATEGORY from DATABASE.Stocks";
		Statement state = MainClass.con.createStatement();
        ResultSet set = state.executeQuery(dataFetch);
        while (set.next()) {
            int DBstockID = set.getInt("STOCK_ID");
            int DBproductID = set.getInt("PRODUCT_ID");
            int DBorderID = set.getInt("ORDER_ID");
            int DBstockQuantity = set.getInt("STOCK_QUANTITY");
            int DBminQuantity = set.getInt("MIN_QUANTITY");
            String DBproductCategory = set.getString("PRODUCT_CATEGORY");
            
            Stock ob = new Stock(DBproductID,DBorderID,DBstockQuantity,DBminQuantity,DBproductCategory);
			stockList.add(ob);
		}
	}
	
	//Displays stock details including product ID, product category, stock quantity, and product price.
	public static void displayStock() throws SQLException{
		String display = "SELECT S.PRODUCT_ID, P.PRODUCT_CATEGORY, S.STOCK_QUANTITY, P.PRODUCT_PRICE FROM DATABASE.Stocks S JOIN DATABASE.Product P ON S.PRODUCT_ID = P.PRODUCT_ID";
		Statement state = MainClass.con.createStatement();
		ResultSet displaystock = state.executeQuery(display);
		System.out.println("Stock Details");
		System.out.println("PRODUCT_ID		PRODUCT_CATEGORY		STOCK_QUANTITY		PRODUCT_PRICE");
		while(displaystock.next()) {
			System.out.println(displaystock.getInt("PRODUCT_ID")+"		"+displaystock.getString("PRODUCT_CATEGORY")+"		"+displaystock.getInt("STOCK_QUANTITY")+"		"+displaystock.getDouble("PRODUCT_PRICE"));
		}
	}
}
