<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.lms.client.service.UserService"%>
<%@page import="com.demo.model.leave.Leave"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	   <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
	
</head>
<body>
	
	<%	ApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);
		UserService a = (UserService) ctx.getBean("UserService"); 
	%>
	
	<%@include file="header.jsp"%>
	<hr>
	<div class="container" id="body" align="center" style=" border: 2px ;">
		<h3 align="center">Leave management system</h3>
	</div>
	<%
		String username = (String) session.getAttribute("username");
		String role = (String) session.getAttribute("role");
	%>

	<% if(username != null){ %>
	<div class="container" align="center" style=" border: 2px ;">
		<h5 style="text-align: center; color: blue;;"> 
			Hello <%= username.toUpperCase() %>. You are <%= role.toUpperCase() %> 
		</h5>
	</div>
	
	<hr>
	
	<div class="container" align="center" style=" border: 2px ;">
		<%if (role != null){ %>
				<%if(role.equals("DEVELOPER")){ %>
					<h5>Apply Leave</h5>
					<form action="<%=request.getContextPath() %>/applyLeave" method="post">
						<table class="table">
							<tr>
								<td>Leave Date</td>
								<td><input type="text" name="leaveDate" placeholder="29/05/2020"></td>
							</tr>
						</table>
						<input type="submit" name="button" value="Submit">
					</form>
					
					<hr>
					<h5>Your Leave Status</h5>
					<%
						ArrayList<Leave> leaves = a.getLeaves(username); 
						int totalLeaves = 0;
						if(leaves != null){
							totalLeaves = leaves.size();	
						}
					%>
					<h5>You have applied total <%= totalLeaves %> leaves</h5>
					<%if(totalLeaves > 0) {%>
							<table class="table">
								<tr>
									<th>Leave ID</th>
									<th>Leave Date</th>
									<th>Leave Status</th>
									<th>Approved By</th>
								</tr>
								<% for(Leave leave : leaves){ 
									String leaveId = leave.getLeaveId();
									String leaveDate = leave.getLeaveDate();
									Boolean status = leave.isStatus();
									String leaveApprovedBy = leave.getLeaveApprovedBy();
								%>
								<tr>
									<td><%=leaveId%></td>
									<td><%=leaveDate%></td>
									<td><%=status%></td>
									<td><%=leaveApprovedBy%></td>
								</tr>								
								<%} %>
							</table>
						<%} %>
				
				<%} %>
				<%	if(role.equals("PM")){ 
						ArrayList<Leave> leaves = a.getLeaves(); 
						int totalLeaves = 0;
						if(leaves != null){
							totalLeaves = leaves.size();	
						}
						%>
						<h5>Total leaves <%= totalLeaves %></h5>
						<%if(totalLeaves > 0) {%>
							<table class="table">
								<tr>
									<th>Applied By</th>
									<th>Leave Date</th>
								</tr>
								<% for(Leave leave : leaves){ 
									
									String leaveAppliedBy = leave.getLeaveAppliedBy();
									String leaveDate = leave.getLeaveDate();
									
									String leaveId = leave.getLeaveId();
									String jmsMessageId = leave.getJmsMessageId();
								%>
								<tr>
									<td><%=leaveAppliedBy%></td>
									<td><%=leaveDate%></td>
									<td>
										<form action="<%=request.getContextPath() %>/approveLeave" method="post">
											<input type="hidden" name="leaveId" value="<%=leaveId%>" />
											<input type="hidden" name="jmsMessageId" value="<%=jmsMessageId%>" />
											<input type="hidden" name="leaveApprovedBy" value="<%=username%>" />
											<input type="submit" name="submit" value="Approve" />
										</form>
									</td>
								</tr>								
								<%} %>
							</table>
						<%} %>
				<%} %>
			<%} %>
		
		<%} %>
	</div>
		

</body>
</html>