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
</head>
<body>
	
	<%@include file="header.jsp"%>
	<hr>
	<div class="container" id="body" align="center">
		<h3 align="center">Leave management system</h3>
	</div>
	<%
		String username = (String) session.getAttribute("username");
		String role = (String) session.getAttribute("role");
	%>

	<% if(username != null){ %>
	<div class="container" align="center">
		<h5 style="text-align: center; color: blue;;"> 
			Hello <%= username.toUpperCase() %>. You are <%= role.toUpperCase() %> 
		</h5>
	</div>
	
	<hr>
	
	<div class="container" align="center">
		<%if (role != null){ %>
				<%if(role.equals("DEVELOPER")){ %>
					<h5>Apply Leave</h5>
					<form action="<%=request.getContextPath() %>/applyLeave" method="post">
						<table>
							<tr>
								<td>Leave Date</td>
								<td><input type="text" name="leaveDate" placeholder="29/05/2020"></td>
							</tr>
						</table>
						<input type="submit" name="button" value="Submit">
					</form>
				<%} %>
				<%	if(role.equals("PM")){ 
						ApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);
						UserService a = (UserService) ctx.getBean("UserService");
						ArrayList<Leave> leaves = a.getLeaves(); 
						int totalLeaves = 0;
						if(leaves != null){
							totalLeaves = leaves.size();	
						}
						%>
						<h5>Total leaves <%= totalLeaves %></h5>
						<%if(totalLeaves > 0) {%>
							<table>
								<tr>
									<th>Applied By</th>
									<th>Leave Date</th>
								</tr>
								<% for(Leave leave : leaves){ 
									String leaveAppliedBy = leave.getLeaveAppliedBy();
									String leaveDate = leave.getLeaveDate();
									String messageId = leave.getMessageId();
									out.print("messageId: " + messageId);
								%>
								<tr>
									<td><%=leaveAppliedBy%></td>
									<td><%=leaveDate%></td>
									<td>
										<form action="<%=request.getContextPath() %>/approveLeave" method="post">
											<input type="hidden" name="leaveAppliedBy" value="<%=leaveAppliedBy%>" />
											<input type="hidden" name="leaveApprovedBy" value="<%=username%>" />
											<input type="hidden" name="leaveDate" value="<%=leaveDate%>" />
											<input type="hidden" name="jmsMessageId" value="<%=messageId%>" />
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