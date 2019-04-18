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

public class EmployeeDAOImpl implements EmployeeDAO {

	Connection connection = null;	// Our connection to the database
	Connection connectionPK = null;
	PreparedStatement stmt = null;	
   
	Statement stmtpk = null;

	@Override
	public List<Employee> getAllEmployees() {

		List<Employee> employees = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	
			String sql = "SELECT * FROM Employees";	      //    SQL query
			
			
			stmt = connection.prepareStatement(sql);	

			ResultSet rs = stmt.executeQuery();			  //      Query the database

			
			while (rs.next()) {
			
				Employee employee = new Employee();

			
				employee.setId(rs.getInt("empid"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setEmail(rs.getString("email"));
				employee.setManagerId(rs.getInt("cred"));

	
				employees.add(employee);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed,
			// or else we could wind up with a memory leak
			closeResources();
		}

		// return the list of Book objects populated by the DB.
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByFirstName(String firstName) {

			List<Employee> employees = new ArrayList<>();

			try {
				connection = DAOUtilities.getConnection();
				String sql = "SELECT * FROM Employees WHERE firstname LIKE ?";	
				stmt = connection.prepareStatement(sql);

		
				stmt.setString(1, "%" + firstName + "%");

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Employee employee = new Employee();

					employee.setId(rs.getInt("empid"));
					employee.setFirstName(rs.getString("firstname"));
					employee.setLastName(rs.getString("lastname"));
					employee.setEmail(rs.getString("email"));
					employee.setManagerId(rs.getInt("cred"));

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
			String sql = "SELECT * FROM Employees WHERE lastname LIKE ?";	
			stmt = connection.prepareStatement(sql);

		
			stmt.setString(1, "%" + lastName + "%");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();

				employee.setId(rs.getInt("empid"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setEmail(rs.getString("email"));
				employee.setManagerId(rs.getInt("cred"));

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

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				employee.setId(rs.getInt("empid"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setEmail(rs.getString("email"));
				employee.setManagerId(rs.getInt("cred"));
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
					+ "VALUES (?, ?, ?, ?, ?)";                   // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);

			// But that's okay, we can set them all before we execute
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
			String sql = "UPDATE Employees SET firstname=?, lastname=?, cred=?, email=? WHERE empid=?";
			stmt = connection.prepareStatement(sql);                   ///should i swap these?

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
			String sql = "DELETE Employees WHERE empid=?";
			stmt = connection.prepareStatement(sql);

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
		ResultSet rs=null;
		connectionPK = DAOUtilities.getConnection();
		String sql = "SELECT max(empid) as max FROM Employees";
		int new_pk = 0;
		try {
			stmtpk = connectionPK.createStatement();
			rs=stmtpk.executeQuery(sql);
			while(rs.next()){
				new_pk = (rs.getInt("max"))+1;
			}
		}
		catch (SQLException e) {e.printStackTrace();}
//		finally{
//			if (stmtpk != null) {stmtpk.close();}
//			if (connectionPK != null) {connectionPK.close();}
//		}
		return new_pk;

	}

 //   // Close all resources to prevent memory leaks...       				//  //
//   // Ideally, close them in the reverse-order you open them...        //  //
		private void closeResources() {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.out.println("Could not close statement!");
				e.printStackTrace();
			}

			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.out.println("Could not close connection!");
				e.printStackTrace();
			}
		}

}
