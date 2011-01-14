<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, java.io.*, java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create Student Account</title>
</head>

<body>
<%
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement ucpst = null;
		ResultSet rs = null;

	   try
	   {   
		   String username = request.getParameter("username2");
		   String password = request.getParameter("password2");
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
			String usercheck = "SELECT studentID, username FROM students WHERE username = ? AND password = ?";
		   ucpst = conn.prepareStatement(usercheck);
		   ucpst.setString(1, username);
		   ucpst.setString(2, password);
		   rs = ucpst.executeQuery();
		   if(rs.next()){
				session.setAttribute("userName", rs.getString("username"));
				session.setAttribute("userID", rs.getString("studentID"));
				session.setAttribute("userLevel", "student");
				conn.close();
				String redirectURL1 = "students.jsp"; 
				response.sendRedirect(redirectURL1);
		   }else{
			   conn.close();
			   String redirectURL2 = "students.jsp?error=4"; 
			   response.sendRedirect(redirectURL2);
		   }
	   }
	   catch(Exception e) {
  	     e.printStackTrace();
  	   }
  	   finally {
  	     conn.close();
  	   }
	   
	   
	   %>
</body>
</html>