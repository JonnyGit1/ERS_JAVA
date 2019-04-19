package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.model.ReimbursementRequest;
import com.revature.utilities.DAOUtilities;

public class ReimbursementDAOImpl implements ReimbursementDAO {

	Connection connection = null;
	Connection connectionPK = null;
	PreparedStatement stmt = null;
								
	Statement stmtpk = null;

	@Override
	public List<ReimbursementRequest> findAllReimbursementRequest() {
		   List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	                    // Get our database
                          
			String sql = "SELECT * FROM ReimbursementRequest";			    // Our SQL query
			stmt = connection.prepareStatement(sql);	                    // Creates the prepared

			ResultSet rs = stmt.executeQuery();			                    // Queries the database

			
			while (rs.next()) {

				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

				rreq.setId(rs.getInt("rid"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("submit_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}
				
					rreq.setEmpId(rs.getInt("empid"));
		            rreq_list.add(rreq);
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
		return rreq_list;
	}

	@Override
	public boolean addReimbursementRequest(ReimbursementRequest reimbursementRequest) {
		try {
			connection = DAOUtilities.getConnection();

			String sql = "INSERT INTO ReimbursementRequest (rid, name, description, amount, submit_date, approved, empid) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)"; 

			stmt = connection.prepareStatement(sql);
			// But that's okay, we can set them all before we execute

			stmt.setInt(1, getNewPK());
			stmt.setString(2, reimbursementRequest.getName());
			stmt.setString(3, reimbursementRequest.getDescription());
			stmt.setDouble(4, reimbursementRequest.getAmount());
			java.sql.Date sql_StartDate = new java.sql.Date(
            reimbursementRequest.getDate().getTime());
			stmt.setDate(5, sql_StartDate);
			if(reimbursementRequest.isApproved()==false) {
				stmt.setInt(6, 0);
			} else {
				stmt.setInt(6, 1);
			}
			stmt.setInt(7, reimbursementRequest.getEmpId());


//-----------------	If we were able to add our employee to the DB, we want to return true.
//-----------------This if statement both executes our query, and looks at the return
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
	public boolean updateReimbursementRequest(ReimbursementRequest reimbursementRequest, int i) {

		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE ReimbursementRequest SET name=?, description=?, amount=?, submit_date=?, approved=?, empid=? WHERE rid=?";
			stmt = connection.prepareStatement(sql); 

			stmt.setString(1, reimbursementRequest.getName());
			stmt.setString(2, reimbursementRequest.getDescription());
			stmt.setDouble(3, reimbursementRequest.getAmount());
			stmt.setDate(4, (Date) reimbursementRequest.getDate());
			stmt.setInt(5,  i);
			stmt.setInt(6, reimbursementRequest.getEmpId());
			stmt.setInt(7, reimbursementRequest.getId());

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
	public boolean deleteReimbursementRequestId(int id) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE ReimbursementRequest WHERE rid=?";
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

	@Override
	public List<ReimbursementRequest>findReimbursementRequestByEmployeeId(int empId) {

		List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM ReimbursementRequest WHERE empid LIKE ?";	
			stmt = connection.prepareStatement(sql);

	
			stmt.setString(1, Integer.toString(empId));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

	
				rreq.setId(rs.getInt("rid"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("submit_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}
				rreq.setEmpId(rs.getInt("empid"));
	
				rreq_list.add(rreq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeResources();
		}
		return rreq_list;
	}

	@Override
	public ReimbursementRequest findReimbursementRequestByReimbursementId(int id) {

		ReimbursementRequest rreq = null;

			try {
				connection = DAOUtilities.getConnection();
				String sql = "SELECT * FROM ReimbursementRequest WHERE rid = ?";
				stmt = connection.prepareStatement(sql);

				stmt.setString(1, Integer.toString(id));

				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					int i = rs.getInt("approved");
					rreq = new ReimbursementRequest();
					rreq.setId(rs.getInt("rid"));
					rreq.setName(rs.getString("name"));
					rreq.setDescription(rs.getString("description"));
					rreq.setAmount(rs.getDouble("amount"));
					rreq.setDate(rs.getDate("submit_date"));
					rreq.setEmpId(rs.getInt("empid"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources();
			}

			return rreq;
		}



	@Override
	public List<ReimbursementRequest> findPendingReimbursementRequest() {

		List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM ReimbursementRequest WHERE approved LIKE ?";
			
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, Integer.toString(0));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

				rreq.setId(rs.getInt("rid"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("submit_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}
				rreq.setEmpId(rs.getInt("empid"));
		
				rreq_list.add(rreq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeResources();
		}
		return rreq_list;
	}

	@Override
	public List<ReimbursementRequest> findResolvedReimbursementRequest() {
		   List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM ReimbursementRequest WHERE approved LIKE ?";
			
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, Integer.toString(1));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");


				rreq.setId(rs.getInt("rid"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("submit_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}
				rreq.setEmpId(rs.getInt("empid"));

				rreq_list.add(rreq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeResources();
		}
		return rreq_list;
	}

	private int getNewPK() throws SQLException {
		ResultSet rs=null;
		connectionPK = DAOUtilities.getConnection();
		String sql = "SELECT max(rid) as max FROM ReimbursementRequest";
		int new_pk = 0;
		try {
			stmtpk = connectionPK.createStatement();
			rs=stmtpk.executeQuery(sql);
			while(rs.next()){
				new_pk = (rs.getInt("max"))+1;
			}
		}
		catch (SQLException e) {e.printStackTrace();}

		return new_pk;

	}

//	//----Important---prevent memory leaks by Closing all resources...
//	// Ideally, close in reverse-order that opened
	
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
			
		} catch (SQLException e) {
			System.out.println("Statement not Closed.");
			e.printStackTrace();
		}

		try {
			if (connectionPK != null) {connectionPK.close();}
			if (connection != null)
				connection.close();
			
		} catch (SQLException e) {
			System.out.println("Connection not Closed.");
			e.printStackTrace();
		}
	}

}