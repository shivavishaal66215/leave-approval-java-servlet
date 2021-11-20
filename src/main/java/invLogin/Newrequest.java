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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Newrequest extends HttpServlet
{ 
  public static int date_diff(String fdate,String idate)
  {
	  SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
	  String inputString1 = idate.substring(8)+" "+idate.substring(5,7)+" "+idate.substring(0,4);
	  String inputString2 = fdate.substring(8)+" "+fdate.substring(5,7)+" "+fdate.substring(0,4);

	  try {
	      Date date1 = myFormat.parse(inputString1);
	      Date date2 = myFormat.parse(inputString2);
	      long diff = date2.getTime() - date1.getTime();
	      long x = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	      return (int)x;
	  } catch (ParseException e) {
	      e.printStackTrace();
	  }
	  return 0;
  }
  public static boolean check_newreqcred(String idate,String fdate,String empid,String ltype,String proof) throws Exception
  {
	  if(proof.trim().equals("")||idate.trim().equals("")||fdate.trim().equals("")||(fdate.compareTo(idate)<=0)||
	    (date_diff(fdate,idate)>3 && ltype.compareTo("Casual Leave")==0)||(date_diff(fdate,idate)>15 && ltype.compareTo("Sick Leave")==0)||
	    (date_diff(fdate,idate)>16 && ltype.compareTo("Earned Leave")==0)||(date_diff(fdate,idate)>84 && ltype.compareTo("Paternity Leave")==0)||
	    (date_diff(fdate,idate)>15 && ltype.compareTo("Marriage Leave")==0))
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
        try {
        	if(empid.charAt(0) == 'E') {
        		//do nothing
        	}
        	else if(empid.charAt(0) == 'M') {
        		throw new Exception("error");   
        	}
        }
        catch(Exception e) 
        {
            res.sendRedirect("home.html");
        }
        
        String idate=req.getParameter("initialdate");
        String fdate=req.getParameter("finaldate");
        String ltype=req.getParameter("leavetype");
        String proof=req.getParameter("proof");
        //System.out.println(idate+" "+ fdate + " "+ ltype +" "+proof);
        
        try {
	        if(check_newreqcred(idate,fdate,empid,ltype,proof))
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
	            
	            //System.out.println("insert into Log values('"+reqid+"','"+strCurrentDate+"','"+empid+"','"+idate+"','"+fdate+"','"+ltype+"','Pending','"+proof+"','');");
	            st.executeUpdate("insert into Log values('"+reqid+"','"+strCurrentDate+"','"+empid+"','"+idate+"','"+fdate+"','"+ltype+"','Pending','"+proof+"','');"); 
	            st.close();
	            con.close();
	            res.sendRedirect("Dashboard");
	        }
	        else
	        {
	             pw.println("<script type=\"text/javascript\">");
	        	 pw.println("alert('You have selected too many days');");
	        	 pw.println("location='Newrequest.html';");
	        	 pw.println("</script>");
	        }
        }
        catch(Exception e) 
        {
             System.out.println(e);
             pw.println("<script type=\"text/javascript\">");
	       	 pw.println("alert('Something went wrong');");
	       	 pw.println("location='Newrequest.html';");
	       	 pw.println("</script>");
        }
        
        pw.close();
    }
}