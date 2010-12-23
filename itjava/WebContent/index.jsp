<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.view.*, java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Tutorial Search</title>
<style>
#divProgress {
	width:300px;
	height:200px;
	position:fixed;
	top:30%;
	left:30%;
	font-family: segoe ui, verdana;
	display: none;
}

</style>
<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script type="text/javascript">
function showProgress() {
	document.getElementById("divProgress").style.display = 'table-cell';
}
</script>
</head>
<body>
<%
session.setAttribute("username", "Aniket");
%>
<form action="CodeSearchServlet" method="get" name="codeSearchForm" id="codeSearchForm">
<pre>
  <input type="text" name="query" id="query" placeholder="Enter query"/>  <input type="submit" onclick="return showProgress();" value="Search" id="btnSearch"/>
</pre>
</form>
<div id="divProgress">
<img src="images/loopLoader.gif" /><br />
Searching the web for code snippets..
</div>
</body>
</html>