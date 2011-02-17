<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, itjava.data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Teacher Page</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="js/ratingsys.js"></script> 
<script type="text/javascript" language="javascript" src="js/main.js"></script> 
</head>

<body>
<table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><%@ include file="/modules/headers/header2.jsp" %></td>
  </tr>
  <tr>
    <td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" rowspan="3" bgcolor="#122222"></td>
        <td rowspan="2" valign="top" style="padding:0px 5px 5px 5px; color: #333; font-size: 12px; font-family: Arial, Helvetica, sans-serif;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10"></td>
          </tr>
          <tr><td>
          <%
          	if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && request.getParameter("page") != null && session.getAttribute("userLevel").equals("teacher") && !session.getAttribute("userID").equals("0") && request.getParameter("page").equals("accountinfo")){ 
          		%><jsp:include page="/modules/teachers/teacheraccountinfo.jsp" /><%
          	}else if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && request.getParameter("page") != null && session.getAttribute("userLevel").equals("teacher") && !session.getAttribute("userID").equals("0") && request.getParameter("page").equals("savedtutors")){ 
          		%><jsp:include page="/modules/savedtutorpanel.jsp" /><%
          	}else if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && request.getParameter("page") != null && session.getAttribute("userLevel").equals("teacher") && !session.getAttribute("userID").equals("0") && request.getParameter("page").equals("classes")){ 
          		%><jsp:include page="/modules/teachers/teacherclasses.jsp" /><%
          	}else if(request.getParameter("page") != null && request.getParameter("page").equals("tutordetails")){ 
          		%><jsp:include page="/modules/tutordetails.jsp" /><%
          	}else if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && session.getAttribute("userLevel").equals("teacher") && !session.getAttribute("userID").equals("0")){ 
          		%><jsp:include page="/modules/createtutorbox.jsp" /><jsp:include page="/modules/commonterms.jsp" /><%
          	}else{
          		%><jsp:include page="/modules/teachers/teachercontent.jsp" /><%
          	}%>
          </td></tr>
        </table></td>
        <td width="350" valign="top">
        	<%
        		if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && session.getAttribute("userLevel").equals("teacher") && !session.getAttribute("userID").equals("0")){ 
        			%><%@ include file="/modules/teachers/teacherinfobox.jsp" %><%
        		}else{
        			%><%@ include file="/modules/teachers/teacherloginbox.jsp" %><%
        		}
        	%>
        	<%@ include file="/modules/mostpopular2.jsp" %>
        </td>
        <td width="5"></td>
        <td width="1" rowspan="3" bgcolor="#122222"></td>
      </tr>
      <tr>
        <td height="10" colspan="2"></td>
        <td></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#122222"></td>
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
HashMap<String, String> teachersfilter = new HashMap<String, String>();
teachersfilter.put("createdBy", (String) session.getAttribute("userName"));
teachersfilter.put("userLevel", (String) session.getAttribute("userLevel"));
            
ArrayList<TutorialInfo> teacherstutorialInfoList = TutorialInfoStore.SelectInfo(teachersfilter);
session.setAttribute("tutorialInfoList", teacherstutorialInfoList);
session.setAttribute("studentId", session.getAttribute("userID"));
DeliverableLauncher teacherslauncher = new DeliverableLauncher();
session.setAttribute("deliverableLauncher", teacherslauncher);
%> 
</body>
</html>