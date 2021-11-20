<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="java.io.IOException,java.io.PrintWriter,
javax.servlet.ServletException,javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
java.sql.Connection, javax.servlet.*, javax.servlet.http.*,
java.sql.DriverManager, java.sql.ResultSet, java.sql.SQLException,
java.sql.Statement" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" type="text/css" href="styles/newuserrequest.css" />
		<title>New User Requests</title>
	</head>
	<body>
		<div id="nav-placeholder"></div>

		<div id="newUserRequestTitle">New User Requests</div>

		<div class="newUserRequestcontainer">
			<%!
            ResultSet rs; 
            String empid;
			String empName;
			%>
			
			<%
            rs = (ResultSet)request.getAttribute("allrequests"); 
            while(rs.next()) {
			empid=rs.getString("EmpId");
			empName=rs.getString("EmpName"); 
			%>
			
			<form>
			<div class="newUserRequest">
				<div class="newUserRequestNameTitle">Name</div>
				<div class="newUserRequestUserId"><%= empid %></div>
				<input type="text" style="display:none" name="empid" value=<%= empid %> >
				
				<div class="newUserRequestUserIdTitle">User ID</div>
				<div class="newUserRequestUserId"><%= empName %></div>
				<input type="text" style="display:none" name="empName" value=<%= empName %> >

				<div class="newUserRequestButtons">
					<button type="submit" class="newUserRequestAccept" formaction="NewUserRequestsAccept">Accept</button>
					<button type="submit" class="newUserRequestReject" formaction="NewUserRequestsReject">Reject</button>
				</div>
			</div>
			</form>

			<% } %>
		</div>
		<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
		<script>
			$(function () {
				$("#nav-placeholder").load("nav_login.html");
			});
		</script>
	</body>
</html>