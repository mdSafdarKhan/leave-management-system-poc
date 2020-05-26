<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	   <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
	
</head>
<body>
	<div class="container" align="center" style=" border: 2px ;">
		<h1>Login</h1>
		<form action="<%=request.getContextPath() %>/validateLogin" method="post">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password"></td>
				</tr>
			</table>
			<input type="submit" name="button" value="Submit">
		</form>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>