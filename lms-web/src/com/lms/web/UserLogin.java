package com.lms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.service.login.LoginRemote;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public UserLogin() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			Context ctx = Utility.getInitialContext();
			LoginRemote loginService = (LoginRemote) ctx.lookup("java:global/leave-management-system/Login!com.demo.service.login.LoginRemote");
			HashMap<String, String> user = loginService.validateLogin(request.getParameter("username"), request.getParameter("password"));
			
			if (null != user) {
				String username = user.get("username");
				String role = user.get("role");
				out.write("<p>Hello " + username + " (" + role + ")</p>");
				
				HttpSession session = request.getSession();
				session.setAttribute("loggedInUser", username);
				
			} else {
				out.write("<p>Wrong username or password!</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
