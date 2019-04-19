package com.revature.dao;

import java.util.List;

import com.revature.model.Login;

public interface LoginDAO {

	public Login getLoginByUser(String user);
	public List<Login> getLogins();
	
}
