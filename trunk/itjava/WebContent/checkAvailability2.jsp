<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Check Availability</title>
<style type="text/css">
<!--
body table tr td {
	text-align: center;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 14px;
}
.asdf {
	font-size: 12px;
	font-weight: normal;
	color: #030;
}
.ddas {
	color: #900;
	font-size: 12px;
	font-weight: normal;
}
-->
</style>
</head>

<body>
<table width="400" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3">Check For Username Availability</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3">
    	<%
			Connection conn = null;
			PreparedStatement pst = null;
			ResultSet rs = null;

		   try
		   { 
			   String username = request.getParameter("username");
			   conn = DBConnection.GetConnection();
			   String sql = "Select username FROM students WHERE username = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, username);
				rs = pst.executeQuery();
				if(rs.next()){
					out.print("<span class=\"ddas\">The username you selected is not available, <br />please try a different name.</span>");
				}else{
					out.print("<span class=\"asdf\">The username you selected is available!</span><br />");
				}
				conn.close(); 
		   }
		   catch(Exception e) {
      	     e.printStackTrace();
      	   }
      	   finally {
      	     conn.close();
      	   }
		   
		   
		   %>
        
      	</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>