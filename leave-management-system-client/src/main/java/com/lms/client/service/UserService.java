package com.lms.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.leave.Leave;
import com.demo.service.leave.LeaveServiceRemote;
import com.demo.service.login.LoginRemote;
import com.lms.client.config.AppConfig;
import com.lms.client.util.Utility;

@Service("UserService")
public class UserService {

	@Autowired 
	private AppConfig appConfig;
	
	@Autowired
	private Utility utility;
	
	public HashMap<String, String> validateLogin(String username, String password) {
		
		HashMap<String, String> userDetails = new HashMap<String, String>();
		
		System.out.println(".validateLogin " + username);
		System.out.println(".validateLogin " + password);
		
		try {
			Context ctx = utility.getInitialContext();
			LoginRemote loginService = (LoginRemote) ctx.lookup(appConfig.getLoginJndi());
			HashMap<String, String> user = loginService.validateLogin(username, password);
			System.out.println(".validateLogin " + user);
			if (null != user) {
				userDetails.put("username", username);
				userDetails.put("role", user.get("role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDetails;
	}
	
	public void applyLeave(String loggedInUser, String leaveDate) {
		
		Context ctx = utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup(appConfig.getLeaveServiceJndi());
				leaveService.applyLeave(loggedInUser, leaveDate);;
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Leave> getLeaves() {
		ArrayList<Leave> leaves = null;
		Context ctx = utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup(appConfig.getLeaveServiceJndi());
				leaves = leaveService.getLeaves();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return leaves;
	}
	
	public ArrayList<Leave> getLeaves(String username) {
		ArrayList<Leave> leaves = null;
		Context ctx = utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup(appConfig.getLeaveServiceJndi());
				leaves = leaveService.getLeaves(username);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return leaves;
	}
	
	public void approveLeave(String leaveId, String jmsMessageId, String leaveApprovedBy) {
		Context ctx = utility.getInitialContext();
		if(ctx != null) {
			try {
				LeaveServiceRemote leaveService = (LeaveServiceRemote) ctx.lookup(appConfig.getLeaveServiceJndi());
				leaveService.approveLeave(leaveId, jmsMessageId, leaveApprovedBy);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}
	
}
