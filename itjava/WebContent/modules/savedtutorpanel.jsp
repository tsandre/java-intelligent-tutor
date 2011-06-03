<%@ page import="itjava.model.*, itjava.data.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<%
HashMap<String, String> filter = new HashMap<String, String>();
filter.put("createdBy", (String) session.getAttribute("userName"));
filter.put("userLevel", (String) session.getAttribute("userLevel"));
            
ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(filter);
session.setAttribute("tutorialInfoList", tutorialInfoList);
session.setAttribute("studentId", session.getAttribute("userID"));
DeliverableLauncher launcher = new DeliverableLauncher();
session.setAttribute("deliverableLauncher", launcher);
%>
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
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" colspan="3" bgcolor="#333333"></td>
	</tr>
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
	                  		  	rs2.next();
	                  		  	if(rs2.getString("avgrating") != null){
	                  		  		avgrating = "AVG RATING: " + rs2.getString("avgrating");
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
	                        	if(userLevel.equals("student")){
		                            out.println("<tr class=\"myClass\" onclick=\"gotoURL('students.jsp?page=tutordetails&start=1&id=" + tutorialInfo.getTutorialId() + "');\">");
	                        	}else{
	                        		out.println("<tr class=\"myClass\" onclick=\"gotoURL('teachers.jsp?page=tutordetails&start=1&id=" + tutorialInfo.getTutorialId() + "');\">");
	                        	}
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
	                            out.print((description.length() > 120) ? description.substring(0, 120) + "..." : description);
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
               	  	   if(i==0){
               	  		   out.println("You have no tutors in your account");
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