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
	
  public static boolean check_registercred(String username,String userid, String password, String confirm, String type) throws Exception
  {
	  
	  if(!confirm.equals(password)) {
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
      
      String addon = "";
      if(type.equals("Employee")) {
    	  addon = "Emp";
      }
      else {
    	  addon = "Man";
      }
      
      //Checking if user already exists in the system
      Connection con=DriverManager.getConnection(url, uname, pass);   
      Statement st=con.createStatement();  
      ResultSet rs=st.executeQuery("select * from "+ type + " where " + addon + "Id='" +userid+"';"); 
      
      
      String pd = "";
      int count = 0;
      while(rs.next()) 
      {
          System.out.println(rs.getString(1)); 
          pd=rs.getString(1);
          count += 1;
      }
      
      if(count > 0) {
    	  return false;
      }
      
      //Creating new user
      int rs2 = st.executeUpdate("insert into " + type + "(" + addon+"Id ,"+addon+"Name,"+addon+"Password) VALUES("+userid+","+username+","+password+");"); 
      
      st.close();
      con.close();
      
      return true;
  }

	
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
        PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        
        
        String username = req.getParameter("username");
        String userid = req.getParameter("login-user-id");
        String pass = req.getParameter("login-password");
        String confirm = req.getParameter("confirm-password");
        String type = req.getParameter("registration-type");
        try {
	        if(check_registercred(username,userid,pass,confirm,type))
	        {
	            pw.println("Registration Success...!");
	        }
	        else
	        {
	            pw.println("Registration Failed...!");
	        }
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        pw.close();
    }
}