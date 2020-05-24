<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<style type="text/css">
a {
	display: inline-block;
	text-align: center;
	padding: 10px;
	padding: 10px;
}
</style>
</head>
<body>
	<div class="container" id="navbars">
		<div class="row-4">
			<a href="<%=request.getContextPath() %>/login">Login</a> 
			<a href="<%=request.getContextPath() %>/register">Register</a>
		</div>
	</div>
</body>
</html>