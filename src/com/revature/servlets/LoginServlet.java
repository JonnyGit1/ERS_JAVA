package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.LoginDAO;
import com.revature.dao.LoginDAOImpl;
import com.revature.model.Login;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getRequestDispatcher("/Login.html").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String pw = request.getParameter("password");
		LoginDAO log_dao_i = new LoginDAOImpl();
		Login log = log_dao_i.getLoginByUser(username);
		
		PrintWriter pwrite = response.getWriter();
		response.setContentType("text/html");
		
//------------------ New Session Object---------------------------//
		HttpSession session = request.getSession();
		
		if (username.equals(log.getUsername()) && pw.equals(log.getPassword())) {
			session.setAttribute("username", username);
			session.setAttribute("id", log.getId());
			response.sendRedirect("choose?id=" + log.getId());
		} else {
			response.sendRedirect("loginfail");
		}
	}

	}


