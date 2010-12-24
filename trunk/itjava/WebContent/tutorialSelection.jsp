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
    toggleHints();
    
    $('#divWordInfo input:checkbox').click(toggleHints);
	
    $('#divApproveSample input:radio').click(function () {
        var step2 = $('#divWordInfo');  
        var step3 = $('#divRating');
        if (this.value == 'Quiz')  {
            step2.show('slow');  
        	step3.show('slow');
        }
        else {  
            step2.hide('slow');  
            step3.hide('slow'); 
        }  
    });
    
    function toggleHints(){
    	$('#divHint').hide();
    	$('#divHint div').hide();
    	var allVals = [];
    	$('#divWordInfo input:checkbox:checked').each(function () {
    		allVals.push(this.value);
    	}); 
		if (allVals.length > 0) {
			$('#divHint').show('fast');
		}
		for (var i =0 ; i < allVals.length; i++ ) {
			var divHintId = "divHint" + allVals[i];
			document.getElementById(divHintId).style.display = "block";
		} 
    }
});   

function isReady(form) {
	if (document.tutorialSelectionForm.radioApproval[1].checked || 
			document.tutorialSelectionForm.radioApproval[2].checked) {
		return true;
	}
	else if (document.tutorialSelectionForm.radioApproval[0].checked) {
		var wordInfoFlag = false;
		var difficultyFlag = false;
		for (var i = 0; i < document.tutorialSelectionForm.cbxWordInfo.length; i++) {
			if (document.tutorialSelectionForm.cbxWordInfo[i].checked) {
				wordInfoFlag = true;
				break;
			}
		}
		for (i =0; i < 5; i++) {
			if (document.tutorialSelectionForm.difficultyLevel[i].checked) {
				difficultyFlag = true;
				break;
			}
		}
		if (difficultyFlag && wordInfoFlag) {
			return true;
		}
	}
	alert("Please finish all the required steps..");
	return false;
}
</script>

</head>
<body>

<!--<form id="tutorialSelectionForm" method="post" action="TutorialSelectionServlet">-->
<form id="tutorialSelectionForm" name="tutorialSelectionForm" method="post" 
	onsubmit="return isReady(this)" action="SaveSelectionsServlet">
<div id="divMain">
<%
int currentIndex = Integer.parseInt(request.getParameter("index"));
session.setAttribute("currentIndex", currentIndex);

ArrayList<String> approvalList = (ArrayList<String>) session.getAttribute("approvalList");
ArrayList<List<String>> wordsList = (ArrayList<List<String>>) session.getAttribute("wordsList");
ArrayList<Integer> difficultyList = (ArrayList<Integer>) session.getAttribute("difficultyList");
ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>)session.getAttribute("tutorialList");
ArrayList<HashMap<String, ArrayList<String>>> hintsMapList = (ArrayList<HashMap<String, ArrayList<String>>>)session.getAttribute("hintsMapList");

Tutorial currentTutorial = tutorialList.get(currentIndex);
%>

<table><tbody><tr>
<%
for (int i = 0; i < tutorialList.size(); i++) {
	String tutorialSelectionClass = (i==currentIndex) ? "active" : "passive";
	out.print("<td class=" + tutorialSelectionClass);
	out.print(" onclick=\"window.location.href='tutorialSelection.jsp?index=");
	out.print(i);
	out.print("'\"");
	out.print("><div>Snippet# " + (i+1));
	out.print("</div><div><image src=\"images/tick.png\" class=\"");
	if (approvalList.get(i) != null) {
		out.print("showimage");
	}
	else {
		out.print("noimage");
	}
	
	out.println("\"/></div></td>");
}
%>
</tr></tbody></table>	

<div id="divStepsContainer">
<div id="divApproveSample" class="stepBox">
<div class="step">STEP 1</div>
<fieldset>
<label>What would you like to do with the following Snippet?</label><br />

<%
String approvalSelected = approvalList.get(currentIndex);
String[] approvalOptions = {"Quiz", "Example", "Discard"};
for (int approvalIndex = 0; approvalIndex < 3; approvalIndex++) {
	out.print("<input type=\"radio\" name=\"radioApproval\" value=\""); 
	out.print(approvalOptions[approvalIndex]);
	out.print("\"");
	if (approvalSelected != null) {
		if (approvalSelected.equals(approvalOptions[approvalIndex])) {
			out.print(" checked=\"checked\"");
		}
	}
	out.print("/>");
	out.println(approvalOptions[approvalIndex]);
}
%>
</fieldset>
</div>

<div id="divWordInfo" class="stepBox">
<div class="step">STEP 2</div>
<fieldset>
<label>Select the words to blank:</label><br />
<%
int index = 0;
List<String> selectedWords = wordsList.get(currentIndex);
for (WordInfo currentWordInfo : currentTutorial.getWordInfoList()) {
	out.print("<input type=\"checkbox\" name=\"cbxWordInfo\" value=\"" + index + "\"");
	if(selectedWords != null) {
		if (selectedWords.contains(Integer.toString(index))) {
			out.print(" checked=\"checked\"");
		}
	}
	out.print(">");
	out.print(currentWordInfo.wordToBeBlanked + " : ");
	out.print("<span class=\"lineNumber\">" + currentWordInfo.lineNumber + "</span>");
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
	<label>Rate the difficulty of this tutorial:</label><br />
	<%
	Integer difficultySelected = difficultyList.get(currentIndex);
		for (int difficultyIndex = 1; difficultyIndex <=5; difficultyIndex++) {
			out.print("<input name=\"difficultyLevel\" type=\"radio\" value=\"" + difficultyIndex + "\" class=\"star\"");
			if (difficultySelected != null) {
				if (difficultySelected == difficultyIndex) {
					out.print(" checked=\"checked\"");
				}
			}
			out.println("/>");
		}
	%>
</fieldset>
</div>
</div>

<div id="divSubmit">
<input type="submit" name="btnSubmit" id="btnSubmitPrev"
<%
if (currentIndex == 0) {
	out.print(" disabled=\"disabled\" ");
}
%>
	value="<< Previous Snippet" />

<input type="submit" name="btnSubmit" id="btnSubmitNext"
	value="Next Snippet >>" />
</div>

<div id="divCode">
<table>
<tr><td>
<div id="divCodeTable">
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
<tr><td></td><td class="copyright">For copyright of this snippet visit: <% out.println(currentTutorial.sourceUrl.replaceFirst("^\\d", "")); %></td></tr>
</tbody>
</table>
</div>
</td>
<td id="tdHintCell">
<div class="step">STEP 4</div>
<div id="divHint"> Hints Corresponding to : 
<% 
HashMap<String, ArrayList<String>> hintsMap = hintsMapList.get(currentIndex);
int currWordInfoIndex = 0;
for (WordInfo currentWordInfo : currentTutorial.getWordInfoList()) {
	String wordToBeBlanked = currentWordInfo.wordToBeBlanked;
	ArrayList<String> currHintsList = null;
	String hintValue = "";
	if (hintsMap != null) {
		currHintsList = hintsMap.get(wordToBeBlanked);
	}
	out.println("<div class=\"divInvisibleHint\" id=\"divHint" + currWordInfoIndex + "\"> ");
	out.println(wordToBeBlanked);
	for (int hintIndex = 1; hintIndex <= 2; hintIndex++) {
		if (currHintsList != null) {
			hintValue = currHintsList.get(hintIndex);
		}
		out.println("<br /><input type=\"text\" " +
				"name=\"txtHint_" + currWordInfoIndex + "_" + hintIndex + "\" " + 
				"placeholder=\"Hint " + hintIndex + "\" " + 
				"value=\"" + hintValue + "\" " +
				"size=\"30\" " +
				" />");		
	}
	out.println("</div>");
	currWordInfoIndex++;
}
%>
</div>
</td>
</tr>
</table>
</div>


</div>
</form>
	

</body>
</html>