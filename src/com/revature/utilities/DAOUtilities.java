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

//---------Similar to the connection factory class in example,
//----------this retrieves DAO implementations and manages data base connections

public class DAOUtilities {
	
// need my info here!---------------------------->>>>>>>>>
	private static final String instance_un = "ersmaster";
	private static final String instance_pw = "ersmasterpw";
	private static final String instance_url = "jdbc:oracle:thin:@ersdbinstance.cclccpvm73o8.us-east-2.rds.amazonaws.com:1521:ORCL";
//------------------why was this so trickey? just needed to replace local host with AWS RDS instance endpoint	
	

	
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
			connection = DriverManager.getConnection(instance_url, instance_un,instance_pw);
		}

//----------------------If connection was closed then retrieve a new connection------------------------------//
		
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(instance_url, instance_un,instance_pw);
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

