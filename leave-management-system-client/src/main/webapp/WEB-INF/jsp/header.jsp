<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	   <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
	
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
	<div class="container" id="navbars" style=" border: 2px ;">
		<div class="row-4">
			<a href="<%=request.getContextPath() %>/login">Login</a> 
			<a href="<%=request.getContextPath() %>/register">Register</a>
		</div>
	</div>
</body>
</html>