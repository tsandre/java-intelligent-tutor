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
		   
		   String firstName = request.getParameter("firstName");
		   String lastName = request.getParameter("lastName");
		   String school = request.getParameter("school");
		   String email = request.getParameter("email");
		   String username = request.getParameter("username");
		   String password = request.getParameter("password");
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
			String usercheck = "SELECT username, email FROM students WHERE username = ? OR email = ?";
		   ucpst = conn.prepareStatement(usercheck);
		   ucpst.setString(1, username);
		   ucpst.setString(2, email);
		   rs = ucpst.executeQuery();
		   if(!rs.next()){	
				String sql = "INSERT INTO students(username, password, firstName, lastName, school, email) values(?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, username);
				pst.setString(2, password);
				pst.setString(3, firstName);
				pst.setString(4, lastName);
				pst.setString(5, school);
				pst.setString(6, email);
				pst.executeUpdate();
				
				sql = "SELECT studentID, username FROM students WHERE username = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, username);
				rs = pst.executeQuery();
				rs.next();
				session.setAttribute("userName", rs.getString("username"));
				session.setAttribute("userID", rs.getString("studentID"));
				session.setAttribute("userLevel", "student");
				conn.close();
				String redirectURL1 = "students.jsp"; 
				response.sendRedirect(redirectURL1);
		   }else{
			   if(rs.getString("email").equals(email) && rs.getString("username").equals(username)){
				   conn.close();
				   String redirectURL2 = "students.jsp?error=3"; 
				   response.sendRedirect(redirectURL2);
			   }else if(rs.getString("username").equals(username)){
				   conn.close();
				   String redirectURL3 = "students.jsp?error=1"; 
				   response.sendRedirect(redirectURL3);
			   }else{
				   conn.close();
				   String redirectURL4 = "students.jsp?error=2"; 
				   response.sendRedirect(redirectURL4); 
			   }
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