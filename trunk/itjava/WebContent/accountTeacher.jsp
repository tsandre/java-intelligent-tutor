<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Teacher Page</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" /><style type="text/css">
<!--
.navmain a {
	font-family: Arial, Helvetica, sans-serif;
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
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333;
	text-align: left;
}
.basicbutton {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333;
	text-align: center;
}
.titles {
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 16px;
	color: #3E4854;
}
#form1 table tr td p {
	color: #900;
}
.basic1 {	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333;
	text-align: left;
}
#form2 table tr td table tr .basic {
	text-align: right;
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

function checkAvailability(){
	window.open('checkAvailability.jsp?username='+document.getElementById("username").value,'mywindow','width=400,height=200')
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
          <% if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel").equals("teacher")){ %>
            <tr><td align="center">
            <form id="form3" name="form3" method="post" action="UpdateTeacherServlet">
              <table width="450" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="1" rowspan="21" align="center" bgcolor="#3E4854" class="titles"></td>
                  <td colspan="3" align="center" bgcolor="#3E4854" class="titles"><span style="color:white; font:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold;; font-family: Arial, Helvetica, sans-serif">Update Account Information</span></td>
                  <td width="1" rowspan="21" align="center" bgcolor="#3E4854" class="titles"></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                
                  <% if(request.getParameter("error") != null){%><tr>
                 <td align="left" colspan="3" style="padding:5px 5px 5px 5px"><% if(request.getParameter("error").equals("3") || request.getParameter("error").equals("1")){ %><p>*The username you have selected all ready exists. Please be sure to check the availability before submitting.</p>
                    <% } 
						if(request.getParameter("error").equals("3") || request.getParameter("error").equals("2")){%>
                    <p>*The email you have used is already connected to another account. To recover your password <a href="teacherPasswordRecover.jsp" title="click here" target="_self">click here</a>.</p><% } %></td></tr><% } %>
                    
                     <% if(request.getParameter("success") != null){%>
                    <tr><td align="left" colspan="3" style="padding:5px 5px 5px 5px"><p>*Your information has been updated successfully!</p></td></tr><% } %>
                
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">&nbsp;</td>
                </tr>
                <%  Connection conn = null;
        	   PreparedStatement pst = null;
			   PreparedStatement ucpst = null;
    		   ResultSet rs = null;
        	   try{
        	     conn = DBConnection.GetConnection();
        	     String usercheck = "SELECT firstName, lastName, school, username, email FROM teachers WHERE username = ? AND teacherID = ?";
 				 ucpst = conn.prepareStatement(usercheck);
 				 ucpst.setString(1, (String) session.getAttribute("userName"));
 				 ucpst.setString(2, (String) session.getAttribute("userID"));
 				 rs = ucpst.executeQuery();
 				 rs.next();
				 out.println("<tr>");
                  out.println("<td width=\"140\" align=\"right\">First Name:</td>");
                  out.println("<td width=\"5\">&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"firstName3\" type=\"text\" class=\"basic\" id=\"firstName3\" style=\"width:140px\" value=\"" + rs.getString("firstName") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td align=\"right\">Last Name:</td>");
                  out.println("<td>&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"lastName3\" type=\"text\" class=\"basic\" id=\"lastName3\" style=\"width:140px\" value=\"" + rs.getString("lastName") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td align=\"right\">Your School:</td>");
                  out.println("<td>&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"school3\" type=\"text\" class=\"basic\" id=\"school3\" style=\"width:200px\" value=\"" + rs.getString("school") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
				}
        	   catch(Exception e) {
        	     e.printStackTrace();
        	   }
        	   finally {
        	     conn.close();
        	   } %>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">&nbsp;</td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">Change Password: (Optional)</td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="password3" type="password" class="basic" id="password3" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Confirm Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="passwordConfirm3" type="password" class="basic" id="passwordConfirm3" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">
                    <input name="subbutton3" type="button" class="basicbutton" id="button3" value="Update" style="width:125px;" onmousedown="Javascript: checkForm3();" />
                  </td>
                  </tr>
                <tr>
                  <td colspan="3" align="right">&nbsp;</td>
                </tr>
                <tr>
                  <td height="1" colspan="3" align="right" bgcolor="#3E4854"></td>
                  </tr>
              </table>
            </form>
            </td></tr>
            <% }else{%>
          <tr>
            <td class="titles">Welcome Teachers!</td>
          </tr>
          <tr>
            <td class="basic">This website will help you create intelligent tutors which can be distributed to your students. They will be able to learn the Java API material more quickly and easily than they can with the traditional example based methods. Please take time to register with us before creating your first tutor. This will help you keep track of your tutors and make it possible to set up online classrooms. This will make the distribution of material to your students quick and easy. Lets get started, please fill out the form below to begin.</td>
          </tr>
          <tr>
            <td class="basic">&nbsp;</td>
          </tr>
          <tr>
            <td align="center">
            
            <form id="form1" name="form1" method="post" action="CreateTeacherServlet">
              <table width="450" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="1" rowspan="21" align="center" bgcolor="#3E4854" class="titles"></td>
                  <td colspan="3" align="center" bgcolor="#3E4854" class="titles"><span style="color:white; font:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold;">Sign Up Now!</span></td>
                  <td width="1" rowspan="21" align="center" bgcolor="#3E4854" class="titles"></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                
                  <% if(request.getParameter("error") != null){%><tr>
                 <td align="left" colspan="3" style="padding:5px 5px 5px 5px"><% if(request.getParameter("error").equals("3") || request.getParameter("error").equals("1")){ %><p>*The username you have selected all ready exists. Please be sure to check the availability before submitting.</p>
                    <% } 
						if(request.getParameter("error").equals("3") || request.getParameter("error").equals("2")){%>
                    <p>*The email you have used is already connected to another account. To recover your password <a href="teacherPasswordRecover.jsp" title="click here" target="_self">click here</a>.</p><% } %></td></tr><% } %>
                
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">&nbsp;</td>
                </tr>
                <tr>
                  <td width="140" align="right">First Name:</td>
                  <td width="5">&nbsp;</td>
                  <td align="left"><input name="firstName" type="text" class="basic" id="firstName" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Last Name:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="lastName" type="text" class="basic" id="lastName" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Your School:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="school" type="text" class="basic" id="school" style="width:200px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Email Address:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="email" type="text" class="basic" id="email" style="width:200px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Username:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="username" type="text" class="basic" id="username" style="width:140px" /> <label>
                    <input type="button" name="button2" class="basicbutton" id="button2" value="Check Availability" onmousedown="Javascript: checkAvailability();" />
                  </label></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="password" type="password" class="basic" id="password" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Confirm Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="passwordConfirm" type="password" class="basic" id="passwordConfirm" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">
                    <input name="subbutton" type="button" class="basicbutton" id="button" value="Join" style="width:125px;" onmousedown="Javascript: checkForm();" />
                  </td>
                  </tr>
                <tr>
                  <td colspan="3" align="right">&nbsp;</td>
                </tr>
                <tr>
                  <td height="1" colspan="3" align="right" bgcolor="#3E4854"></td>
                  </tr>
              </table>
            </form></td>
          </tr><% } %>
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
            <td width="1" rowspan="3" bgcolor="#3E4854"></td>
            <td height="5" bgcolor="#3E4854"></td>
            <td width="2" rowspan="3" bgcolor="#3E4854"></td>
            </tr>
          <tr>
            <td height="100" style="padding: 5px 5px 5px 5px; vertical-align:top; text-align: right; font-family: Arial, Helvetica, sans-serif; color: #333; font-size: 12px;">
           <% if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel").equals("teacher")){ 
        	  
			%>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left"><span class="basic"><%  Connection conn = null;
        	   PreparedStatement pst = null;
			   PreparedStatement ucpst = null;
    		   ResultSet rs = null;
        	   try{
        	     conn = DBConnection.GetConnection();
        	     String usercheck = "SELECT firstName, lastName, school, username, email FROM teachers WHERE username = ? AND teacherID = ?";
 				 ucpst = conn.prepareStatement(usercheck);
 				 ucpst.setString(1, (String) session.getAttribute("userName"));
 				 ucpst.setString(2, (String) session.getAttribute("userID"));
 				 rs = ucpst.executeQuery();
 				 rs.next();
 				 out.print("Welcome back " + rs.getString("firstName") + "!");
        	   }
        	   catch(Exception e) {
        	     e.printStackTrace();
        	   }
        	   finally {
        	     conn.close();
        	   } %></span></td>
                </tr>
              <tr>
                <td>&nbsp;</td>
                </tr>
              <tr>
                <td align="left"><a href="accountTeacher.jsp" target="_self">Account Information</a></td>
                </tr>
              <tr>
                <td align="left"><a href="savedTutors.jsp">Saved Tutors</a></td>
              </tr>
              <tr>
                <td align="left"><a href="classLists.jsp">Class Lists</a></td>
              </tr>
              <tr>
                <td align="left"><a href="logout.jsp" target="_self">Click to logout</a></td>
              </tr>
              </table>
            <% }else{ %>
            <form id="form2" name="form2" method="post" action="LoginTeacherServlet">
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
            <td height="1" bgcolor="#3E4854"></td>
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