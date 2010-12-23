<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="org.apache.jasper.util.Entry"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, java.util.HashMap, java.util.ArrayList, itjava.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Begin Learning..</title>
<style>
#divMainContent {
	width: 800px;
	margin:0 auto;
	position: relative;
}

#tableMeta {
	width: 800px;
}
</style>

<script type="text/javascript">
var launchCounts = 0;
function launchNext(folderName, deliverableName) {
	if (launchCounts == 0) {
		launchCounts++;
		window.open("delivery/" + folderName + "/" + deliverableName + ".jnlp");
		document.getElementById("btnLaunch").value = "Next >>";
		return false;
	}
	else {
		launchCounts = 0;
		return true;
	}
}
</script>

</head>

<body>
<%
HashMap<String, String> whereClause = new HashMap<String, String>();
whereClause.put("tutorialId", request.getParameter("id"));
ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(whereClause);
String deliverableName = request.getParameter("deliName");
%>
<form id="formMainTest" method="post" action="DeliverableSelectionServlet">
<div id="divMainContent">
<table border="1" id="tableMeta"><tbody>
<%
TutorialInfo tutorialInfo = new TutorialInfo();
if (tutorialInfoList.size() != 1) {
	out.println("Error seeking Tutorial Information..");
}
else {
	tutorialInfo = tutorialInfoList.get(0);
	int timesAccessed = tutorialInfo.getTimesAccessed() + 1;
	TutorialInfoStore.UpdateInfo(Integer.parseInt(request.getParameter("id")), 
			new KeyValue<String, String>("timesAccessed", Integer.toString(timesAccessed)));
	out.println("<tr>");
	out.print("<td colspan=\"3\" class=\"tdTutorialName\">");
	out.print(tutorialInfo.getTutorialName());
	out.println("</td><tr>");
	
	out.print("<td class=\"tdMeta\">Created by : ");
	out.print(tutorialInfo.getCreatedBy());
	out.println("</td>");
	out.print("<td class=\"tdMeta\">Date : ");
	out.print(tutorialInfo.getCreationDate().toString());
	out.println("</td>");
	out.print("<td class=\"tdMeta\">Downloads : ");
	out.print(tutorialInfo.getTimesAccessed());
	out.println("</td></tr>");
	
	out.print("<tr><td colspan=\"3\" class=\"tdDescription\">Description : ");
	out.print(tutorialInfo.getTutorialDescription());
	out.println("</td></tr>");
	
}
String folderName = tutorialInfo.getFolderName();
String buttonLabel = (deliverableName.equals("0")) ? "Begin Lesson" : "Save & Next" ;
KeyValue<Integer, String> deliveryKeyValue = DeliverableLauncher.GetFirstDeliverableName(tutorialInfo.getTutorialId());
deliverableName = (deliverableName.equals("0")) ? deliveryKeyValue.getValue() : deliverableName;
String disabledLaunch = "";
if (deliverableName == null) {
	disabledLaunch = "disabled";
}
else {
	session.setAttribute("deliverableName", deliverableName);
	session.setAttribute("deliverableId", deliveryKeyValue.getKey());
	session.setAttribute("tutorialInfoId", tutorialInfo.getTutorialId());
	session.setAttribute("studentId", 99);
}
%>
</tbody></table>
<div id="divNavigate">
<input type="submit" id="btnLaunch" name="btnLaunch" value="<%= buttonLabel%>" 
	onclick="return launchNext('<%= folderName%>', '<%= deliverableName%>');"
	<%= disabledLaunch%>/>
</div>

</div>
</form>
</body>
</html>