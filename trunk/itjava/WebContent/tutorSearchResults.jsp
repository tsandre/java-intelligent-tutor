<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
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
	width: 35%;
	color: gray;
	font-size: 0.9em;
	background-color: beige;
	border-collapse:collapse;
}

.tdTutorialName {
	width: 20%;
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

.rateStatus{float:left; clear:both; width:100%; height:20px;}
.rateMe{float:left; clear:both; width:100%; height:auto; padding:0px; margin:0px;}
.rateMe li{float:left;list-style:none;}
.rateMe li a:hover,
.rateMe .on{background:url(images/star_on.gif) no-repeat; cursor:pointer; width:12px; height:12px;}
.rateMe a{float:left;background:url(images/star_off.gif) no-repeat; width:12px; height:12px;}
.ratingSaved{display:none;}
.saved{color:red; }

-->
</style>
<script type="text/javascript" language="javascript" src="js/ratingsys.js"></script> 
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

function subformsome(tutorid){
	window.open("updateRatingServlet?tutorID=" + document.getElementById("tutorID_" + tutorid).value + "&ratingValue=" + document.getElementById("ratingValue_" + tutorid).value, 'mywindow', 'width=400, height=200');
}
</script>
</head>

<body>
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
                        int i=0;
                        Connection conn = null;
                		PreparedStatement ucpst = null;
                		ResultSet rs = null;
                		PreparedStatement ucpst2 = null;
                		ResultSet rs2 = null;
                		try
                		   {
                			conn = DBConnection.GetConnection();
                		   
	                		for (TutorialInfo tutorialInfo : tutorialInfoList) {	
	                        	i++;
	                        	String getrating = "SELECT rating FROM TutorRatings WHERE tutorId=? AND userName=? AND userLevel=?";
	                        	String getavgrating = "SELECT ROUND(AVG(rating), 2) as avgrating FROM TutorRatings WHERE tutorId=?";
	                        	String userLevel = "unknown";
	                        	if(session.getAttribute("userLevel") != null){
	                        		userLevel = (String) session.getAttribute("userLevel");
	                        	}
	                        
	                  		    ucpst = conn.prepareStatement(getrating);
	                  		    ucpst2 = conn.prepareStatement(getavgrating);
	                  		    
	                  		    ucpst.setInt(1, tutorialInfo.getTutorialId());
	                  		    ucpst.setString(2, (String) session.getAttribute("userName"));
	                  		    ucpst.setString(3, userLevel	);
	                  		    ucpst2.setInt(1, tutorialInfo.getTutorialId());
	                  		    
	                  		    rs = ucpst.executeQuery();
	                  		    rs2 = ucpst2.executeQuery();
	                  			//System.out.println(getavgrating + ": " + tutorialInfo.getTutorialId());
	                  		    int ratingval;
	                  		    String avgrating;
	                  		  	if(rs.next()){
	                  		  		ratingval = rs.getInt("rating");
	                  		  	}else{
	                  		  		ratingval = 0;
	                  		  	}
	                  		  	if(rs2.next()){
	                  		  		avgrating = rs2.getString("avgrating");
	                  		  	}else{
	                  		  		avgrating = "NR";
	                  		  	}
	                  		  	
	                        	out.println("<tr>");
	                            out.println("<td colspan=\"2\" style=\"color:#FFF\">");
	                            out.println("<table><tr>");
	                            out.println("<td style=\"width:75px; \"><form id=\"myform"+i+"\" method=\"POST\" action=\"updateRatingServlet\"><div class = \"rateMe\" id=\"rateMe_"+i+"\" title=\"\">");
	                            out.println("<a onclick=\"rateIt(this, " + i + ", 1)\" id=\"_" + i + "_1\" title=\"Poor\" onmouseover=\"rating(this, " + i + ", 1)\" onmouseout=\"off(this, " + i + ", 1)\"></a>");
	                            out.println("<a onclick=\"rateIt(this, " + i + ", 2)\" id=\"_" + i + "_2\" title=\"Not Bad\" onmouseover=\"rating(this, " + i + ", 2)\" onmouseout=\"off(this, " + i + ", 2)\"></a>");
	                            out.println("<a onclick=\"rateIt(this, " + i + ", 3)\" id=\"_" + i + "_3\" title=\"Good\" onmouseover=\"rating(this, " + i + ", 3)\" onmouseout=\"off(this, " + i + ", 3)\"></a>");
	                            out.println("<a onclick=\"rateIt(this, " + i + ", 4)\" id=\"_" + i + "_4\" title=\"Great\" onmouseover=\"rating(this, " + i + ", 4)\" onmouseout=\"off(this, " + i + ", 4)\"></a>");
	                            out.println("<a onclick=\"rateIt(this, " + i + ", 5)\" id=\"_" + i + "_5\" title=\"Excellent\" onmouseover=\"rating(this, " + i + ", 5)\" onmouseout=\"off(this, " + i + ", 5)\"></a>");
	                            out.print("</div><input type=\"hidden\" id=\"tutorID_"+i+"\" value=\"" + tutorialInfo.getTutorialId() + "\" /><input type=\"hidden\" id=\"ratingValue_"+i+"\" value=\"");
	                            if(ratingval != 0){
		                  		    out.println(ratingval + ")\" /></form> <script language=\"javascript\" type=\"text/javascript\">rating(this, " + i + ", " + ratingval + ");</script>");
	                  		    }else{
	                  		    	out.println("0)\" /></form>");
	                  		    }
	                            out.println("</td>");
	                    		out.println("<td style=\"width:100px; margin-top:5px; padding-top:5px\">");
	                            out.println("<span class=\"rateStatus\" id=\"rateStatus_"+i+"\"></span>");
	                            out.println("<span class=\"ratingSaved\" id=\"ratingSaved_"+i+"\"></span>");
	                            out.println("</td>");
	                            out.println("</tr></table>");
	                        	out.println("</td><td style=\"text-align:right; color:#FFF; font-size:11px;\">");
	                        	out.println("AVG RATING: " + avgrating);
	                        	out.println("</td></tr>");
	                            out.println("<tr class=\"myClass\" onclick=\"gotoURL('tutorSearchResultsDetails.jsp?start=1&id=" + tutorialInfo.getTutorialId() + "');\">");
	                            out.println("<td class=\"tdTutorialName\">");
	                            out.println("<br />");
	                            out.println(tutorialInfo.getTutorialName());
	                            out.println("</td>");
	                            out.println("<td class=\"tdMeta\">");
	                            out.println("<table cellspacing=\"0\" id=\"subTable\">");
	                            
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
               		   }catch(Exception e) {
               	  	     e.printStackTrace();
               	  	   }
               	  	   finally {
               	  		 try {
               				   conn.close();
               			   }catch(Exception e){
               				 e.printStackTrace();
               			   }
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
                <td align="left"><span class="basic">Welcome back <% conn = null;
			PreparedStatement pst = null;
			ucpst = null;
			 rs = null;
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
                      <td width="110" align="right" class="basic">Username:</td>
                      <td width="5">&nbsp;</td>
                      <td align="left"><input name="username3" type="text" class="basic1" id="username3" style="width:140px" /></td>
                    </tr>
                    <tr>
                      <td align="right" class="basic">Password:</td>
                      <td>&nbsp;</td>
                      <td align="left"><input name="password3" type="password" class="basic1" id="password3" style="width:140px" /></td>
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
          </table>
          
          
          <table width="350" border="0" cellspacing="0" cellpadding="0">
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
          <%
		        conn = DBConnection.GetConnection();		
		        String getmostpopular = "SELECT TutorialInfo.*, ROUND(AVG(TutorRatings.rating), 2) as avgrating  FROM TutorialInfo, TutorRatings, TutorRatings as ratings2 WHERE TutorRatings.tutorId = TutorialInfo.tutorialInfoId GROUP BY TutorialInfo.tutorialInfoId ORDER BY avgrating DESC LIMIT 20";
      		    ucpst = conn.prepareStatement(getmostpopular);
      		    rs = ucpst.executeQuery();
			  	while(rs.next()){
			  		out.println("<tr>");
		            out.println("<td width=\"1\" bgcolor=\"#122222\"></td>");
		            out.println("<td bgcolor=\"#FFFFFF\">" + rs.getString("tutorialName") + "</td>");
		            out.println("<td width=\"1\" bgcolor=\"#122222\"></td>");
		            out.println("</tr>");
			  	}
          %>
          <tr>
		           <td width="1" rowspan="3" bgcolor="#122222"></td>
		            <td height="5" bgcolor="#122222"></td>
		            <td width="1" rowspan="3" bgcolor="#122222"></td>
		            </tr>
		         
          <tr>
            <td height="1" bgcolor="#122222"></td>
            </tr>
          </table></td>
        <td width="5"></td>
        <td width="1" rowspan="3" bgcolor="#122222"></td>
      </tr>
      <tr>
        <td valign="top"></td>
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