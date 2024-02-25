/**
 * The SearchClass class implements the Search interface.
 * It provides the methods for searching products by category and by price range for the customers.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 23 Feb 2024
 */
package com.Search;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Scanner;

import com.Person.MainClass;
//
public class SearchClass implements Search{
	
	//Method implementation
	//Searches for products by a specified category.
	public void searchByCategory() throws SQLException, SQLSyntaxErrorException {
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Enter the product category");
	    String category = scanner.nextLine();
	    String select = "SELECT S.PRODUCT_ID, P.PRODUCT_NAME,P.PRODUCT_CATEGORY, S.STOCK_QUANTITY, P.PRODUCT_PRICE FROM DATABASE.Stocks S JOIN DATABASE.Product P ON S.PRODUCT_ID = P.PRODUCT_ID WHERE P.PRODUCT_CATEGORY = ? ORDER BY P.PRODUCT_CATEGORY";
	    PreparedStatement ps = MainClass.con.prepareStatement(select);
	        ps.setString(1, category);
	        ResultSet rs = ps.executeQuery();
	        System.out.println("The Results are ");
	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
	        System.out.printf("%-12s %-20s %-20s %-15s %-20s\n",
	                "PRODUCT_ID", "PRODUCT_NAME", "PRODUCT_CATEGORY", "STOCK_QUANTITY", "PRODUCT_PRICE");
	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
	        while (rs.next()) {
	            System.out.printf("%-12s %-20s %-20s %-15s %-20s\n",
	                    rs.getInt("PRODUCT_ID"), rs.getString("PRODUCT_NAME"),
	                    rs.getString("PRODUCT_CATEGORY"), rs.getInt("STOCK_QUANTITY"),
	                    rs.getDouble("PRODUCT_PRICE"));
	        }
	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
	    } 
	//Method implementation
	//Searches for products within a specified price range.
	public void searchByPrice()throws SQLSyntaxErrorException {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the minimum price: ");
            double minPrice = sc.nextDouble();
            System.out.println("Enter the maximum price: ");
            double maxPrice = sc.nextDouble();
            String select = "SELECT P.PRODUCT_ID, P.PRODUCT_NAME, P.PRODUCT_CATEGORY, S.STOCK_QUANTITY, P.PRODUCT_PRICE FROM DATABASE.Product P LEFT JOIN DATABASE.Stocks S ON P.PRODUCT_ID = S.PRODUCT_ID WHERE P.PRODUCT_PRICE BETWEEN ? AND ?";
            PreparedStatement ps = MainClass.con.prepareStatement(select);
                ps.setDouble(1, minPrice);
                ps.setDouble(2, maxPrice);
                ResultSet rs = ps.executeQuery();
                System.out.println("The Results are ");
    	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
    	        System.out.printf("%-12s %-20s %-20s %-15s %-20s\n",
    	                "PRODUCT_ID", "PRODUCT_NAME", "PRODUCT_CATEGORY", "STOCK_QUANTITY", "PRODUCT_PRICE");
    	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
    	        while (rs.next()) {
    	            System.out.printf("%-12s %-20s %-20s %-15s %-20s\n",
    	                    rs.getInt("PRODUCT_ID"), rs.getString("PRODUCT_NAME"),
    	                    rs.getString("PRODUCT_CATEGORY"), rs.getInt("STOCK_QUANTITY"),
    	                    rs.getDouble("PRODUCT_PRICE"));
    	        }
    	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
