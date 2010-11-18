<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, itjava.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Select tutorials here..</title>
<link type="text/css" rel="stylesheet" href="css/tutorialSelection.css"/>
<script src="js/jquery.js" type="text/javascript"></script> 
<script src="js/jquery.metadata.js" type="text/javascript"></script> 
<script src="js/jquery.validate.js" type="text/javascript"></script> 

<script type="text/javascript"> 
$.metadata.setType("attr", "validate");

$(document).ready(function() {
	$("#tutorialSelectionForm").validate({
		rules: {
			radioApproval: "required",
			cbxWordInfo: {
				required: "#radioApproval:checked"
			},
			difficultyLevel: {
				required: "#radioApproval:checked"
			}
		}
	});
});
</script> 

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
<legend>Does this code help to create a tutorial for your need? </legend>
<input type="radio" name="radioApproval" value="Yes" validate="required:true" /> Yes
<input type="radio" name="radioApproval" value="No" /> No
<label for="radioApproval" class="error">Required Field</label>
</div>
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
<fieldset>
<legend>Select the words to blank:</legend>
<%
int index = 0;
for (WordInfo currentWordInfo : currentTutorial.wordInfoList) {
	out.println("<input type=\"checkbox\" name=\"cbxWordInfo\" validate=\"required:true\" value=\"" + index + "\">");
	out.print(currentWordInfo.wordToBeBlanked + " : ");
	out.println("<span class=\"lineNumber\">" + currentWordInfo.lineNumber + "</span>");
	out.println("</input><br />");
	index++;
}
%>
<label for="cbxWordInfo" class="error">Required Field. If none are useful mark "NO" in STEP 1.</label>
</fieldset>
</div>
</td>
</tr>
</tbody>
</table>

<div id="divRating">
<div class="step">STEP 3</div>
<fieldset>
	Please rate the difficulty of this tutorial : 
	<input name="difficultyLevel" type="radio" value="1" class="star" validate="required:true" /> 
	<input name="difficultyLevel" type="radio" value="2" class="star" />
	<input name="difficultyLevel" type="radio" value="3" class="star" /> 
	<input name="difficultyLevel" type="radio" value="4" class="star" /> 
	<input name="difficultyLevel" type="radio" value="5" class="star" />
	<label for="difficultyLevel" class="error">Required Field.</label>	
</fieldset>
</div>
<input type="submit" name="btnSubmit" id="btnSubmit"
	value="Next Tutorial >>" /></form>
	
</div>
</body>
</html>