package com.revature.dao;

import java.util.List;

import com.revature.model.Employee;

public interface EmployeeDAO {

	public List<Employee> getAllEmployees();
    public List<Employee> getEmployeesByFirstName(String firstName);
    public List<Employee> getEmployeesByLastName(String lastName);
    public Employee getEmployeeById(int id);
    
    public boolean addEmployee(Employee employee);
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployeeById(int id);
}
