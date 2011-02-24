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
	align=center;
}

#tableCenter {
	width:795px;
	border: 0px;
	margin:0;
}

#divBody {
	width:795px;
	border:1px;
	border-color:#333;
}

#divBanner {
	width: 790px;
	background-color: #FFF;
	background-image:url(images/bannerbg.jpg);
	background-repeat:repeat-x;
	margin:0 1;
	position: relative;
	text-align:left;
	padding-left:5px;
}

#divSpacerWhite{
	height: 10px;
	background-color:#FFFFFF;
}

#divSpacerGrey{
	height: 10px;
	background-color:#F4F4F4;
}

#divMain {
	width: 790px;
	position: relative; 
	margin: 0;
	background-color: #f4f4f4;
	text-align:left;
}

#tableMain {
	width:795px;
	border:0;
	padding:0;
	border-spacing:0;
}

#tableMain tr{
	height:1;
	background-color:#333333;
}

.myClass:hover td {background-color: orange; color: black; cursor:pointer; }

#subTable {
	width:100%;
	border:0;
	padding:0;
	border-spacing:0;
}


.tdDescription {
	width: 45%;
	vertical-align:top;
	background-color: beige;
}

.tdMeta {
	width: 25%;
	color: gray;
	font-size: 0.9em;
	background-color: beige;
	border-collapse:collapse;
}

.tdTutorialName {
	width: 30%;
	vertical-align:top;
	background-color: beige;
	padding-left:10px;
	border-right:1px;
	border-right-color:#F4F4F4;
}
</style>
<script type="text/javascript">
	function gotoURL(URL) {
		window.location = URL;
	}
</script>

</head>
<body>
<%
ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(null);
session.setAttribute("tutorialInfoList", tutorialInfoList);
int STUDENT_ID;
if (session.getAttribute("studentId") == null || session.getAttribute("studentId").toString().equals("")) {
	STUDENT_ID = Integer.parseInt(request.getParameter("studentId"));	
}
else{
	STUDENT_ID = Integer.parseInt(session.getAttribute("studentId").toString());
}
session.setAttribute("studentId", STUDENT_ID);
DeliverableLauncher launcher = new DeliverableLauncher();
session.setAttribute("deliverableLauncher", launcher);
%>
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" colspan="3" bgcolor="#333333"></td>
	</tr>
	<tr>
  		<td width="1" bgcolor="#333333"></td>
  		<td>
			<div id="divBody">
                <div id="divSpacerWhite"></div>
                <div id="divBanner"><img src="images/logo1.jpg" width="200" height="60" border="0" /></div>
                <div id="divSpacerGrey"></div>
                <div id="divMain">
                <form id="formLaunch">
                <table cellspacing="0" id="tableMain">
                	<tbody>
                        <tr><td colspan="3"></td></tr>
                        <%
                        for (TutorialInfo tutorialInfo : tutorialInfoList) {	
                            out.println("<tr class=\"myClass\" onclick=\"gotoURL('studentMainTest.jsp?start=1&id=" + tutorialInfo.getTutorialId() + "');\">");
                            out.println("<td class=\"tdTutorialName\">");
                            out.println(tutorialInfo.getTutorialName());
                            out.println("</td>");
                            out.println("<td class=\"tdMeta\">");
                            out.println("<table cellspacing=\"0\" id=\"subTable\">");
                            out.println("<tr class=\"myClass\">");
                            out.print("<td class=\"tdMeta\">Created by: ");
                            out.print(tutorialInfo.getCreatedBy());
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.print("<td class=\"tdMeta\">Date:");
                            out.print(tutorialInfo.getCreationDate().toString());
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr>");
                            out.print("<td class=\"tdMeta\">Downloads: ");
                            out.print(tutorialInfo.getTimesAccessed());
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("</table></td>");
                            out.print("<td class=\"tdDescription\">Description: ");
                            String description = tutorialInfo.getTutorialDescription();
                            out.print((description.length() > 30) ? description.substring(0, 30) + "..." : description);
                            out.println("</td>");
                            out.println("</tr>");
                            out.println("<tr><td colspan=\"3\"></td></tr>");
        
                        }
                        %>
                	</tbody>
                </table>
                </form>
                </div>
			</div>
		</td>
    	<td width="1" bgcolor="#333333"></td>
    </tr>
	<tr>
  		<td height="1" colspan="3" bgcolor="#333333"></td>
  	</tr>
</table>
</body>
</html>