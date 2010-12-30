<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Welcome students</title>
<style>
body {
	font-family: segoe ui, verdana;
}
#divMain {
	width: 810px;
	position: relative; 
	margin: 0pt auto; 
}

#tableMain {
	width: 800px;
}

.tdDescription {
	width: 40%;
	vertical-align:top;
}

.tdMeta {
	width: 30%;
	color: gray;
	font-size: 0.9em;
	background-color: beige;
	border-collapse:collapse;
}

.tdTutorialName {
	width: 30%;
	vertical-align:top;
	background-color: beige;
}
#divBanner {
	width: 810px;
	background-color: beige;
	height: 100px; 
	margin:0 auto;
	position: relative;
}
a[href] {
	color: #D96C00;
}
</style>

</head>
<body>
<%
ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(null);
session.setAttribute("tutorialInfoList", tutorialInfoList);
int STUDENT_ID = 99;
session.setAttribute("studentId", STUDENT_ID);
DeliverableLauncher launcher = new DeliverableLauncher();
session.setAttribute("deliverableLauncher", launcher);
%>
<div id="divBanner">
Main Banner
</div>
<div id="divMain">
<form id="formLaunch">
<table border="0" id="tableMain">
<tbody>

<%
for (TutorialInfo tutorialInfo : tutorialInfoList) {
	out.println("<tr>");
	out.print("<td rowspan=\"3\" class=\"tdTutorialName\">");
	out.print("<a href=\"studentMainTest.jsp?start=1&id=" + tutorialInfo.getTutorialId() + "\">");
	out.print(tutorialInfo.getTutorialName());
	out.println("</a></td>");
	out.print("<td class=\"tdMeta\">Created by : ");
	out.print(tutorialInfo.getCreatedBy());
	out.println("</td>");
	out.print("<td rowspan=\"3\" class=\"tdDescription\">Description : ");
	String description = tutorialInfo.getTutorialDescription();
	out.print((description.length() > 30) ? description.substring(0, 30) + "..." : description);
	out.println("</td></tr>");
	
	out.print("<tr><td class=\"tdMeta\">Date : ");
	out.print(tutorialInfo.getCreationDate().toString());
	out.println("</td></tr>");
	
	out.print("<tr><td class=\"tdMeta\">Downloads : ");
	out.print(tutorialInfo.getTimesAccessed());
	out.println("</td></tr>");
}
%>

</tbody>
</table>
</form>
</div>
</body>
</html>