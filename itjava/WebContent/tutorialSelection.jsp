<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, itjava.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Select tutorials here..</title>
<link type="text/css" rel="stylesheet" href="css/tutorialSelection.css">
</head>
<body>
<div id="divMain">
<form id="tutorialSelectionForm" method="post"
	action="TutorialSelectionServlet">

<%
ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>)session.getAttribute("tutorialList");
int currentIndex = (Integer)session.getAttribute("currentIndex");
Tutorial currentTutorial = tutorialList.get(currentIndex);
%>

<table><tbody><tr>
<%
for (int i = 0; i < tutorialList.size(); i++) {
	String tutorialSelectionClass = (i==currentIndex) ? "active" : "passive";
	out.print("<td class=" + tutorialSelectionClass + ">Tutorial # " + i + "</td>");
}
%>
</tr></tbody></table>	

<div id="divApproveSample">
<div class="step">STEP 1</div>
Does this code help to create a tutorial for your need? <input
	type="radio" id="radioApproval" name="radioApproval" value="Yes" /> Yes
<input type="radio" name="radioApproval" value="No" /> No</div>

<table id="tableCodeAndWordSelection">
<tbody>
<tr>
<td>
<div id="divCode">
<table>
<tbody>
<%
for (int index = 1; index <= currentTutorial.linesOfCode.size(); index++) {
	String className = (index%2==0) ? "even" : "odd";
	out.println("<tr><td>");
	out.print("<span class=\"lineNumber\">" + index + "</span>");
	out.println("</td><td class=\"" + className + "\">");
	out.print(currentTutorial.linesOfCode.get(index-1));
	out.println("</tr></td>");
}
%>
</tbody>
</table>
</div>
</td>

<td>
<div id="divWordInfo">
<div class="step">STEP 2</div>
Select the words to blank: <br />
<%
int index = 0;
for (WordInfo currentWordInfo : currentTutorial.wordInfoList) {
	out.println("<input type=\"checkbox\" name=\"cbxWordInfo\" value=\"" + index + "\">");
	out.print(currentWordInfo.wordToBeBlanked + " : ");
	out.println("<span class=\"lineNumber\">" + currentWordInfo.lineNumber + "</span>");
	out.println("</input><br />");
	index++;
}
%>
</div>
</td>
</tr>
</tbody>
</table>

<div id="divRating">
<div class="step">STEP 3</div>
Please rate the difficulty of this tutorial : <input
	name="difficultyLevel" type="radio" value="1" class="star" /> <input
	name="difficultyLevel" type="radio" value="2" class="star" /> <input
	name="difficultyLevel" type="radio" value="3" class="star" /> <input
	name="difficultyLevel" type="radio" value="4" class="star" /> <input
	name="difficultyLevel" type="radio" value="5" class="star" /></div>

<input type="submit" name="btnSubmit" id="btnSubmit"
	value="Next Tutorial >>" /></form>
	
</div>
</body>
</html>