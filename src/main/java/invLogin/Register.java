package invLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public static boolean validation(String username,String userid, String password, String confirmpassword, String registrationtype) throws Exception
	  {
		  
		  if(!confirmpassword.equals(password)) {
			  return false;
		  }
		  
		  if(!((userid.charAt(0) == 'E' && registrationtype.equals("employee")) || (userid.charAt(0) == 'M' && registrationtype.equals("manager")))) {
			  return false;
		  }
		  
		  String dburl ="jdbc:mysql://localhost:3306/leaveapprovalsystem";
		  String dbuname="root";
		  String dbpassword="";
		  String dbdriver="com.mysql.cj.jdbc.Driver";
		  try 
	      {
	          Class.forName(dbdriver); 
	      }
	      catch(Exception e) 
	      {
	          System.out.println(e);
	      }
	     
	      String value = "";
	      if(registrationtype.equals("employee")) {
	    	  value = "Emp";
	      }
	      else {
	    	  value = "Man";
	      }
	      
	      
	      Connection con=DriverManager.getConnection(dburl, dbuname, dbpassword);   
	      Statement st=con.createStatement();  
	      ResultSet set= st.executeQuery("select * from "+ registrationtype + " where " + value + "Id='" +userid+"';"); 
	      
	      
	      String pd = "";
	      int count = 0;
	      while(set.next()) 
	      {
	          System.out.println(set.getString(1)); 
	          pd=set.getString(1);
	          count += 1;
	      }
	      
	      if(count > 0) {
	    	  return false;
	      }
	      
	     
	      int set1 = st.executeUpdate("insert into " + registrationtype + "(" + value + "Id," + value + "Name," + value + "Password) VALUES('"+userid+"','"+username+"','"+password+"');");
	      
	      st.close();
	      con.close();
	      
	      return true;
	  }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
		 	PrintWriter pw=response.getWriter();
	        response.setContentType("text/html");
	        
	        
	        String username = request.getParameter("username");
	        String userid = request.getParameter("userid");
	        String password = request.getParameter("password");
	        String confirmpassword = request.getParameter("confirmpassword");
	        String registrationtype = request.getParameter("registration-type");
	        try {
		        if(validation(username,userid,password,confirmpassword,registrationtype))
		        {
		            response.sendRedirect("home.html");
		        }
		        else
		        {
		        	 pw.println("<script type=\"text/javascript\">");
		        	 pw.println("alert('Data entered is invalid.');");
		        	 pw.println("location='register.html';");
		        	 pw.println("</script>");
		        }
	        }
	        catch(Exception e) 
	        {
	            System.out.println(e);
	        }
	        pw.close();
	    }
	}