<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Success</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	   <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700" rel="stylesheet">
	
</head>
<body>
	<div class="container" align="center" style=" border: 2px ;">
		<h3 style="color: green; margin: 20px; padding: 20px">Success. OK</h3>
		<a href="<%= request.getContextPath()%>/home" style="display: block;">Back Home</a>
	</div>
</body>
</html>