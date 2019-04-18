package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeDAOImpl;

import com.revature.dao.ReimbursementDAO;

import com.revature.dao.ReimbursementDAOImpl;

/**
* Class used to retrieve DAO Implementations. Serves as a factory. Also
manages a single instance of the database connection.
*/
public class DAOUtilities {
	
// need my info here!---------------------------->>>>>>>>>
	private static final String CONNECTION_USERNAME = "ersmaster";
	private static final String CONNECTION_PASSWORD = "ersmasterpw";
	private static final String URL = "jdbc:oracle:thin:@ersdbinstance.cclccpvm73o8.us-east-2.rds.amazonaws.com:1521:ORCL";
	
	

	
	private static Connection connection;
//and here---------------------------------------->>>
	
	public static synchronized Connection getConnection() throws SQLException {
		
		if (connection == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME,CONNECTION_PASSWORD);
		}

//----------------------If connection was closed then retrieve a new connection------------------------------//
		
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME,CONNECTION_PASSWORD);
		}
		return connection;
	}

	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAOImpl();
	}

	
	public static ReimbursementDAO getReimbursementRequestDAO() {
		return new ReimbursementDAOImpl();
	}

}

