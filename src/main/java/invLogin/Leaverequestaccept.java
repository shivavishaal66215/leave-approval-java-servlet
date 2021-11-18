package invLogin;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;
import java.util.ArrayList;

public class Leaverequestaccept extends HttpServlet{
	
	public static void acceptreq(String reqid,String manid)
	{
		String url = "jdbc:mysql://localhost:3306/LeaveApprovalSystem";
		String uname="root";
		String pass="";
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, uname, pass);   
			Statement st=con.createStatement();
			int i=st.executeUpdate("update Log set Status='Approved',ApprovedBy='" + manid + "' where reqid= '" + reqid + "'");
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
        	//pw.println(uid);
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        
        String rid=req.getParameter("reqId");
        try {
        	acceptreq(rid,manid);
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        
        res.sendRedirect("Leaverequest");
 
    }
}
