package com.demo.model.leave;

import java.io.Serializable;

public class Leave implements Serializable{

	private static final long serialVersionUID = 1L;

	private String leaveId;
	private String jmsMessageId;
	private String leaveAppliedBy;
	private String leaveApprovedBy;
	private String leaveDate;
	private boolean status;
	private String createdOn;
	private String modifiedOn;
	
	public Leave() {}

	
	public String getLeaveId() {
		return leaveId;
	}


	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}


	public String getJmsMessageId() {
		return jmsMessageId;
	}


	public void setJmsMessageId(String jmsMessageId) {
		this.jmsMessageId = jmsMessageId;
	}


	public String getLeaveAppliedBy() {
		return leaveAppliedBy;
	}

	public void setLeaveAppliedBy(String leaveAppliedBy) {
		this.leaveAppliedBy = leaveAppliedBy;
	}

	public String getLeaveApprovedBy() {
		return leaveApprovedBy;
	}

	public void setLeaveApprovedBy(String leaveApprovedBy) {
		this.leaveApprovedBy = leaveApprovedBy;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	@Override
	public String toString() {
		return "Leave [leaveId=" + leaveId + ", jmsMessageId=" + jmsMessageId + ", leaveAppliedBy=" + leaveAppliedBy
				+ ", leaveApprovedBy=" + leaveApprovedBy + ", leaveDate=" + leaveDate + ", status=" + status
				+ ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

}
