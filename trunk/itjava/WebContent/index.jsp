<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.view.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Tutorial Search</title>
</head>
<body>
<form action="CodeSearchServlet" method="get" name="codeSearchForm" id="codeSearchForm">
<pre>
  <input type="text" name="query" id="query" placeholder="Enter query" />  <input type="submit" value="Search" />
</pre>
</form>
</body>
</html>