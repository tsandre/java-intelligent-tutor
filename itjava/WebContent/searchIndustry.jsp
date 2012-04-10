<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.scraper.*, itjava.presenter.*, itjava.model.*, itjava.view.*, java.util.HashMap, java.util.ArrayList, java.sql.*, java.util.Enumeration, java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="http://experiments.eecs.oregonstate.edu:8080/favicon.ico" type="image/x-icon" />
<title>Search API methods in Industry Code repository</title>
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
        <td valign="top" style="width: 522px;" align="center">
<form action="IndustrySearchServlet" method="get" name="industrySearchForm" id="industrySearchForm">
<table align="center" style="padding-top: 10px; margin: 0px;" cellspacing="0" cellpadding="0">
	<tr style="color:#fff;">
            <td colspan="3" bgcolor="#122222" align="center" style="padding: 0px; margin: 0px; color:#FFF; font-weight:bold; font-family:Arial, Helvetica, sans-serif; font-size:12px;">Industry related Code</td>
        </tr>
        <tr>
            <td style="width:1px; background-color:#122222; padding: 0px; margin: 0px; height:100px;"></td>
            <td style="width:510px" align="center">
                <table cellspacing="0" cellpadding="0" >
                    <tr>
                        <td colspan="3" align="center">Enter an API method to get related Code</td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="query" id="query" style="width:400px;" />
                        </td>
                        <td width="1"></td>
                        <td><input type="submit" name="Searchbtn" id="Searchbtn" onclick="return showProgress();" value="Search" /></td>
                    </tr>
                </table>
            </td>
            <% 
						  String noResultClass = null;
						  if(request.getParameter("error") != null && request.getParameter("error").equals("5")){ 
							  noResultClass = "noResult";
						  }
						  else noResultClass = "hideResult";
			%>
            <td style="width:1px; background-color:#122222; padding: 0px; margin: 0px;"></td>
        </tr>
        <tr>
            <td colspan="3" bgcolor="#122222" height="1" style="padding: 0px; margin: 0px;"></td>
        </tr>
</table>
<div id="<%= noResultClass %>">* We did not find any results for the query.</div>
</form>
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
String tempQuery = (String) session.getAttribute("query");
session.setAttribute("query", tempQuery);
%>
</body>
</html>