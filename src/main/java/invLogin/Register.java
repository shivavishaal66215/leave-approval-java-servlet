package invLogin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register extends HttpServlet
{
	
  public static boolean check_logincred(String user,String password) throws Exception
  {
	  if(user.trim().equals("")||password.trim().equals(""))
	  {
		  return false;
	  }
      String url = "jdbc:mysql://localhost:3306/Invigilator";
      String uname="root";
      String pass="";
      try 
      {
          Class.forName("com.mysql.cj.jdbc.Driver"); 
      }
      catch(Exception e) 
      {
          System.out.println(e);
      }
      
//      DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
      Connection con=DriverManager.getConnection(url, uname, pass);   
      Statement st=con.createStatement();  
      ResultSet rs=st.executeQuery("select EmpPassword from employee where EmpId='" +user+"';"); 
      
      String pd="";
      while(rs.next() ) 
      {
          System.out.println(rs.getString(1)); 
          pd=rs.getString(1);
      }
      
      st.close();
      con.close();
      
      return password.equals(pd) ; 
  }

	
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
        PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        
        
        String user=req.getParameter("login-user-id");
        String pass=req.getParameter("login-password");

        try {
	        if(check_logincred(user,pass))
	        {
	            pw.println("Login Success...!");
	        }
	        else
	        {
	            pw.println("Login Failed...!");
	        }
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        pw.close();
    }
}