package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Login;
import com.revature.utilities.DAOUtilities;

public class LoginDAOImpl implements LoginDAO {
	
	public Login getLoginByUser(String user) {
		
		Login log = new Login();
		String sq = "SELECT * FROM Employees WHERE email = ?";
		ResultSet rset = null;
		
		try (Connection conect = DAOUtilities.getConnection();
			PreparedStatement pre_s = conect.prepareStatement(sq)){
			
			pre_s.setString(1, user);
			
			rset = pre_s.executeQuery();
			
		while(rset.next()) {
			int empId = rset.getInt("EMPID");
			log.setUsername(user);
			
			String username = rset.getString("USERNAME");
			log.setUsername(username);
			
			String password = rset.getString("PASSWORD");
			log.setPassword(password);
			
		}
			
}catch (SQLException e) {
	e.printStackTrace();
	
}finally {
	if(rset != null) {
		try {
			rset.close();
		}	catch(SQLException e) {
			e.printStackTrace();
			
		}
	
	}
}
return log;
	}
	
@Override
public List<Login> getLogins() {
	List<Login> loginList = new ArrayList<Login>();
	
	String sq = "SELECT * FROM Employee";
	
	try (Connection conect = DAOUtilities.getConnection();
			Statement stm = conect.createStatement();
			ResultSet rset = stm.executeQuery(sq)){
		while(rset.next()) {
			
			Login log = new Login();
			int empId = rset.getInt("EMPID");
			log.setId(empId);
			
			String username = rset.getString("USERNAME");
			log.setUsername(username);
			
			String pw = rset.getString("PASSWAORD");
			log.setPassword(pw);
			
			loginList.add(log);
		}
	}catch (SQLException e) {
		e.printStackTrace();	
			
		}
	return loginList;
	
	}
}
		



