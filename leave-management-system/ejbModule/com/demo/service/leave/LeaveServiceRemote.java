package com.demo.service.leave;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.demo.model.leave.Leave;

@Remote
public interface LeaveServiceRemote {

	public void applyLeave(String leaveAppliedBy, String leaveDate);
	public ArrayList<Leave> getLeaves();
	public ArrayList<Leave> getLeaves(String username);
	public void approveLeave(String leaveId, String jmsMessageId, String leaveApprovedBy);
	public void saveLeave(String jmsMessageId, String leaveAppliedBy, String leaveDate);
	public void updateLeave(String leaveId, String jmsMessageId, String leaveApprovedBy);
}
