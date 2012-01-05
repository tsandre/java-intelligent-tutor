<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="http://experiments.eecs.oregonstate.edu:8080/favicon.ico" type="image/x-icon" />
<title>Tutor Viewer</title>
<link href="css/prettify.css" type="text/css" rel="stylesheet" /><script type="text/javascript" src="js/prettify.js"></script>
<script type="text/javascript" language="javascript">
var currenthint = 0;

function checkanswer(ansnum){
	var givenans = document.getElementById("answer_"+ansnum).value;
	if(givenans != Edges[ansnum]){
		
		document.getElementById("answer_"+ansnum).style.borderColor="rgb(144,0,0)";
		if(questionscore[ansnum] >= 20){
			questionscore[ansnum] = questionscore[ansnum] - 20;
		}else{
			questionscore[ansnum] = 0;
		}
	}else{
		document.getElementById("answer_"+ansnum).style.borderColor="rgb(9,144,0)";
	}
}
function checkanswers(tutorID, j){
	var totalanswers = document.getElementById("totalanswers").value;
	var totalquestions = Edges.length;
	var totalcorrect = 0;
	for(var i=0; i<totalquestions; i++){
		var givenans = document.getElementById("answer_"+i).value;
		if(givenans != Edges[i]){
			document.getElementById("answer_"+i).style.borderColor="rgb(144,0,0)";
			if(questionscore[i] >= 20){
				questionscore[i] = questionscore[ansnum] - 20;
			}else{
				questionscore[i] = 0;
			}
		}else{
			document.getElementById("answer_"+i).style.borderColor="rgb(9,144,0)";
			totalcorrect++;
		}
	}
	if(totalcorrect == totalquestions){
		alert("You completed this quiz successfully!");
		getnext(tutorID, j);
	}else{
		alert("You missed some! Please complete the quiz to continue.");
	}
}

function setcurrenthint(hintnum){
	document.getElementById("currentquestionscore").innerHTML = "Question " + (parseInt(hintnum)+1) + " Remaining Points Possible: " + questionscore[hintnum] + "/100";
	for(var i=0; i<currhint.length; i++){
		if(i==hintnum){
			if(document.getElementById("answer_"+i).style.borderColor=="rgb(9, 144, 0)"){
				//leave as green
			}else{
				document.getElementById("answer_"+i).style.borderColor="rgb(0,102,153)";
			}
		}else{
			if(document.getElementById("answer_"+i).style.borderColor=="rgb(0, 102, 153)"){
				document.getElementById("answer_"+i).style.borderColor="rgb(153, 153, 153)";
			}
		}
	}
	currenthint = hintnum;
}

function getnext(tutorID, j){
	if(document.getElementById("pagetype").value == "Example"){
		window.location = "tutorViewer.jsp?tutorID=" + tutorID + "&pagenum=Quiz-" + document.getElementById("nextQuiz").value + "&nextExample=" + document.getElementById("nextExample").value + "&nextQuiz=" + document.getElementById("nextQuiz").value;
	}else if(userscore() > 50){
		window.location = "tutorViewer.jsp?tutorID=" + tutorID + "&pagenum=Quiz-" + document.getElementById("nextQuiz").value + "&nextExample=" + document.getElementById("nextExample").value + "&nextQuiz=" + document.getElementById("nextQuiz").value;
	}else{
		window.location = "tutorViewer.jsp?tutorID=" + tutorID + "&pagenum=Example-" + document.getElementById("nextExample").value + "&nextExample=" + document.getElementById("nextExample").value + "&nextQuiz=" + document.getElementById("nextQuiz").value;
	}
}

function userscore(){
	var totalscore = 0;
	for(var i=0; i<questionscore.length; i++){
		totalscore += parseInt(questionscore[i]);
	}
	var averagescore = parseInt(totalscore)/parseInt(questionscore.length);
	return roundNumber(averagescore, 2);
}

function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function showHint(){
	var currhintnum = 0;
	for(var i=0; i<currenthint; i++){
		currhintnum += parseInt(totalhintsleft[i]) + parseInt(currhint[i]);
	}
	currhintnum += parseInt(currhint[currenthint]);
	document.getElementById("hintspanel").innerHTML = Hints[currhintnum];
	if(totalhintsleft[currenthint] == 1){
		questionscore[currenthint] = 0;
	}else{
		if(questionscore[currenthint] >= 30){
			questionscore[currenthint] = questionscore[currenthint] - 30;
		}else{
			questionscore[currenthint] = 0;
		}
	}
	if(totalhintsleft[currenthint]>1){
		currhint[currenthint]++;
		totalhintsleft[currenthint]--;
	}
	document.getElementById("currentquestionscore").innerHTML = "Question " + (parseInt(currenthint)+1) + " Remaining Points Possible: " + questionscore[currenthint] + "/100";
}

$(document).ready(function() {
    $('a.tTip').tinyTips('title');
});

function onloadfunc(){
	document.getElementById("answer_0").style.borderColor="rgb(0,102,153)";
	document.getElementById("answer_0").focus();
}

function focusdonebutton(){
	document.getElementById("currentquestionscore").innerHTML = "Current Score if All Correct: " + userscore() + "/100";
	if(document.getElementById("answer_"+(parseInt(totalhintsleft.length)-1)).style.borderColor=="rgb(0, 102, 153)"){
		document.getElementById("answer_"+(parseInt(totalhintsleft.length)-1)).style.borderColor="rgb(153, 153, 153)";
	}
}
</script>

</head>
<body onload="prettyPrint(); onloadfunc()">
<%@ page import="itjava.db.*, itjava.util.*, java.sql.*"%>
<%
	String tutorID = request.getParameter("tutorID").toString();
	Connection conn = null;
	PreparedStatement ucpst = null;
	ResultSet rs = null;
	PreparedStatement ucpst2 = null;
	ResultSet rs2 = null;
	conn = DBConnection.GetConnection();		
	String getThisTutor = "SELECT DeliverableInfo.* FROM DeliverableInfo WHERE DeliverableInfo.tutorialInfoId = ? ORDER BY difficultyLevel ASC";
	ucpst = conn.prepareStatement(getThisTutor);
	ucpst.setString(1, tutorID);
	rs = ucpst.executeQuery();
	String getThisTutor2 = "SELECT TutorialInfo.* FROM TutorialInfo WHERE TutorialInfo.tutorialInfoId = ?";
	ucpst2 = conn.prepareStatement(getThisTutor2);
	ucpst2.setString(1, tutorID);
	rs2 = ucpst2.executeQuery();
	rs2.next();
	String TutorName = rs2.getString("tutorialName");
	String TutorDesc = rs2.getString("tutorialDescription");
	String APIType = rs2.getString("folderName");
        String pageDescription = "";
	int i=0;
	int pagenum = 0;
	String thispagetype;
	int thispagenum;
	int tutorialComplete = 0;
	int nextExample = Integer.parseInt(request.getParameter("nextExample").toString());
	int nextQuiz = Integer.parseInt(request.getParameter("nextQuiz").toString());
	if(request.getParameter("pagenum") != null){
		String[] temp = request.getParameter("pagenum").toString().split("-");
		thispagetype = temp[0];
		thispagenum = Integer.parseInt(temp[1]);
	}else{
		if(rs2.getInt("numExamples") > 0){
			thispagetype = "Example";
			thispagenum = 1;
		}else{
			thispagetype = "Quiz";
			thispagenum = 1;
		}
	}
	
	if(thispagetype.equals("Quiz")){
		if(rs2.getInt("numQuizes") < thispagenum){
			tutorialComplete = 1;
		}
	}else{
		if(rs2.getInt("numExamples") < thispagenum){
			if(rs2.getInt("numQuizes") < nextQuiz){
				tutorialComplete = 1;	
			}else{
				thispagenum = nextQuiz;
				thispagetype = "Quiz";
			}
		}
	}
	out.println("<table><tr><td><span style=\"font-size:18px; font-weight:bold;\">" + TutorName + "</span></td></tr><tr><td>" + TutorDesc + "</td></tr><tr><td>API: " + APIType + "</td></tr><tr height=\"15\"><td></td></tr><tr><td>");
	String pagetype = "";
	int examplecount = 0;
	int quizcount = 0;
	while(rs.next()){
		if(rs.getString("deliverableType").equals(thispagetype) ){
                        pageDescription = rs.getString("tutorialDescription");
			if(rs.getString("deliverableType").equals("Quiz")){
				quizcount++;
				if(quizcount == thispagenum){
					pagetype = "Quiz";
					out.println(rs.getString("tutorialHTMLCode"));
					out.println("<input type=\"button\" onClick=\"checkanswers(" + tutorID + ", " + i + ");\" onfocus=\"focusdonebutton()\" value=\"DONE\" />");
				}
			}else{
				examplecount++;
				if(examplecount == thispagenum){
					pagetype = "Example";
					out.println("<code class=\"prettyprint\">");
					out.println(rs.getString("tutorialHTMLCode"));
					out.println("<input type=\"button\" onClick=\"getnext(" + tutorID + ", " + i + ");\" value=\"DONE\" />");
					out.println("</code>");
				}
			}
		}
		i++;
	}
	out.print("</td><td valign=\"top\" style=\"padding-top:35px;\"><div style=\"border:solid; border-width:1px; border-color:#333; width:250px; padding: 4px 4px 4px 4px;\" id=\"hintspanel\">");
	if(tutorialComplete==1){
		out.println("<p align=\"center\">You have successfully completed this Intelligent Tutor! <br/><br/><a href=\"http://localhost:8080/itjava/\" title=\"Tutor Search\" class=\"navmain\">Return Home</a></p></div></td></tr></table>");
	}else{
           if(pageDescription.length() > 0){
               out.println(pageDescription + "<br /><br />");
           }
		if(pagetype.equals("Quiz")){
			out.println("If you need help, get a hint by clicking the \"Get Hint\" button. The hint will be related to the textbox that the cursor is in when you click the \"Get Hint\" button.</div><p align=\"center\"><input type=\"submit\" name=\"button\" id=\"button\" onclick=\"showHint();\" value=\"Get Hint\" /></p><div id=\"currentquestionscore\" style=\"border:solid; border-width:1px; border-color:#333; width:250px; height:100px; padding: 4px 4px 4px 4px; display:none;\"></div></td></tr></table>");
		}else{
			out.println("This is an example. Please read it over carefully to prepare for a quiz on the material. Once you feel you understand this code snippet, click the done button to continue on.</div></td></tr></table>");
		}
		out.println("<input type=\"hidden\" name=\"totalanswers\" id=\"totalanswers\" value=\"" + i + "\" />");
		if(pagetype == "Example"){
			out.println("<input type=\"hidden\" name=\"nextExample\" id=\"nextExample\" value=\"" + (nextExample+1) + "\" />");
			out.println("<input type=\"hidden\" name=\"nextQuiz\" id=\"nextQuiz\" value=\"" + nextQuiz + "\" />");
		}else{
			out.println("<input type=\"hidden\" name=\"nextExample\" id=\"nextExample\" value=\"" + nextExample + "\" />");
			out.println("<input type=\"hidden\" name=\"nextQuiz\" id=\"nextQuiz\" value=\"" + (nextQuiz+1) + "\" />");
		}
            
	}
	
	out.println("<input type=\"hidden\" name=\"pagetype\" id=\"pagetype\" value=\"" + pagetype + "\" />");
	
	conn.close();
%>

</body>
</html>