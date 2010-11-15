<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, itjava.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Select tutorials here..</title>
</head>
<body>
<form id="tutorialSelectionForm" method="post" action="tutorialSelection.jsp">
<div id="divCode">Tutorial Number# <%= (Integer)session.getAttribute("currentIndex") %><br />
<%
ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>)session.getAttribute("tutorialList");
int currentIndex = (Integer)session.getAttribute("currentIndex");
Tutorial currentTutorial = tutorialList.get(currentIndex);
for (int index = 1; index <= currentTutorial.linesOfCode.size(); index++) {
	out.println(index + " : \t" + currentTutorial.linesOfCode.get(index-1) + "<br />");
}
%>
</div>

<div id="divWordInfo">Corresponding word info list <br />
<%
for (WordInfo currentWordInfo : currentTutorial.wordInfoList) {
	out.println(currentWordInfo.wordToBeBlanked + " : Line num = " + currentWordInfo.lineNumber + "<br />");
}
%>
</div>

<%
session.setAttribute("currentIndex", ++currentIndex);
%>

<div id="divApproveSample">
Do you want to retain this sample code?
</div>

<input type="submit" name="btnSubmit" id="btnSubmit" value="Next Tutorial >>" />
</form>
</body>
</html>