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
#tableMain {
	width:600px;
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

a {
	color:#333333;
	font-size: 12px;
	font-family: segoe ui, verdana;
}

a:hover {
	color:#000000;
	font-size: 12px;
	font-family: segoe ui, verdana;
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
            ArrayList<TutorialInfo> tutorialInfoList = (ArrayList<TutorialInfo>) session.getAttribute("tutorialInfoList");
            session.setAttribute("studentId", 99);
            DeliverableLauncher launcher = new DeliverableLauncher();
            session.setAttribute("deliverableLauncher", launcher);
%>
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" colspan="3"></td>
	</tr>
	<tr>
	<td width="1"></td>
	<td><a href="search.jsp">Search Again</a></td>
	<td width="1"></td>
	</tr>
	<tr height="5"><td></td><td></td><td></td></tr>
	<tr>
  		<td width="1" bgcolor="#333333"></td>
  		<td>
                <form id="formLaunch">
                <table cellspacing="0" id="tableMain">
                	<tbody>
                        <tr><td colspan="3"></td></tr>
                        <%
                        for (TutorialInfo tutorialInfo : tutorialInfoList) {	
                            out.println("<tr class=\"myClass\" onclick=\"gotoURL('tutorSearchResultsDetails.jsp?start=1&id=" + tutorialInfo.getTutorialId() + "');\">");
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
		</td>
    	<td width="1" bgcolor="#333333"></td>
    </tr>
	<tr>
  		<td height="1" colspan="3" bgcolor="#333333"></td>
  	</tr>
</table>
            </td></tr>
           
        </table></td>
        <td width="350" valign="top"><table width="351" border="0" cellspacing="0" cellpadding="0">
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
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
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