package com.revature.model;

import java.util.Date;

public class ReimbursementRequest {
	
	private int id;
	private String name;
	private String description;
	private double amount;
	private Date date;
	private boolean approved;
	private int empId;
	
	
	
	public ReimbursementRequest() {
		super();
		
	}
	
	public ReimbursementRequest(int id, String name, String description, double amount, Date date, boolean approved, int empId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.amount = amount;
		this.date = date;
		this.approved = approved;
		this.empId = empId;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public int getEmpId() {
		return empId;
	}

	
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
	@Override
	public String toString() {
		return "ReimbursementRequest [id=" + id + ", name=" + name + ", description=" + description + ", amount="
				+ amount + ", approved=" + approved + ", empId=" + empId + "]";
	}
	
	
	
	

}
