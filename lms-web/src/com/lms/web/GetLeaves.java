package com.lms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.model.leave.Leave;
import com.demo.service.leave.LeaveServiceRemote;

@WebServlet("/GetLeaves")
public class GetLeaves extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Context ctx = Utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup("java:global/leave-management-system/LeaveService!com.demo.service.leave.LeaveServiceRemote");
				ArrayList<Leave> leaves = leaveService.getLeaves();
				for(Leave leave : leaves) {
					out.write("<p>" + leave + "</p>");
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

}
