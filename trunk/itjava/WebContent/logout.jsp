<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*, java.io.*, java.security.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Sign User Out</title>
</head>

<body>
<%
	String redirectURL1 = "";
	redirectURL1 = "index.jsp";
	session.setAttribute("userName", null);
	session.setAttribute("userID", "0");
	response.sendRedirect(redirectURL1);
%>
</body>
</html>