package com.demo.service.leave;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.demo.model.leave.Leave;

@Remote
public interface LeaveServiceRemote {

	public void applyLeave(String leaveAppliedBy, String leaveDate);
	public ArrayList<Leave> getLeaves();
	public void approveLeave(String leaveAppliedBy, String leaveApprovedBy, String leaveDate, String jmsMessageId);
	public void saveLeave(String leaveAppliedBy, String leaveApprovedBy, String leaveDate, String jmsMessageId);
}
