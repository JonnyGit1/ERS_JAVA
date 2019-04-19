package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.dao.EmployeeDAO;
import com.revature.dao.EmployeeDAOImpl;

import com.revature.dao.ReimbursementDAO;

import com.revature.dao.ReimbursementDAOImpl;


//---------Similar to the connection factory class in example,
//----------this retrieves DAO implementations and manages data base connections

public class DAOUtilities {
	
//  DB info here!---------------------------->>>>>>>>>
	private static final String instance_un = "ersmaster";
	private static final String instance_pw = "ersmasterpw";
	private static final String instance_url = "jdbc:oracle:thin:@ersdbinstance.cclccpvm73o8.us-east-2.rds.amazonaws.com:1521:ORCL";
//-------------- AWS RDS instance endpoint	
	

	
	private static Connection connection;

	public static synchronized Connection getConnection() throws SQLException {
		
		if (connection == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Cant Drive. Driver Issue.");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(instance_url, instance_un, instance_pw);
		}

//----------------------If connection was closed then retrieve a new connection------------------------------//
		
		if (connection.isClosed()){
			System.out.println("Connection Open");
			connection = DriverManager.getConnection(instance_url, instance_un, instance_pw);
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

