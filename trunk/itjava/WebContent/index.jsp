<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="http://experiments.eecs.oregonstate.edu:8080/favicon.ico" type="image/x-icon" />
<title>Create a jTutor</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script type="text/javascript" language="javascript" src="js/ratingsys.js"></script> 
<script type="text/javascript" language="javascript" src="js/main.js"></script> 
<script src="MD5.js"></script>
</head>
<body>
<table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3"><%@ include file="/modules/headers/header5.jsp" %></td>
  </tr>
  <tr>
  	<td width="1" bgcolor="#122222"></td>
    <td width="1022">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td width="5"></td>
        <td valign="top" style="width:250px;">
        	<%@ include file="/modules/commonterms2.jsp" %>
        </td>
        <td valign="top" style="width: 522px;" align="center">
        	<%@ include file="/modules/headers/searchbox3.jsp" %>
        	<%@ include file="/modules/howtouse.jsp" %>
        </td>
        <td width="250" valign="top">
        	<%
        		if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && session.getAttribute("userLevel").equals("student") && !session.getAttribute("userID").equals("0")){ 
        			%><%@ include file="/modules/students/studentinfobox.jsp" %><%
        		}else if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && session.getAttribute("userLevel").equals("teacher") && !session.getAttribute("userID").equals("0")){ 
        			%><%@ include file="/modules/teachers/teacherinfobox.jsp" %><%
        		}else if(session.getAttribute("userLevel") != null && session.getAttribute("userLevel").equals("teacher")){ 
        			%><%@ include file="/modules/teachers/teacherloginbox.jsp" %><%
        		}else{ 
        			%><%@ include file="/modules/students/studentloginbox.jsp" %><%
        		}
        	%>
        	<%@ include file="/modules/mostpopular.jsp" %>
        </td>
        <td width="5"></td>
      </tr>
      <tr>
        <td height="10" colspan="2"></td>
        <td></td>
        </tr>
    </table></td>
    <td width="1" bgcolor="#122222"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#122222" colspan="3"></td>
  </tr>
</table> 
<% 
if(session.getAttribute("userName") == null){
	String timestamp =  new Double(System.currentTimeMillis()/1000).toString();
	byte[] defaultBytes = timestamp.getBytes();
	MessageDigest algorithm = MessageDigest.getInstance("MD5");
	algorithm.reset();
	algorithm.update(defaultBytes);
	byte messageDigest[] = algorithm.digest();
			
	StringBuffer hexString = new StringBuffer();
	for (int i=0;i<messageDigest.length;i++) {
		hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	}
	messageDigest.toString();
	String tempuser=hexString+"";
	session.setAttribute("userID", "0");
	session.setAttribute("userName", tempuser);
	session.setAttribute("userLevel", "unknown");
} 
%>
<%
HashMap<String, String> filterindex = new HashMap<String, String>();
filterindex.put("createdBy", (String) session.getAttribute("userName"));
filterindex.put("userLevel", (String) session.getAttribute("userLevel"));
            
ArrayList<TutorialInfo> tutorialInfoListindex = TutorialInfoStore.SelectInfo(filterindex);
session.setAttribute("tutorialInfoList", tutorialInfoListindex);
session.setAttribute("studentId", session.getAttribute("userID"));
DeliverableLauncher launcherindex = new DeliverableLauncher();
session.setAttribute("deliverableLauncher", launcherindex);
%>
</body>
</html>