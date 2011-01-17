<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, java.io.*, java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Update Student Account</title>
</head>

<body>
<%
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement ucpst = null;
		ResultSet rs = null;
		String password = null;
		String firstName = null;
		String lastName = null;
		String school = null;
		String studentID = null;
		String sql = null;
		String redirectURL1 = null;

	   try
	   {
		    firstName = request.getParameter("firstName3");
		    lastName = request.getParameter("lastName3");
		    school = request.getParameter("school3");
		    studentID = (String) session.getAttribute("userID");
	   		password = request.getParameter("password3");
	   		byte[] defaultBytes = password.getBytes();
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();
			
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<messageDigest.length;i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			String foo = messageDigest.toString();
			password=hexString+"";

		    conn = DBConnection.GetConnection();
			if(!password.equals("d41d8cd98f0b24e980998ecf8427e")){
				sql = "UPDATE students SET password=?, firstName=?, lastName=?, school=? WHERE studentID=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, password);
				pst.setString(2, firstName);
				pst.setString(3, lastName);
				pst.setString(4, school);
				pst.setString(5, studentID);
			}else{
				sql = "UPDATE students SET firstName=?, lastName=?, school=? WHERE studentID=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, firstName);
				pst.setString(2, lastName);
				pst.setString(3, school);
				pst.setString(4, studentID);
			}
			pst.executeUpdate();
			conn.close();
			redirectURL1 = "accountStudent.jsp?success=1"; 
			response.sendRedirect(redirectURL1);
	   }
	   catch(Exception e) {
  	     e.printStackTrace();
  	   }
  	   finally {
  	    
  	   }
	   
	   
	   %>
</body>
</html>