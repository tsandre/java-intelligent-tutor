<%@ page import="itjava.model.*, itjava.data.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<%
HashMap<String, String> filter = new HashMap<String, String>();
filter.put("UserTutors.userID", session.getAttribute("userID").toString());
filter.put("UserTutors.userLevel", session.getAttribute("userLevel").toString());

String getmostpopular = "SELECT TutorialInfo.*, ROUND(AVG(TutorRatings.rating), 2) as avgrating  FROM TutorialInfo, TutorRatings, TutorRatings as ratings2 WHERE TutorRatings.tutorId = TutorialInfo.tutorialInfoId GROUP BY TutorialInfo.tutorialInfoId ORDER BY avgrating DESC LIMIT 20";

ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectMostPopular();
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
        <td colspan="3" align="center" height="20" style="font-size:14px; font-family:Arial; font-weight: bold;">Most Popular Tutors</td>
    </tr>	
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
                        int impl=0;
                        Connection connmpl = null;
                		PreparedStatement ucpstmpl = null;
                		ResultSet rsmpl = null;
                		PreparedStatement ucpstmpl2 = null;
                		ResultSet rsmpl2 = null;
                		try
                		   {
                			connmpl = DBConnection.GetConnection();
                		   
	                		for (TutorialInfo tutorialInfo : tutorialInfoList) {	
	                			impl++;
	                        	String getrating = "SELECT rating FROM TutorRatings WHERE tutorId=? AND userName=? AND userLevel=?";
	                        	String getavgrating = "SELECT ROUND(AVG(rating), 2) as avgrating FROM TutorRatings WHERE tutorId=?";
	                        	String userLevel = "unknown";
	                        	if(session.getAttribute("userLevel") != null){
	                        		userLevel = (String) session.getAttribute("userLevel");
	                        	}
	                        
	                        	ucpstmpl = connmpl.prepareStatement(getrating);
	                        	ucpstmpl2 = connmpl.prepareStatement(getavgrating);
	                  		    
	                  		  	ucpstmpl.setInt(1, tutorialInfo.getTutorialId());
	                  			ucpstmpl.setString(2, (String) session.getAttribute("userName"));
	                  			ucpstmpl.setString(3, userLevel	);
	                  			ucpstmpl2.setInt(1, tutorialInfo.getTutorialId());
	                  		    
	                  		  	rsmpl = ucpstmpl.executeQuery();
	                  			rsmpl2 = ucpstmpl2.executeQuery();
	                  			//System.out.println(getavgrating + ": " + tutorialInfo.getTutorialId());
	                  		    int ratingval;
	                  		    String avgrating;
	                  		  	if(rsmpl.next()){
	                  		  		ratingval = rsmpl.getInt("rating");
	                  		  	}else{
	                  		  		ratingval = 0;
	                  		  	}
	                  		  	rsmpl2.next();
	                  		  	if(rsmpl2.getString("avgrating") != null){
	                  		  		avgrating = "AVG RATING: " + rsmpl2.getString("avgrating");
	                  		  	}else{
	                  		  		avgrating = "";
	                  		  	}
	                  		  	
	                        	out.println("<tr>");
	                            out.println("<td colspan=\"2\" style=\"color:#FFF\">");
	                            out.println("<table><tr>");
	                            out.print("<td style=\"width:75px; \">");
	                            if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && !session.getAttribute("userLevel").equals("unknown") && !session.getAttribute("userID").equals("0")){
	                            	out.println("<form id=\"myform"+impl+"\" method=\"POST\" action=\"updateRatingServlet\"><div class = \"rateMe\" id=\"rateMe_"+impl+"\" title=\"\">");
	                            	out.println("<a onclick=\"rateIt(this, " + impl + ", 1)\" id=\"_" + impl + "_1\" title=\"Poor\" onmouseover=\"rating(this, " + impl + ", 1)\" onmouseout=\"off(this, " + impl + ", 1)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + impl + ", 2)\" id=\"_" + impl + "_2\" title=\"Not Bad\" onmouseover=\"rating(this, " + impl + ", 2)\" onmouseout=\"off(this, " + impl + ", 2)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + impl + ", 3)\" id=\"_" + impl + "_3\" title=\"Good\" onmouseover=\"rating(this, " + impl + ", 3)\" onmouseout=\"off(this, " + impl + ", 3)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + impl + ", 4)\" id=\"_" + impl + "_4\" title=\"Great\" onmouseover=\"rating(this, " + impl + ", 4)\" onmouseout=\"off(this, " + impl + ", 4)\"></a>");
	                            	out.println("<a onclick=\"rateIt(this, " + impl + ", 5)\" id=\"_" + impl + "_5\" title=\"Excellent\" onmouseover=\"rating(this, " + impl + ", 5)\" onmouseout=\"off(this, " + impl + ", 5)\"></a>");
	                            	out.print("</div><input type=\"hidden\" id=\"tutorID_"+impl+"\" value=\"" + tutorialInfo.getTutorialId() + "\" /><input type=\"hidden\" id=\"ratingValue_"+impl+"\" value=\"");
	                            	if(ratingval != 0){
		                  		 	   out.println(ratingval + "\" /></form> <script language=\"javascript\" type=\"text/javascript\">rating(this, " + impl + ", " + ratingval + ");</script>");
	                  		   	 	}else{
	                  		    		out.println("0\" /></form>");
	                  		    	}
	                            }
	                            out.println("</td>");
	                    		out.println("<td style=\"width:100px; margin-top:5px; padding-top:5px\">");
	                            out.println("<span class=\"rateStatus\" id=\"rateStatus_"+impl+"\"></span>");
	                            out.println("<span class=\"ratingSaved\" id=\"ratingSaved_"+impl+"\"></span>");
	                            out.println("</td>");
	                            out.println("</tr></table>");
	                        	out.println("</td><td style=\"text-align:right; color:#FFF; font-size:11px;\">");
	                        	out.println(avgrating);
	                        	out.println("</td></tr>");
	                            out.println("<tr class=\"myClass\" onclick=\"gotoURL('?page=tutordetails&start=1&id=" + tutorialInfo.getTutorialId() + "');\">");
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
               	  			connmpl.close();
               			   }catch(Exception e){
               				 e.printStackTrace();
               			   }
               	  	   }
               	  	   if(impl==0){
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