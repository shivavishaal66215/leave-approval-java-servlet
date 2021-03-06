package invLogin;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;
import java.util.ArrayList;

public class NewUserRequestsAccept extends HttpServlet{
	
	public static void deletereq(String empid)
	{
		String url = "jdbc:mysql://localhost:3306/LeaveApprovalSystem";
		String uname="root";
		String pass="";
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, uname, pass);   
			Statement st=con.createStatement();
			
			if(empid.charAt(0) == 'M') {
				int i=st.executeUpdate("update Manager set Valid= '1' where manid= '" + empid + "'");
			}
			else {
				int i=st.executeUpdate("update Employee set Valid= '1' where empid= '" + empid + "'");
			}
			
			
			//out.println("Data Deleted Successfully!");
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	}
    
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
		PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        HttpSession session=req.getSession(false);
        String manid=(String)session.getAttribute("username");
        try {
        	if(manid.charAt(0) == 'M') {
        		//do nothing
        	}
        	else if(manid.charAt(0) == 'E') {
        		throw new Exception("error");   
        	}
        }
        catch(Exception e) 
        {
            res.sendRedirect("home.html");
        }
        
        String empid=req.getParameter("empid");
        String empName=req.getParameter("empName");
        
        try {
        	deletereq(empid);
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        
        res.sendRedirect("NewUserRequests");
 
    }
}
