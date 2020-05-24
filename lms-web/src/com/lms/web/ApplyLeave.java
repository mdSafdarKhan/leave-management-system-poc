package com.lms.web;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.service.leave.LeaveServiceRemote;

@WebServlet("/ApplyLeave")
public class ApplyLeave extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ApplyLeave() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser == null || loggedInUser.isEmpty()) {
			throw new ServletException("You are not authorized to apply leave");
		}
		
		String leaveDate = request.getParameter("leaveDate");
		if(leaveDate == null || leaveDate.isEmpty()) {
			throw new ServletException("Leave date must be provided");
		}
		
		Context ctx = Utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup("java:global/leave-management-system/LeaveService!com.demo.service.leave.LeaveServiceRemote");
				leaveService.applyLeave(loggedInUser, leaveDate);;
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

}
