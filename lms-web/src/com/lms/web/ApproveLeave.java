package com.lms.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.service.leave.LeaveServiceRemote;

@WebServlet("/ApproveLeave")
public class ApproveLeave extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ApproveLeave() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String leaveAppliedBy = request.getParameter("leaveAppliedBy");
		String leaveApprovedBy = request.getParameter("leaveApprovedBy");
		String leaveDate = request.getParameter("leaveDate");
		String jmsMessageId = request.getParameter("jmsMessageId");
		
		List<String> params = Arrays.asList(leaveAppliedBy, leaveApprovedBy, leaveDate, jmsMessageId);
		if(params.contains(null)) {
			throw new ServletException("Parameter should not be null!");
		}
		
		Context ctx = Utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup("java:global/leave-management-system/LeaveService!com.demo.service.leave.LeaveServiceRemote");
				leaveService.approveLeave(leaveAppliedBy, leaveApprovedBy, leaveDate, jmsMessageId);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

}
