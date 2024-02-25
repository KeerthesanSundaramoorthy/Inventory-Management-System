package com.Inventory.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	public static void main(String []args) {
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "system";
		String password = "keerthisan";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(URL,userName,password);
			if (con == null) {
				System.out.println("Connection Not Established");
			}
			else {
				System.out.println("Connection Established");
			}
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
