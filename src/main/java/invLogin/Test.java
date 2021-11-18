package invLogin;

import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
        PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        
        HttpSession session=req.getSession(false);  
        String n=(String)session.getAttribute("username");  
        
        try {
        	pw.println(n);
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        pw.close();
    }
}
