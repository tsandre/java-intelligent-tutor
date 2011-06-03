<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
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

<body OnLoad="javascript:parent.window.close()">
<table width="400" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3">Adding to your favorites, please wait...</td>
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
			PreparedStatement pst2 = null;
			ResultSet rs = null;

		   try
		   { 
			   String theUserLevel = session.getAttribute("userLevel").toString();
			   conn = DBConnection.GetConnection();
			   String sql = "Select user_tutor_id FROM UserTutors WHERE userID = ? AND tutorID = ? AND userLevel = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, request.getParameter("username"));
				pst.setString(2, request.getParameter("id"));
				pst.setString(3, theUserLevel);
				rs = pst.executeQuery();
				if(rs.next()){
					//don't insert again
				}else{
					sql = "INSERT INTO UserTutors(userID, tutorID, userLevel) VALUES(?, ?, ?)";
					pst2 = conn.prepareStatement(sql);
					pst2.setString(1, request.getParameter("username"));
					pst2.setString(2, request.getParameter("id"));
					pst2.setString(3, theUserLevel);
					pst2.executeUpdate();
				}
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