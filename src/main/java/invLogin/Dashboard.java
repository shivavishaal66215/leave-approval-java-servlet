package invLogin;

import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dashboard extends HttpServlet
{
    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
        
    	HttpSession session=req.getSession(false);    
        String uid=(String)session.getAttribute("username");
        
        if(uid.charAt(0) == 'E') {
        	res.sendRedirect("employeedash.html");
        }
        else {
        	res.sendRedirect("managerdash.html");
        }
    }
}