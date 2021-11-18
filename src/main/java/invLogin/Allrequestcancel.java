package invLogin;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;
import java.util.ArrayList;

public class Allrequestcancel extends HttpServlet{
	
	public static void deletereq(String id)
	{
		String url = "jdbc:mysql://localhost:3306/LeaveApprovalSystem";
		String uname="root";
		String pass="";
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, uname, pass);   
			Statement st=con.createStatement();
			int i=st.executeUpdate("delete from Log where ReqId='"+id+"'");
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
        String reqid=req.getParameter("reqid");
        try {
        	deletereq(reqid);
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        
        res.sendRedirect("Allrequest");
 
    }
}
