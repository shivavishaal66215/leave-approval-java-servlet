<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="java.io.IOException,java.io.PrintWriter,
javax.servlet.ServletException,javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest,
javax.servlet.http.HttpServletResponse,
java.sql.Connection,
javax.servlet.*,
javax.servlet.http.*,
java.sql.DriverManager,
java.sql.ResultSet,
java.sql.SQLException,
java.sql.Statement"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" type="text/css" href="styles/allrequest.css" />
		<title>All Requests</title>
	</head>
	<body>
		<div id="nav-placeholder"></div>

		<div id="allRequestTitle">All Requests</div>
		<div class="allRequestcontainer">
			<%!
				ResultSet rs;
				String reqid;
				String sdate;
				String edate;
				String subj;
				String proof;
				String status;	
			%>
			
			<%
				rs = (ResultSet)request.getAttribute("allrequests");
				while(rs.next()) 
            	{
            		//System.out.println(rs.getString("EmpId"));
            		reqid=rs.getString("ReqId");
					sdate=rs.getString("StartDate"); 
            		edate=rs.getString("EndDate"); 
            		subj=rs.getString("Subject"); 
            		proof=rs.getString("Proof"); 
            		status=rs.getString("Status");
            	%>	
            		<form>
            		<div class='allRequest'>
            	
            			<div class='allRequestStartDateTitle'>Start Date</div>
            			<div class='allRequestStartDate'> <%= sdate %> </div>
            			
            			<div class='allRequestEndDateTitle'>End Date</div>
            			<div class='allRequestEndDate'> <%= edate %> </div>
            			
            			<div class='allRequestSubjectTitle'>Subject</div>
            			<div class='allRequestSubject'> <%= subj %> </div> 
            			
            			<div class='allRequestProofTitle'>Proof</div>
            			<a href=<%= proof %> target="_blank"><div class='allRequestProof'> <%= proof %> </div></a>
            	        
            	        <div class='allRequestStatusTitle'>Status</div>
            	        <div class='allRequestStatus'> <%= status %> </div>
            	        
            	        <div class='allRequestButtons'>
            	        <%
							if(status.equals("Pending")) 
							{
						%>
								<input type="text" style="display:none" name="reqid" value=<%= reqid %> >
            					<button type="submit" class="allRequestCancel" formaction="Allrequestcancel">Cancel</button>
            						
						<% } %>
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
