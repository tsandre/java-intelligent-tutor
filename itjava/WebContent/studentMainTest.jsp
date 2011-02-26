<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, java.util.HashMap, java.util.ArrayList, itjava.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Begin Learning..</title>
<style>
body {
	font-family: segoe ui, verdana;
	align=center;
}

#divBanner {
	background-image:url(images/bannerbg.jpg);
	background-repeat:repeat-x;
	text-align:left;
	padding-left:5px;
}

.tdBold {
	font-weight:bold;
	font-size:12px;
}

.tdRegular {
	font-size:12px;
}

.tdInstr {
	background-color:#F4F4F4;
	padding: 10px;
}

#divNavigate {
	width: 800px;
	text-align:left;
}
#divContainer {
	width: 800px;
	text-align: center;
}
#divInstructions {
	border-radius: 2px;
	display:none;
	font-size: 0.8em;
	width: 100%;
	-webkit-box-shadow: 0px 0px 5px 2px gray;
	box-shadow: 0px 0px 5px 2px gray;
	-moz-box-shadow: 0px 0px 5px 2px gray;
}
</style>
<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script type="text/javascript">
$(function () {
	$('#divInstructions').fadeIn(2000);
});
var launchCounts = 0;
function launchNext(folderName, deliverableName) {
	if (launchCounts == 0) {
		launchCounts++;
		window.open("delivery/" + folderName + "/" + deliverableName + ".jnlp");
		document.getElementById("btnLaunch").value = "Save & Next >>";
		return false;
	}
	else {
		launchCounts = 0;
		return true;
	}
}

function launchNow(folderName, deliverableName) {
	window.open("delivery/" + folderName + "/" + deliverableName + ".jnlp");
	launchCounts = 1;
}
</script>

</head>

<body>
<%

int tutorialInfoId = Integer.parseInt(request.getParameter("id"));

DeliverableLauncher deliverableLauncher = (DeliverableLauncher)session.getAttribute("deliverableLauncher");
if (deliverableLauncher == null) {
	System.err.println("Landed on studentMainTest.jsp from a wrong source..");
	response.sendRedirect("studentWelcome.jsp");
}
else {
	deliverableLauncher.setStudentId((Integer)session.getAttribute("studentId"));
	deliverableLauncher.setTutorialInfoId(tutorialInfoId);
}

HashMap<String, String> whereClause = new HashMap<String, String>();
whereClause.put("tutorialInfoId", Integer.toString(tutorialInfoId));
session.setAttribute("tutorialInfoId", tutorialInfoId);
ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(whereClause);

KeyValue<Integer, String> deliveryKeyValue = (KeyValue<Integer, String>)session.getAttribute("deliveryKeyValue");
int deliverableId = -1;
String deliverableName = null;
if (deliveryKeyValue == null  && request.getParameter("start").trim().equals("1")) {
	deliveryKeyValue = deliverableLauncher.GetFirstDeliverableName();
}
else if (deliveryKeyValue == null)
{
	response.sendRedirect("studentFinalPage.jsp");
}
deliverableId = deliveryKeyValue.getKey();
deliverableName = deliveryKeyValue.getValue();

String br = "<br />";
String star = br + "&nbsp;*&nbsp;&nbsp;";
StringBuilder instr = new StringBuilder("<b><i>Instructions:</i></b>");
String divColor = "#F4F4F4";
if (deliverableName.contains("1Example")) {
	instr.append(br).append("The description below explains the topic in brief. You can access this description any time.");
	instr.append(star).append("On clicking launch button at the bottom, you will be provided with an <b><i>example</i></b> in a pop-up.");
	instr.append(star).append("Please read the code snippet in the pop-up carefully. Pay special attention to the <b><i>italicized</i></b> terms.");
	instr.append(star).append("<b><i>IMPORTANT:</i></b> Click Done button on the pop-up. Then click the close button. This is necessary for our experiment.");
	divColor = "honeydew";
}
else if (deliverableName.contains("Example")) {
	instr.append(star).append("On clicking launch button at the bottom, you will be provided with a <b><i>Quiz</i></b> in a pop-up.");
	instr.append(star).append("Fill in the blanks.");
	instr.append(star).append("You can access hints corresponding to any blank by selecting that blank and clicking Help.");
	instr.append(star).append("Multiple hints can be accessed by clicking the help button repeatedly.");
	divColor = "mistyrose";
}
String instructionText = instr.toString();
%>
<form id="formMainTest" method="post" action="DeliverableSelectionServlet">
<div id="divMainContent">
<table border="0" cellpadding="0" cellspacing="0" id="tableMeta" align="center"><tbody>
<tr><td>
  <table id="tableMain" border="0" align="center" cellpadding="0"  cellspacing="0">
    <tbody>
      <%
		TutorialInfo tutorialInfo = new TutorialInfo();
		if (tutorialInfoList.size() != 1) {
			System.err.println("TutorialInfo table should have returned only 1 tuple..");
			out.println("Error seeking Tutorial Information..");
		}
		else {
			tutorialInfo = tutorialInfoList.get(0);
			int timesAccessed = tutorialInfo.getTimesAccessed() + 1;
			TutorialInfoStore.UpdateInfo(tutorialInfoId, 
					new KeyValue<String, String>("timesAccessed", Integer.toString(timesAccessed)));
      
      		out.println("<tr>");
        	out.println("<td height=\"1\" colspan=\"3\" bgcolor=\"#333333\"></td>");
      		out.println("</tr>");
      		out.println("<tr>");
        	out.println("<td width=\"1\" bgcolor=\"#333333\"></td>");
        	out.println("<td width=\"800\">");
          	out.println("<table width=\"800\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td colspan=\"3\" bgcolor=\"#FEFEFE\" class=\"tdRegular\" height=\"10\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=\"3\" id=\"divBanner\"><img src=\"images/logo1.jpg\" width=\"200\" height=\"60\" border=\"0\" /></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdBold\" style=\"padding-left:10px;\" height=\"10\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height=\"2\" colspan=\"3\" bgcolor=\"#333333\" class=\"tdBold\" style=\"padding-left:10px;\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height=\"10\" colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdBold\" style=\"padding-left:10px;\"></td>");
            out.println("</tr>");
            out.println("<tr><td colspan=\"3\" class=\"tdInstr\"><div id=\"divInstructions\" style=\"background-color:"+ divColor +"\">" + instructionText + "</div></td></tr>");
            out.println("<tr>");
            out.print("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdBold\" style=\"padding-left:10px;\">");
			out.print(tutorialInfo.getTutorialName());
			out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.print("<td bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\" >Created by : ");
			out.print(tutorialInfo.getCreatedBy());
			out.println("</td>");
            out.print("<td bgcolor=\"#F4F4F4\" class=\"tdRegular\">Date : ");
			out.print(tutorialInfo.getCreationDate().toString());
			out.println("</td>");
            out.print("<td bgcolor=\"#F4F4F4\" class=\"tdRegular\">Downloads : ");
			out.print(tutorialInfo.getTimesAccessed());
			out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.print("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\">Description : ");
			out.print(tutorialInfo.getTutorialDescription());
			out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height=\"5\" colspan=\"3\" bgcolor=\"#F4F4F4\" style=\"padding-left:10px;\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height=\"1\" colspan=\"3\" style=\"padding-left:10px;\" bgcolor=\"#F4F4F4\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height=\"5\" colspan=\"3\" bgcolor=\"#F4F4F4\" style=\"padding-left:10px;\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\">");
           // out.println("<input type=\"submit\" id=\"btnLaunch\" name=\"btnLaunch\" value=\"Begin Lesson\" class=\"tdRegular\" onclick=\"return launchNext('ProcessBuilderinjava', 'Example1');\" />");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\" height=\"10px\"></td>");
            out.println("</tr>");
            out.println("</tbody>");
          	out.println("</table>");
        	out.println("</td>");
        	out.println("<td width=\"1\" bgcolor=\"#333333\"></td>");
      		out.println("</tr>");
      		out.println("<tr>");
        	out.println("<td colspan=\"3\" bgcolor=\"#333333\" height=\"1\"></td>");
      		out.println("</tr>");
	}
	String folderName = tutorialInfo.getFolderName();
	String buttonLabel = "Launch";

	String disabledLaunch = "";
	if (deliverableName == null) {
		disabledLaunch = "disabled";
	}
	else {
		session.setAttribute("deliveryKeyValue", deliveryKeyValue);
		session.setAttribute("tutorialInfoId", tutorialInfoId);
		session.setAttribute("studentId", 99);
		session.setAttribute("deliverableLauncher", deliverableLauncher);
	}
	%>
</tbody></table></td></tr></tbody></table>

<table id="divContainer" align="center"><tbody><tr><td>
<div id="divNavigate">
<input type="submit" id="btnLaunch" name="btnLaunch" value="<%= buttonLabel%>" 
	onclick="return launchNext('<%= folderName%>', '<%= deliverableName%>');"
	<%= disabledLaunch%>/>
</div>

</td></tr></tbody></table>
</div>
</form>
</body>
</html>