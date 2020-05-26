package com.demo.service.leave;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.sql.DataSource;

import com.demo.model.leave.Leave;

@Stateless
public class LeaveService implements LeaveServiceRemote, LeaveServiceLocal {

	@Resource(name = "loginDataSource")
	private DataSource loginDataSource;

	@Resource(name = "lmsConnectionFactory", mappedName = "lms/lmsConnectionFactory")
	ConnectionFactory cf;

	Connection connection = null;

	@Resource(mappedName = "lms/lmsQueue")
	Queue queue;

	public LeaveService() {
	}

	public void applyLeave(String leaveAppliedBy, String leaveDate) {
		try {

			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer sender = session.createProducer(queue);
			
			String leaveId = UUID.randomUUID().toString();
			
			Leave leave = new Leave();
			leave.setLeaveId(leaveId);
			leave.setLeaveAppliedBy(leaveAppliedBy);
			leave.setLeaveDate(leaveDate);
			
			ObjectMessage objectMessage = session.createObjectMessage(leave);
			sender.send(objectMessage);	//to queue
			
			saveLeave(leaveId, leaveAppliedBy, leaveDate);	//to database
			
			System.out.println(".Leave request sent to queue and saved to database with false status!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Leave> getLeaves() {
		ArrayList<Leave> leaves = null;
		try {
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser = session.createBrowser(queue);
			Enumeration<Message> msgs = browser.getEnumeration();

			if (!msgs.hasMoreElements()) {
				System.err.println("No messages in queue");
			} else {
				leaves = new ArrayList<Leave>();
				while (msgs.hasMoreElements()) {
					Message msg = msgs.nextElement();
					if(msg instanceof ObjectMessage) {
						Leave leave = msg.getBody(Leave.class);
						leave.setJmsMessageId(msg.getJMSMessageID());
						leaves.add(leave);
					}
				}
				System.out.println(".leaves " + leaves);
				System.out.println(".total leaves " + leaves.size());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		return leaves;
	}
	
	public ArrayList<Leave> getLeaves(String username) {
		ArrayList<Leave> leaves = new ArrayList<Leave>();
		String query= "select * from leaves where leaveAppliedBy=?";
		java.sql.Connection connection = null;
		PreparedStatement pStmt = null;

		try {
			connection = loginDataSource.getConnection();
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				String leaveId = rs.getString("leaveId");
				String leaveApprovedBy = rs.getString("leaveApprovedBy");
				String leaveDate = rs.getString("leaveDate");
				Boolean status = rs.getBoolean("status");
				
				Leave leave = new Leave();
				leave.setLeaveId(leaveId);
				leave.setLeaveApprovedBy(leaveApprovedBy);
				leave.setLeaveDate(leaveDate);
				leave.setStatus(status);
				
				leaves.add(leave);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return leaves;
	}

	public void approveLeave(String leaveId, String jmsMessageId, String leaveApprovedBy) {
		String messageId = "JMSMessageID='" + jmsMessageId + "'";
		System.out.println(".messageId: " + messageId);

		MessageConsumer consumer = null;

		try {
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			consumer = session.createConsumer(queue, messageId);
			consumer.receiveNoWait();
			updateLeave(leaveId, jmsMessageId, leaveApprovedBy);
			
			System.out.println(".Leave message consumed from queue and saved to database!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void saveLeave(String leaveId, String leaveAppliedBy, String leaveDate) {

		String query= "insert into leaves (leaveId, leaveAppliedBy, leaveDate, status, createdOn) values (?, ?, ?, ?, ?)";
		java.sql.Connection connection = null;
		PreparedStatement pStmt = null;

		try {
			connection = loginDataSource.getConnection();
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, leaveId);
			pStmt.setString(2, leaveAppliedBy);
			pStmt.setString(3, leaveDate);
			pStmt.setBoolean(4, false);
			pStmt.setString(5, new Date().toString());
			pStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateLeave(String leaveId, String jmsMessageId, String leaveApprovedBy) {
		System.out.println(".leaveId : " + leaveId);
		String query= "update leaves set jmsMessageId=?, leaveApprovedBy=?, status=?, modifiedOn=? where leaveId=?";
		java.sql.Connection connection = null;
		PreparedStatement pStmt = null;

		try {
			connection = loginDataSource.getConnection();
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, jmsMessageId);
			pStmt.setString(2, leaveApprovedBy);
			pStmt.setBoolean(3, true);
			pStmt.setString(4, new Date().toString());
			pStmt.setString(5, leaveId);
			pStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
