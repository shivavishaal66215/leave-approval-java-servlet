package invLogin;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;
import java.util.ArrayList;

public class Allrequest extends HttpServlet{
    
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
		PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        HttpSession session=req.getSession(false);    
        String uid=(String)session.getAttribute("username");
        
        try {
        	if(uid.charAt(0) == 'E') {
        		//do nothing
        	}
        	else if(uid.charAt(0) == 'M') {
        		throw new Exception("error");   
        	}
        }
        catch(Exception e) 
        {
            res.sendRedirect("home.html");
        }
        String url = "jdbc:mysql://localhost:3306/LeaveApprovalSystem";
        String uname="root";
        String pass="";
        try 
        {
        	ArrayList<String> allreq=new ArrayList<String>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(url, uname, pass);   
            Statement st=con.createStatement();  
            ResultSet rs=st.executeQuery("select * from Log where EmpId='"+uid+"';"); 
            req.setAttribute("allrequests",rs);
            RequestDispatcher view = req.getRequestDispatcher("/Allrequest.jsp");  
            view.forward(req,res);
            st.close();
            con.close();
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        pw.close();  
    }
}
