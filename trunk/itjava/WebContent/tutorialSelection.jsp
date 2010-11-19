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
<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script type="text/javascript">
$(function () {  
    $('#divApproveSample input:radio').click(function () {  
        var step2 = $('#divWordInfo');  
        var step3 = $('#divRating');
        if (this.value == 'Yes')  {
            step2.show('slow');  
        	step3.show('slow');
        }
        else {  
            step2.hide('slow');  
            step3.hide('slow'); 
        }  
    });  
});   
</script>
</head>
<body>

<form id="tutorialSelectionForm" method="post"
	action="TutorialSelectionServlet">
<div id="divMain">
<%
ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>)session.getAttribute("tutorialList");
int currentIndex = (Integer)session.getAttribute("currentIndex");
Tutorial currentTutorial = tutorialList.get(currentIndex);
%>

<table><tbody><tr>
<%
for (int i = 0; i < tutorialList.size(); i++) {
	String tutorialSelectionClass = (i==currentIndex) ? "active" : "passive";
	out.print("<td class=" + tutorialSelectionClass + ">Example # " + (i+1) + "</td>");
}
%>
</tr></tbody></table>	

<div id="divStepsContainer">
<div id="divApproveSample" class="stepBox">
<div class="step">STEP 1</div>
<fieldset>
<label>What would you like to do with the following example?</label><br />
<input type="radio" name="radioApproval" value="Yes" validate="required:true" /> Quiz
<input type="radio" name="radioApproval" value="No" /> Example
<input type="radio" name="radioApproval" value="No" /> Discard
</fieldset>
</div>

<div id="divWordInfo" class="stepBox">
<div class="step">STEP 2</div>
<fieldset>
<label>Select the words to blank:</label><br />
<%
int index = 0;
for (WordInfo currentWordInfo : currentTutorial.getWordInfoList()) {
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
<div id="divRating" class="stepBox">
<div class="step">STEP 3</div>
<fieldset>
	<label>Please rate the difficulty of this tutorial</label><br />
	<input name="difficultyLevel" type="radio" value="1" class="star" /> 
	<input name="difficultyLevel" type="radio" value="2" class="star" />
	<input name="difficultyLevel" type="radio" value="3" class="star" /> 
	<input name="difficultyLevel" type="radio" value="4" class="star" /> 
	<input name="difficultyLevel" type="radio" value="5" class="star" />
</fieldset>
</div>
</div>

<div id="divCode">
<table>
<tbody>
<%
for (index = 1; index <= currentTutorial.getLinesOfCode().size(); index++) {
	String className = (index%2==0) ? "even" : "odd";
	out.println("<tr><td>");
	out.print("<span class=\"lineNumber\">" + index + "</span>");
	out.println("</td><td class=\"" + className + "\">");
	out.print(currentTutorial.getLinesOfCode().get(index-1));
	out.println("</tr></td>");
}
%>
</tbody>
</table>
</div>


<input type="submit" name="btnSubmit" id="btnSubmit"
	value="Next Tutorial >>" />

</div>
</form>
	

</body>
</html>