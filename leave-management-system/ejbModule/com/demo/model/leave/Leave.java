package com.demo.model.leave;

import java.io.Serializable;

public class Leave implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String leaveAppliedBy;
	private String leaveApprovedBy;
	private String leaveDate;
	private String messageId;
	
	public Leave() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "Leave [id=" + id + ", leaveAppliedBy=" + leaveAppliedBy + ", leaveApprovedBy=" + leaveApprovedBy
				+ ", leaveDate=" + leaveDate + ", messageId=" + messageId + "]";
	}
	
}
