<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Saved Tutors</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" /><style type="text/css">
<!--
.navmain a {
	font-family: segoe ui, verdana
	font-size: 12px;
	font-weight: bold;
	color: #FFF;
	text-decoration: none;
}
.asdf {
	color: #000;
	font-weight: bold;
}
.basic {
	font-family: segoe ui, verdana
	font-size: 12px;
	color: #333;
	text-align: left;
}
.basicbutton {
	font-family: segoe ui, verdana
	font-size: 12px;
	color: #333;
	text-align: center;
}
.titles {
	font-family: segoe ui, verdana
	font-weight: bold;
	font-size: 16px;
	color: #3E4854;
}
#form1 table tr td p {
	color: #900;
}
.basic1 {	font-family: segoe ui, verdana
	font-size: 12px;
	color: #333;
	text-align: left;
}
#form2 table tr td table tr .basic {
	text-align: right;
}
.tdBold {
	font-weight:bold;
	font-size:12px;
}

.tdRegular {
	font-size:12px;
}

#tableMain {
	
}

#alternateLaunch {
	display: none;
}

#divNavigate {
	width: 600x;
	text-align:left;
}
#divContainer {
	width: 600px;
	text-align: center;
}
#divMessage {
	text-align: center;
}
-->
</style>
<script language="javascript" type="text/javascript">
function isValidEmail(str){
	return(str.indexOf(".")>2)&&(str.indexOf("@")>0);
}
function checkForm(){
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var school = document.getElementById("school").value;
	var email = document.getElementById("email").value;
	var username = document.getElementById("username").value;
	var password1 = document.getElementById("password").value;
	var password2 = document.getElementById("passwordConfirm").value;
	
	if(firstName.length < 1){
		alert("Please enter your first name.");
		document.form1.firstName.focus();
	}else if(lastName.length < 1){
		alert("Please enter your last name.");
		document.form1.lastName.focus();
	}else if(school.length < 1){
		alert("Please enter your school.");
		document.form1.school.focus();
	}else if(!isValidEmail(email)){
		alert("Please enter a valid email.");
		document.form1.email.focus();
	}else if(username.length < 1){
		alert("Please enter your username.");
		document.form1.username.focus();
	}else if(password1.length < 6 || password1.length > 12){
		alert("Please enter a valid password. Passwords must be 6-12 characters.");
		document.form1.password.focus();
	}else if(password1 != password2){
		alert("The passwords do not match! Please re-enter your password to ensure they are correct.");
		document.form1.password.focus();
	}else{
		document.forms["form1"].submit();
	}
}

function checkForm2(){
	var username = document.getElementById("username2").value;
	var password = document.getElementById("password2").value;
	
	if(username.length < 1){
		alert("Please enter your username to login.");
		document.form1.username2.focus();
	}else if(password.length < 1){
		alert("Please enter your password");
		document.form1.password2.focus();
	}else{
		document.forms["form2"].submit();
	}
}

function checkForm3(){
	var firstName = document.getElementById("firstName3").value;
	var lastName = document.getElementById("lastName3").value;
	var school = document.getElementById("school3").value;
	var password1 = document.getElementById("password3").value;
	var password2 = document.getElementById("passwordConfirm3").value;

	if(firstName.length < 1){
		alert("Please enter your first name.");
		document.form3.firstName.focus();
	}else if(lastName.length < 1){
		alert("Please enter your last name.");
		document.form3.lastName.focus();
	}else if(school.length < 1){
		alert("Please enter your school.");
		document.form3.school.focus();
	}else if(password1.length != 0 && (password1.length < 6 || password1.length > 12)){
		alert("Please enter a valid password. Passwords must be 6-12 characters.");
		document.form3.password.focus();
	}else if(password1 != password2){
		alert("The passwords do not match! Please re-enter your password to ensure they are correct.");
		document.form3.password.focus();
	}else{
		document.forms["form3"].submit();
	}
}

function gotoURL(URL) {
	window.location = URL;
}

var launchCounts = 0;
function launchNext(folderName, deliverableName) {
	if (launchCounts == 0) {
		launchCounts++;
		window.open("delivery/" + folderName + "/" + deliverableName + ".jnlp");
		document.getElementById("btnLaunch").value = "Save & Next >>";
		document.getElementById("alternateLaunch").style.display = 'block';
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

function checkAvailability(){
	window.open('checkAvailability2.jsp?username='+document.getElementById("username").value,'mywindow','width=400,height=200')
}
</script>
</head>

<body>
<table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <table width="1024" border="0" cellspacing="0" cellpadding="0" bgcolor="#122222">
      <tr>
        <td height="1" colspan="3" bgcolor="#1222222"></td>
        </tr>
      <tr>
        <td width="1" bgcolor="#122222"></td>
        <td height="77" background="images/bannerbg.jpg"><img src="images/logo1.jpg" width="200" height="60" border="0" /></td>
        <td width="1" bgcolor="#122222"></td>
      </tr>
      <tr>
        <td height="2" colspan="3" bgcolor="#1222222"></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="25" bgcolor="#122222"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="110" align="center"><a href="search.jsp" title="Tutor Search" class="navmain">Tutor Search</a></td>
        <td width="110" align="center"><a href="index.jsp" title="Create Tutor" class="navmain">Create Tutor</a></td>
        <td width="135" align="center"><a href="teachers.jsp" title="Teacher Account" class="navmain">Teacher Account</a></td>
        <td width="135" align="center"><a href="students.jsp" title="Student Account" class="navmain">Student Account</a></td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" rowspan="3" bgcolor="#122222"></td>
        <td rowspan="2" valign="top" style="padding:0px 5px 5px 5px; color: #333; font-size: 12px; font-family: Arial, Helvetica, sans-serif;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10"></td>
          </tr>
        
            <tr><td align="center">
            <%

			int tutorialInfoId = Integer.parseInt(request.getParameter("id"));
			
			DeliverableLauncher deliverableLauncher = (DeliverableLauncher)session.getAttribute("deliverableLauncher");
			if (deliverableLauncher == null) {
				System.err.println("Landed on studentMainTest.jsp from a wrong source..");
				response.sendRedirect("studentWelcome.jsp");
			}
			else {
				String studentId = session.getAttribute("studentId").toString();
				deliverableLauncher.setStudentId(Integer.parseInt(studentId));
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
			%>
<form id="formMainTest" method="post" action="DeliverableSelection2Servlet">
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
        	out.println("<td width=\"600\">");
          	out.println("<table width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
            out.println("<tbody>");
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
	String buttonLabel = "Begin Lesson";

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
<%
String messageToUser = null;
if (deliverableName.contains("Example")) {
	messageToUser = "<b>Example Tutorial Instructions</b><br/><i>Read through it and try to understand the code.</i><br/><i>Key API related methods and its usages are italicized.</i><br/><i>Dont forget to click Done and close the window once you read through it.</i><br/><i>If prompted to save, click Yes.</i><br/><i>Click Next button on the browser page to fetch the next tutorial for the topic.</i><br/>";
}
else if (deliverableName.contains("Quiz")) {
	messageToUser = "<b>Quiz Instruction</b><br/><i>Blanks appear in between the code. Fill in the blanks with appropriate code and click Done.</i><br/><i>If the answer you entered in the blank is correct, text box turns green in color.</i><br/><i>If the answer is wrong, the blank turns red in color. In this case, you can use hints and try re-typing the answer. Info reg. using hints is given below.</i><br/><i>Fill in all the blanks in the Quiz similarly. Click DONE and close the tutor window. When prompted to Save, click Yes.</i><br/><br/><b>Using hints in a Quiz:</b><br/><i>If you are NOT sure about what code to fill in the blank, click Hint button in the right pane. Use the arrow keys beside it to toggle between different hints. The lesser the hints you use, the higher is your knowledge in the topic.</i><br/><i>The final hint is usually answer to be filled in the blank.</i><br/>";
}
%>
<div id="divMessage"><%= messageToUser %></div>
<table id="divContainer" align="center"><tbody><tr><td>
<div id="divNavigate">
<input type="submit" id="btnLaunch" name="btnLaunch" value="<%= buttonLabel%>" 
	onclick="return launchNext('<%= folderName%>', '<%= deliverableName%>');"
	<%= disabledLaunch%>/>
</div>
<div id="alternateLaunch">
If you closed the pop-up by mistake, click on this <input type="button" value="button" onclick="return launchNow('<%= folderName%>', '<%= deliverableName%>');"/> to re-take the quiz.
</div>
</td></tr></tbody>
</div>
</form>
		</td>
    	<td width="1" bgcolor="#333333"></td>
    </tr>
	<tr>
  		<td height="1" colspan="3" bgcolor="#333333"></td>
  	</tr>
</table>
            </td></tr>
            
        </table></td>
        <td width="350"><table width="351" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222"></td>
            <td width="348"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" height="20" align="center" bgcolor="#122222"><a href="students.jsp" title="Student Login" class="navmain">Student Login</a></td>
                <td width="5">&nbsp;</td>
                <td width="140" bgcolor="#3e4854" align="center"><a href="teachers.jsp" title="Teacher Login" class="navmain">Teacher Login</a></td>
                <td>&nbsp;</td>
                </tr>
              </table></td>
            <td width="2"></td>
            </tr>
          <tr>
            <td width="1" rowspan="3" bgcolor="#122222"></td>
            <td height="5" bgcolor="#122222"></td>
            <td width="2" rowspan="3" bgcolor="#122222"></td>
            </tr>
          <tr>
            <td height="100" style="padding: 5px 5px 5px 5px; vertical-align:top; text-align: right; font-family: Arial, Helvetica, sans-serif; color: #333; font-size: 12px;">
           <% if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel").equals("student")){ 
            %>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left"><span class="basic">Welcome back <% Connection conn = null;
			PreparedStatement pst = null;
			PreparedStatement ucpst = null;
			ResultSet rs = null;
			try{
				conn = DBConnection.GetConnection();
				String usercheck = "SELECT firstName, lastName, school, username, email FROM students WHERE username = ? AND studentID = ?";
				ucpst = conn.prepareStatement(usercheck);
				ucpst.setString(1, (String) session.getAttribute("userName"));
				ucpst.setInt(2, (Integer) session.getAttribute("userID"));
				rs = ucpst.executeQuery();
				rs.next();
				out.print(rs.getString("firstName"));
			}catch(Exception e) {
       	     e.printStackTrace();
     	   }
     	   finally {
     	     conn.close();
     	   }
			%>!</span></td>
                </tr>
              <tr>
                <td>&nbsp;</td>
                </tr>
              <tr>
                <td align="left"><a href="accountStudent.jsp" target="_self" class="rightmenunav">Account Information</a></td>
                </tr>
              <tr>
                <td align="left"><a href="savedTutors.jsp" class="rightmenunav">My Tutors</a></td>
              </tr>
              <tr>
                <td align="left"><a href="classLists.jsp" class="rightmenunav">Class Lists</a></td>
              </tr>
              <tr>
                <td align="left"><a href="logout.jsp" target="_self" class="rightmenunav">Click to logout</a></td>
              </tr>
              </table>
            <% }else{ %>
            <form id="form2" name="form2" method="post" action="LoginStudentServlet">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <% if(request.getParameter("error") != null && request.getParameter("error").equals("4")){ %><tr>
                  <td align="center">*Login failed. Please try again.</td>
                </tr><br />
				<% } %>
                <tr>
                  <td align="center"><span class="basic" style="font-weight:bold">Please Login</span></td>
                </tr>
                <tr>
                  <td height="5" align="center"></td>
                </tr>
                <tr>
                  <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="110" align="right" class="basic">Username:</td>
                      <td width="5">&nbsp;</td>
                      <td align="left"><input name="username2" type="text" class="basic1" id="username2" style="width:140px" /></td>
                    </tr>
                    <tr>
                      <td align="right" class="basic">Password:</td>
                      <td>&nbsp;</td>
                      <td align="left"><input name="password2" type="password" class="basic1" id="password2" style="width:140px" /></td>
                    </tr>
                    <tr>
                      <td height="5" colspan="3" align="right"></td>
                      </tr>
                    <tr>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td align="left"><input name="button3" type="button" class="basicbutton" id="button3" value="Login" style="width:125px;" onmousedown="Javascript: checkForm2();" /></td>
                    </tr>
                    <tr>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td align="left">Forgot Your Password?</td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </form><% } %>
            </td>
            </tr>
          <tr>
            <td height="1" bgcolor="#122222"></td>
            </tr>
          </table></td>
        <td width="5"></td>
        <td width="1" rowspan="3" bgcolor="#122222"></td>
      </tr>
      <tr>
        <td><table width="350" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222"></td>
            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20" align="left" bgcolor="#122222" style="padding-left:5px; color: #FFF; font-weight: bold; font-family: Arial, Helvetica, sans-serif; font-size: 12px;">Most Popular</td>
                </tr>
              </table></td>
            <td width="1" bgcolor="#122222"></td>
            </tr>
          <tr>
            <td width="1" rowspan="3" bgcolor="#122222"></td>
            <td height="5" bgcolor="#122222"></td>
            <td width="1" rowspan="3" bgcolor="#122222"></td>
            </tr>
          <tr>
            <td height="300">&nbsp;</td>
            </tr>
          <tr>
            <td height="1" bgcolor="#122222"></td>
            </tr>
          </table></td>
        <td>&nbsp;</td>
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
</body>
</html>