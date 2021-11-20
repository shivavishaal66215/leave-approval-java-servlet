package invLogin;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;
import java.util.ArrayList;

public class Newrequest_Test extends HttpServlet{
    
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
		PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        HttpSession session=req.getSession(false);    
        String uid=(String)session.getAttribute("username");
        
        try {
        	if(uid.charAt(0) == 'E') {
        		res.sendRedirect("Newrequest.html");
        	}
        	else if(uid.charAt(0) == 'M') {
        		throw new Exception("error");   
        	}
        }
        catch(Exception e) 
        {
            res.sendRedirect("home.html");
        }
        pw.close();  
    }
}
