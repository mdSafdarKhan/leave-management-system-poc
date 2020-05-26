package com.lms.client.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lms.client.service.UserService;

@Controller
public class HomeController {

	@Autowired 
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@GetMapping({"/" , "/home"})
	public String home() {
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/validateLogin")
	public void validateLogin() {
		HashMap<String, String> userDetails = userService.validateLogin(request.getParameter("username"), request.getParameter("password"));

		HttpSession session = request.getSession();
		session.setAttribute("username", userDetails.get("username"));
		session.setAttribute("role", userDetails.get("role"));

		if(userDetails.get("role").equals("PM")) {
			request.setAttribute("leaves", userService.getLeaves());
		}
		
		System.out.println(".controller validateLogin " + userDetails);
		
		try {
			response.sendRedirect(request.getContextPath() + "/home");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/applyLeave")
	public void applyLeave() {
		userService.applyLeave((String)request.getSession().getAttribute("username"), request.getParameter("leaveDate"));
	
		try {
			response.sendRedirect(request.getContextPath() + "/home");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	@PostMapping("/approveLeave")
	public void approveLeave() {
		
		String leaveId = request.getParameter("leaveId");
		String jmsMessageId = request.getParameter("jmsMessageId");
		String leaveApprovedBy = request.getParameter("leaveApprovedBy");
		
		System.out.println(".approveLeave " + leaveId);
		System.out.println(".approveLeave " + jmsMessageId);
		System.out.println(".approveLeave " + leaveApprovedBy);
		
		
		userService.approveLeave(leaveId, jmsMessageId, leaveApprovedBy);
		
		System.out.println(".approveLeave done");
		
		try {
			
			response.sendRedirect(request.getContextPath() + "/success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/success")
	public String success() {
		return "success";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
}
