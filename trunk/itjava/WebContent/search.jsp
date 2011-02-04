<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create a jTutor</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" /><style type="text/css">
<!--
.navmain a {
	font-family: segoe ui, verdana;
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
	font-family: segoe ui, verdana;
	font-size: 12px;
	color: #333;
	text-align: center;
}
.basicbutton {
	font-family: segoe ui, verdana;
	font-size: 12px;
	color: #333;
	text-align: center;
}
.titles {
	font-family: segoe ui, verdana;
	font-weight: bold;
	font-size: 16px;
	color: #3E4854;
}
#form1 table tr td p {
	color: #900;
}
.basic1 {	font-family: segoe ui, verdana;
	font-size: 12px;
	color: #333;
	text-align: left;
}
#form2 table tr td table tr .basic {
	text-align: right;
}
#divProgress2 {
	width:100%;
	height:200px;
	text-align:center;
	font-family: segoe ui, verdana;
	display: none;
}
#query {
	width:450px;
	border-color:#333;
	border-style:inset;
	border-width:1px;
	margin: 5px 0 0 0;
	font-family: segoe ui, verdana;
	font-size:12px;
}
#btnSearch {
	background-color:#EEE;
	border:solid;
	border-style:outset;
	border-width:1px;
	border-color:#CCC;
	margin: 3px 0 0 0;
	font-family: segoe ui, verdana;
	font-size:12px;
}
<% 
if(session.getAttribute("userLevel") != null && session.getAttribute("userLevel") == "student"){
	out.println("#studentLogin {");
	out.println("display: inline;");
	out.println("}");
	out.println("#teacherLogin {");
	out.println("display: none;");
	out.println("}");
}else{
	out.println("#studentLogin {");
	out.println("display: none;");
	out.println("}");
	out.println("#teacherLogin {");
	out.println("display: inline;");
	out.println("}");
}
%>
-->
</style>
<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script src="MD5.js"></script>
<script language="javascript" type="text/javascript">

function isValidEmail(str){
	return(str.indexOf(".")>2)&&(str.indexOf("@")>0);
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
	var username = document.getElementById("username3").value;
	var password = document.getElementById("password3").value;
	
	if(username.length < 1){
		alert("Please enter your username to login.");
		document.form1.username2.focus();
	}else if(password.length < 1){
		alert("Please enter your password");
		document.form1.password2.focus();
	}else{
		document.forms["form3"].submit();
	}
}

function checkAvailability(){
	window.open('checkAvailability.jsp?username='+document.getElementById("username").value,'mywindow','width=400,height=200')
}

function showProgress2() {
	document.getElementById("divProgress2").style.display = 'table-cell';
}

function showStudentLogin(){
	document.getElementById("studentLogin").style.display = 'block';
	document.getElementById("teacherLogin").style.display = 'none';
}

function showTeacherLogin(){
	document.getElementById("teacherLogin").style.display = 'block';
	document.getElementById("studentLogin").style.display = 'none';
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
          <tr>
            <td class="basic">
            <table border="0" cellspacing="0" cellpadding="0" width="550" align="center"><tr style="color:#fff;"><td colspan="3" bgcolor="#122222">Tutor Search</td></tr><tr><td width="1" bgcolor="#122222"></td><td>
<form action="TutorSearchServlet" method="get" name="tutorSearchForm" id="tutorSearchForm">

  <p>What kind of tutor are you looking for?<br />  <input type="text" name="query" id="query" placeholder="Enter query"/>  <br /><input name="btnSearch" type="submit" id="btnSearch" onclick="return showProgress2();" value="Find Tutors"/>
  </p>
</form>
<table width="0" align="center"><tr><td>
<div id="divProgress2">
<img src="images/loopLoader.gif" /><br />
Searching the web for code snippets...<br>Please Wait</br></div></td></tr></table></td><td width="1" bgcolor="#122222"></td></tr><tr><td colspan="3" bgcolor="#122222" height="1"></td></tr></table></td>
          </tr>
        </table></td>
        <td width="350" valign="top">
        <div id="teacherLogin" name="teachersLogin">
        <table width="351" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222"></td>
            <td width="348"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" height="20" align="center" bgcolor="#122222"><a href="#" title="Student Login" class="navmain" onclick="showStudentLogin()">Student Login</a></td>
                <td width="5">&nbsp;</td>
                <td width="140" bgcolor="#3e4854" align="center"><a href="#" title="Teacher Login" class="navmain" onclick="showTeacherLogin()">Teacher Login</a></td>
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
 				 ucpst.setInt(2, (Integer) session.getAttribute("userID"));
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
                <td align="left"><a href="accountTeacher.jsp" target="_self" class="rightmenunav">Account Information</a></td>
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
            
            <form id="form2" name="form2" method="post" action="LoginTeacherServlet">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <% if(request.getParameter("error") != null && request.getParameter("error").equals("4")){ %><tr>
                  <td align="center">*Login failed. Please try again.</td>
                </tr><br>
				<% } %>
                <tr>
                  <td align="center"><span class="basic" style="font-weight:bold">Teacher Login</span></td>
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
          </table>
          </div>
          <div id="studentLogin" name="studentsLogin">
          <table width="351" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222"></td>
            <td width="348"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" height="20" align="center" bgcolor="#122222"><a href="#" title="Student Login" class="navmain" onclick="showStudentLogin()">Student Login</a></td>
                <td width="5">&nbsp;</td>
                <td width="140" bgcolor="#3e4854" align="center"><a href="#" title="Teacher Login" class="navmain"onclick="showTeacherLogin()">Teacher Login</a></td>
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
            <form id="form3" name="form3" method="post" action="LoginStudentServlet">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <% if(request.getParameter("error") != null && request.getParameter("error").equals("4")){ %><tr>
                  <td align="center">*Login failed. Please try again.</td>
                </tr><br />
				<% } %>
                <tr>
                  <td align="center"><span class="basic" style="font-weight:bold">Student Login</span></td>
                </tr>
                <tr>
                  <td height="5" align="center"></td>
                </tr>
                <tr>
                  <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="110" align="right" class="basic" style="text-align:right;">Username:</td>
                      <td width="5">&nbsp;</td>
                      <td align="left"><input name="username3" type="text" class="basic1" id="username3" style="width:140px" /></td>
                    </tr>
                    <tr>
                      <td align="right" class="basic" style="text-align:right;">Password:</td>
                      <td>&nbsp;</td>
                      <td align="left"><input name="password3" type="password" class="basic1" id="password3" style="width:140px" /></td>
                    </tr>
                    <tr>
                      <td height="5" colspan="3" align="right"></td>
                      </tr>
                    <tr>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td align="left"><input name="button4" type="button" class="basicbutton" id="button4" value="Login" style="width:125px;" onmousedown="Javascript: checkForm3();" /></td>
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
          </table>
          </div>
          </td>
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
	session.setAttribute("userName", tempuser);
	session.setAttribute("userLevel", "unknown");
} 
%>
</body>
</html>