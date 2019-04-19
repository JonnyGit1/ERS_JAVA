package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Employee;
import com.revature.utilities.DAOUtilities;

//--------------------------Implementation of The Employee DAO----------------------------------------------//

public class EmployeeDAOImpl implements EmployeeDAO {

//---------------------Set up connection to DB--------------------------------------------------------------//
	
	Connection connection = null;	
	Connection connectionPK = null;
	PreparedStatement stmt = null;	
	Statement stmtpk = null;

	@Override
	public List<Employee> getAllEmployees() {
//------------create an array list and fill it with all of the employees of JonnyKool LLC------------------------//
		
		List<Employee> employees = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	
			
			stmt = connection.prepareStatement("SELECT * FROM Employees");	

			ResultSet rset = stmt.executeQuery();			  // execute Query , obtain result set

// while there are employees to grab, get them.	
			while (rset.next()) {
			
				Employee employee = new Employee();

			
				employee.setId(rset.getInt("empid"));
				employee.setFirstName(rset.getString("firstname"));
				employee.setLastName(rset.getString("lastname"));
				employee.setEmail(rset.getString("email"));
				employee.setManagerId(rset.getInt("cred"));

	
				employees.add(employee);
			}
			rset.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
			closeResources();     //------------- don't forget to close resources...
		}

//--------------this will return the list of employees grabbed from the data base------------------------//
		return employees;
	}
//----------------------------------------
	@Override
	public List<Employee> getEmployeesByFirstName(String firstName) {

			List<Employee> employees = new ArrayList<>();

			try {
				connection = DAOUtilities.getConnection();
				String sq = "SELECT * FROM Employees WHERE firstname LIKE ?";	
				stmt = connection.prepareStatement(sq);

		
				stmt.setString(1, "%" + firstName + "%");

				ResultSet rset = stmt.executeQuery();

				while (rset.next()) {
					Employee employee = new Employee();

					employee.setId(rset.getInt("empid"));
					employee.setFirstName(rset.getString("firstname"));
					employee.setLastName(rset.getString("lastname"));
					employee.setEmail(rset.getString("email"));
					employee.setManagerId(rset.getInt("cred"));

					employees.add(employee);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				closeResources();
			}

		return employees;
	}

	@Override
	public List<Employee> getEmployeesByLastName(String lastName) {
		   List<Employee> employees = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sq = "SELECT * FROM Employees WHERE lastname LIKE ?";	
			stmt = connection.prepareStatement(sq);

		
			stmt.setString(1, "%" + lastName + "%");

			ResultSet rset = stmt.executeQuery();

			while (rset.next()) {
				Employee employee = new Employee();

				employee.setId(rset.getInt("empid"));
				employee.setFirstName(rset.getString("firstname"));
				employee.setLastName(rset.getString("lastname"));
				employee.setEmail(rset.getString("email"));
				employee.setManagerId(rset.getInt("cred"));

				employees.add(employee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeResources();
		}

		return employees;

	}

	@Override
	public Employee getEmployeeById(int id) {
		Employee employee = null;

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Employees WHERE empid = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setInt(1, id);

			ResultSet rset = stmt.executeQuery();

			if (rset.next()) {
				employee = new Employee();
				employee.setId(rset.getInt("empid"));
				employee.setFirstName(rset.getString("firstname"));
				employee.setLastName(rset.getString("lastname"));
				employee.setEmail(rset.getString("email"));
				employee.setManagerId(rset.getInt("cred"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return employee;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO Employees (empid, firstname,lastname, email, cred) "
					+ "VALUES (?, ?, ?, ?, ?)";                   
			
			stmt = connection.prepareStatement(sql);


			stmt.setInt(1, getNewPK());
			stmt.setString(2, employee.getFirstName());
			stmt.setString(3, employee.getLastName());
			stmt.setString(4, employee.getEmail());
			stmt.setInt(5, employee.getManagerId());

			
			
			// If we were able to add our employee to the DB, we want to return true.
			// This if statement both executes our query, and looks at the return
			// value to determine how many rows were changed
			
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		try {
			connection = DAOUtilities.getConnection();
			String sq = "UPDATE Employees SET firstname=?, lastname=?, cred=?, email=? WHERE empid=?";
			stmt = connection.prepareStatement(sq);                  

			stmt.setString(1, employee.getFirstName());
			stmt.setString(2, employee.getLastName());
			stmt.setInt(3, employee.getManagerId());
			stmt.setString(4, employee.getEmail());
			stmt.setInt(5, employee.getId());

			System.out.println(stmt);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean deleteEmployeeById(int id) {
		try {
			connection = DAOUtilities.getConnection();
			String sq = "DELETE Employees WHERE empid=?";
			stmt = connection.prepareStatement(sq);

			stmt.setInt(1, id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	private int getNewPK() throws SQLException {
		ResultSet rset=null;
		connectionPK = DAOUtilities.getConnection();
		String sq = "SELECT max(empid) as max FROM Employees";
		int new_prime_key = 0;
		try {
			stmtpk = connectionPK.createStatement();
			rset=stmtpk.executeQuery(sq);
			while(rset.next()){
				new_prime_key = (rset.getInt("max")) + 1;
			}
		}
		catch (SQLException e) {e.printStackTrace();}

		return new_prime_key;

	}

 //--------------------   // Close all resources to prevent memory leaks...       				//  //
//--------------------   // Ideally, close them in the reverse-order you open them...        //  //
	
		private void closeResources() {
			try {
				if (stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				
				System.out.println("This statement could not be closed..");
				e.printStackTrace();
			}

			try {
				if (connection != null)
					connection.close();
				
			} catch (SQLException e) {
				
				System.out.println("Connection was not closed..");
				e.printStackTrace();
			}
		}

}
