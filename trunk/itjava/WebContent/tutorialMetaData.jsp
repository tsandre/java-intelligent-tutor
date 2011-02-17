<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Saved Tutors</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="js/ratingsys.js"></script> 
<script type="text/javascript" language="javascript" src="js/main.js"></script>
<script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>
<script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>
</head>

<body>
<table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="3"><%@ include file="/modules/headers/header4.jsp" %></td>
	</tr>
	<tr><td colspan="3" bgcolor="#122222" height="1"></td></tr>
	<tr>
		<td width="1" bgcolor="#122222"></td>
        <td style="padding:10px 5px 10px 5px; color: #333; font-size: 12px; font-family: Arial, Helvetica, sans-serif;" align="center">
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="600">
				<tr>
					<td colspan="3" height="1" bgcolor="#122222" id="tabletitle">Please Give Your Tutor a Description</td>
				</tr>
				<tr>
					<td width="1" bgcolor="#122222"></td>
					<td>
				    	<form id="formTutorialMetaData" name="formTutorialMetaData" action="TutorialDeliveryServlet" method="post">
							<table>
								<tr>
									<td align="right" width="150" valign="top">Tutorial Name: </td>
									<td width="5"></td>
									<td align="left"><input type="text" id="txtTutorialName" name="txtTutorialName" /></td>
								</tr>
								<tr>
									<td align="right" valign="top">Tutorial Description: </td>
									<td width="5"></td>
									<td align="left"><textarea rows="5" cols="20" id="txtTutorialDescription" name="txtTutorialDescription"></textarea></td>
								</tr>
								<tr>
									<td></td>
									<td width="5"></td>
									<td align="center"><input type="submit" id="submitMetaData" name="submitMetaData" value="Save Info" onclick="return showProgress();"/></td>
								</tr>
							</table>
						</form>
					</td>
			        <td width="1" bgcolor="#122222"></td>
				</tr>
			    <tr>
			    	<td colspan="3" height="1" bgcolor="#122222"></td>
			    </tr>
			</table>
		    <div id="divProgress">
				<img src="images/loopLoader.gif" /><br />
				Creating Deliverables....<br />Please Wait
			</div>
        </td>
        <td width="1" bgcolor="#122222"></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="#122222"></td>
	</tr>
</table> 
</body>
</html>