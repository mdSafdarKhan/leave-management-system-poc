package com.demo.service.leave;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;

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
import javax.transaction.Transactional;

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
			
			Leave leave = new Leave();
			leave.setLeaveAppliedBy(leaveAppliedBy);
			leave.setLeaveDate(leaveDate);
			
			sender.send(session.createObjectMessage(leave));

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
						leave.setMessageId(msg.getJMSMessageID());
						leaves.add(leave);
					}
				}
				System.out.println("total leaves: " + leaves.size());
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

	@Transactional
	public void approveLeave(String leaveAppliedBy, String leaveApprovedBy, String leaveDate, String jmsMessageId) {
		String messageId = "JMSMessageID='" + jmsMessageId + "'";
		System.out.println("messageId: " + messageId);

		MessageConsumer consumer = null;

		try {
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			consumer = session.createConsumer(queue, messageId);
			consumer.receiveNoWait();
			saveLeave(leaveAppliedBy, leaveApprovedBy, leaveDate, jmsMessageId);
			
			System.out.println(".Message consumed from queue and saved to database!");
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

	public void saveLeave(String leaveAppliedBy, String leaveApprovedBy, String leaveDate, String jmsMessageId) {

		String query= "insert into leaves (leaveAppliedBy, leaveApprovedBy, leaveDate, messageId) values (?, ?, ?, ?)";
		java.sql.Connection connection = null;
		PreparedStatement pStmt = null;

		try {
			connection = loginDataSource.getConnection();
			pStmt = connection.prepareStatement(query);
			pStmt.setString(1, leaveAppliedBy);
			pStmt.setString(2, leaveApprovedBy);
			pStmt.setString(3, leaveDate);
			pStmt.setString(4, jmsMessageId);
			pStmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
