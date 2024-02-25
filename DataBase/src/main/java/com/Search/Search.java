/**
 * The Search is an interface that defines methods for searching products by category and price by the customer.
 * @author Keerthesan Sundaramoorthy(expleo)
 * @since 22 Feb 2024
 */
package com.Search;

import java.sql.SQLException;

public interface Search {
	// it performs a search for products based on category.
	public void searchByCategory() throws SQLException;
	
	//it performs a search for products based on price.
	public void searchByPrice() throws SQLException;
}

