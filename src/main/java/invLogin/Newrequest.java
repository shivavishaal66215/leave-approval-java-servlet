package invLogin;

import java.io.*;  
import java.math.BigInteger;
import javax.servlet.*;  
import javax.servlet.http.*;  

import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Newrequest extends HttpServlet
{ 
  public static boolean check_newreqcred(String idate,String fdate,String empid,String subject,String proof) throws Exception
  {
	  if(subject.trim().equals("")||proof.trim().equals("")||idate.trim().equals("")||fdate.trim().equals("")||(fdate.compareTo(idate)<=0))
	  {
		  return false;
	  }
	 
      return true;
  }
  
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
        PrintWriter pw=res.getWriter();
        res.setContentType("text/html");
        HttpSession session=req.getSession(false);
        String empid=(String)session.getAttribute("username");
        
        String idate=req.getParameter("initialdate");
        String fdate=req.getParameter("finaldate");
        String subject=req.getParameter("subject");
        String proof=req.getParameter("proof");
        //System.out.println(idate+" "+ fdate + " "+subject+" "+proof);
        
        try {
	        if(check_newreqcred(idate,fdate,empid,subject,proof))
	        {
	        	String url = "jdbc:mysql://localhost:3306/leaveapprovalsystem";
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
	           
	            
	            
//	            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
	            Connection con=DriverManager.getConnection(url, uname, pass);   
	            Statement st=con.createStatement();
	            Date currentDate = new Date();
	            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String strCurrentDate = df.format(currentDate);
	            
	            ResultSet rs=st.executeQuery("select ReqId from Log;");
	            String reqid="R";
	            String r="1";
	            BigInteger maxx=new BigInteger(r);
	            while(rs.next())
	            {
	            	r=rs.getString("ReqId");
	            	r=r.substring(1);
	            	BigInteger reqno=new BigInteger(r);
	            	if(maxx.compareTo(reqno)<0)
	            		maxx=reqno;
	            }
	            maxx=maxx.add(new BigInteger("1"));
	            reqid = reqid + "" + maxx;
	            
	            //System.out.println("insert into Log values('"+reqid+"','"+strCurrentDate+"','"+empid+"','"+idate+"','"+fdate+"','"+subject+"','Pending','"+proof+"','');");
	            st.executeUpdate("insert into Log values('"+reqid+"','"+strCurrentDate+"','"+empid+"','"+idate+"','"+fdate+"','"+subject+"','Pending','"+proof+"','');"); 
	            st.close();
	            con.close();
	            pw.println("Database update success");
	        }
	        else
	        {
	            pw.println("Database update fail");
	        }
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
        
        pw.close();
    }
}