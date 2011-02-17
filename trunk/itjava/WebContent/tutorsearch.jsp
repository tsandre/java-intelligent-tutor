<%@page import="org.apache.jasper.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>My Saved Tutors</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="js/ratingsys.js"></script> 
<script type="text/javascript" language="javascript" src="js/main.js"></script> 
<style input="text/css">
.rateStatus{float:left; clear:both; width:100%; height:20px;}
.rateMe{float:left; clear:both; width:100%; height:auto; padding:0px; margin:0px;}
.rateMe li{float:left;list-style:none;}
.rateMe li a:hover,
.rateMe .on{background:url(images/star_on.gif) no-repeat; cursor:pointer; width:12px; height:12px;}
.rateMe a{float:left;background:url(images/star_off.gif) no-repeat; width:12px; height:12px;}
.ratingSaved{display:none;}
.saved{color:red; }
</style>
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
	session.setAttribute("userID", "0");
	session.setAttribute("userName", tempuser);
	session.setAttribute("userLevel", "unknown");
} 
%> 
<table width="1024" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><%@ include file="/modules/headers/header5.jsp" %></td>
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
            ArrayList<TutorialInfo> tstutorialInfoList = (ArrayList<TutorialInfo>) session.getAttribute("tutorialInfoList");
            session.setAttribute("studentId", session.getAttribute("userID"));
            DeliverableLauncher tslauncher = new DeliverableLauncher();
            session.setAttribute("deliverableLauncher", tslauncher);
%>
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" colspan="3"></td>
	</tr>
	<tr>
	<td width="1"></td>
	<td></td>
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
                        Connection tsconn = null;
                		PreparedStatement tsucpst = null;
                		ResultSet tsrs = null;
                		PreparedStatement tsucpst2 = null;
                		ResultSet tsrs2 = null;
                		try
                		   {
                			tsconn = DBConnection.GetConnection();
                		   
	                		for (TutorialInfo tutorialInfo : tstutorialInfoList) {	
	                        	i++;
	                        	String getrating = "SELECT rating FROM TutorRatings WHERE tutorId=? AND userName=? AND userLevel=?";
	                        	String getavgrating = "SELECT ROUND(AVG(rating), 2) as avgrating FROM TutorRatings WHERE tutorId=?";
	                        	String userLevel = "unknown";
	                        	if(session.getAttribute("userLevel") != null){
	                        		userLevel = (String) session.getAttribute("userLevel");
	                        	}
	                        
	                  		    tsucpst = tsconn.prepareStatement(getrating);
	                  		    tsucpst2 = tsconn.prepareStatement(getavgrating);
	                  		    
	                  		    tsucpst.setInt(1, tutorialInfo.getTutorialId());
	                  		    tsucpst.setString(2, (String) session.getAttribute("userName"));
	                  		    tsucpst.setString(3, userLevel	);
	                  		    tsucpst2.setInt(1, tutorialInfo.getTutorialId());
	                  		    
	                  		    tsrs = tsucpst.executeQuery();
	                  		    tsrs2 = tsucpst2.executeQuery();
	                  			//System.out.println(getavgrating + ": " + tutorialInfo.getTutorialId());
	                  		    int ratingval;
	                  		    String avgrating;
	                  		  	if(tsrs.next()){
	                  		  		ratingval = tsrs.getInt("rating");
	                  		  	}else{
	                  		  		ratingval = 0;
	                  		  	}
	                  		  	tsrs2.next();
	                  		  	if(tsrs2.getString("avgrating") != null){
	                  		  		avgrating = "AVG RATING: " + tsrs2.getString("avgrating");
	                  		  	}else{
	                  		  		avgrating = "";
	                  		  	}
	                  		  	
	                        	out.println("<tr>");
	                            out.println("<td colspan=\"2\" style=\"color:#FFF\">");
	                            out.println("<table><tr>");
	                            out.print("<td style=\"width:75px; \">");
	                            if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && !session.getAttribute("userLevel").equals("unknown") && !session.getAttribute("userID").equals("0")){
	                            	out.println("<form id=\"myform"+i+"\" method=\"POST\" action=\"updateRatingServlet\"><div class = \"rateMe\" id=\"rateMe_"+i+"\" title=\"\">");
	                            	out.println("<a onclick=\"rateIt(this, " + i + ", 1)\" id=\"_" + i + "_1\" title=\"Poor\" onmouseover=\"rating(this, " + i + ", 1)\" onmouseout=\"off(this, " + i + ", 1)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + i + ", 2)\" id=\"_" + i + "_2\" title=\"Not Bad\" onmouseover=\"rating(this, " + i + ", 2)\" onmouseout=\"off(this, " + i + ", 2)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + i + ", 3)\" id=\"_" + i + "_3\" title=\"Good\" onmouseover=\"rating(this, " + i + ", 3)\" onmouseout=\"off(this, " + i + ", 3)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + i + ", 4)\" id=\"_" + i + "_4\" title=\"Great\" onmouseover=\"rating(this, " + i + ", 4)\" onmouseout=\"off(this, " + i + ", 4)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + i + ", 5)\" id=\"_" + i + "_5\" title=\"Excellent\" onmouseover=\"rating(this, " + i + ", 5)\" onmouseout=\"off(this, " + i + ", 5)\"></a>");
	                            	out.print("</div><input type=\"hidden\" id=\"tutorID_"+i+"\" value=\"" + tutorialInfo.getTutorialId() + "\" /><input type=\"hidden\" id=\"ratingValue_"+i+"\" value=\"");
	                            	if(ratingval != 0){
		                  		 	   out.println(ratingval + "\" /></form> <script language=\"javascript\" type=\"text/javascript\">rating(this, " + i + ", " + ratingval + ");</script>");
	                  		   	 	}else{
	                  		    		out.println("0\" /></form>");
	                  		    	}
	                            }
	                            out.println("</td>");
	                    		out.println("<td style=\"width:100px; margin-top:5px; padding-top:5px\">");
	                            out.println("<span class=\"rateStatus\" id=\"rateStatus_"+i+"\"></span>");
	                            out.println("<span class=\"ratingSaved\" id=\"ratingSaved_"+i+"\"></span>");
	                            out.println("</td>");
	                            out.println("</tr></table>");
	                        	out.println("</td><td style=\"text-align:right; color:#FFF; font-size:11px;\">");
	                        	out.println(avgrating);
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
	                            out.print("<td class=\"tdDescription\">");
	                            String description = tutorialInfo.getTutorialDescription();
	                            out.print((description.length() > 60) ? description.substring(0, 60) + "..." : description);
	                            out.println("</td>");
	                            out.println("</tr>");
	                            out.println("<tr><td colspan=\"3\"></td></tr>");
	        
	                        }
               		   }catch(Exception e) {
               	  	     e.printStackTrace();
               	  	   }
               	  	   finally {
               	  		 try {
               				   tsconn.close();
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
        <td width="350" valign="top">
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