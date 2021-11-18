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
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="styles/leaverequest.css">
    <title>Leave Request</title>
</head>
<body>
    <div id="nav-placeholder"></div>

    <div id="leaveRequestTitle">Leave Request</div>
    <form>
    <div class="leaveRequestcontainer">
    <%!
    	ResultSet rs;
    	String reqid;
    	String uid;
		String sdate;
		String edate;
		String subj;
		String proof;
    %>
    <%
		rs=(ResultSet)request.getAttribute("leaverequests");
		while(rs.next()) 
        {
			reqid=rs.getString("ReqId");
        	uid=rs.getString("EmpId");
        	sdate=rs.getString("StartDate"); 
            edate=rs.getString("EndDate"); 
            subj=rs.getString("Subject"); 
            proof=rs.getString("Proof"); 
    %>
        <div class="leaveRequest">
            <div class='leaveRequestUserIdTitle'>User ID</div>
            <div class='leaveRequestUserId'> <%= uid %> </div>

            <div class='leaveRequestStartDateTitle'>Start Date</div>
            <div class='leaveRequestStartDate'> <%= sdate %> </div>

            <div class='leaveRequestEndDateTitle'>End Date</div>
            <div class='leaveRequestEndDate'> <%= edate %> </div>

            <div class='leaveRequestSubjectTitle'>Subject</div>
            <div class='leaveRequestSubject'> <%= subj %> </div>

            <div class='leaveRequestProofTitle'>Proof</div>
            <a href=<%= proof %> target="_blank"><div class='leaveRequestProof'> <%= proof %> </div></a>        
            
            <div class="leaveRequestButtons">
            	<input type="text" style="display:none" name="reqId" value=<%= reqid %> >
                <button type="submit" class="leaveRequestAccept" formaction="Leaverequestaccept">Accept</button>
				<button type="submit" class="leaveRequestReject" formaction="Leaverequestreject">Reject</button>
            </div>
        </div>
    <% } %>
    </div>
    </form>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script>
        $(function () {
            $("#nav-placeholder").load("nav_login.html");
        });
    </script>
</body>
</html>