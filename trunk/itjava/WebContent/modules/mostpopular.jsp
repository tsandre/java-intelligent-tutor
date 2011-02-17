<table width="350" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="10" colspan="3"></td>
	</tr>
 	<tr>
 		<td bgcolor="#122222"></td>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="20" align="left" bgcolor="#122222" style="padding-left:5px; color: #FFF; font-weight: bold; font-family: Arial, Helvetica, sans-serif; font-size: 12px;">Most Popular</td>
                </tr>
			</table>
		</td>
        <td width="1" bgcolor="#122222"></td>
	</tr>
		<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
		<%
            ArrayList<TutorialInfo> tutorialInfoList = (ArrayList<TutorialInfo>) session.getAttribute("tutorialInfoList");
            session.setAttribute("studentId", session.getAttribute("userID"));
            DeliverableLauncher launcher = new DeliverableLauncher();
            session.setAttribute("deliverableLauncher", launcher);
		%>
		<%
        Connection mpconn = null;
  		PreparedStatement mpucpst = null;
  		ResultSet mprs = null;
  		mpconn = DBConnection.GetConnection();		
        String getmostpopular = "SELECT TutorialInfo.*, ROUND(AVG(TutorRatings.rating), 2) as avgrating  FROM TutorialInfo, TutorRatings, TutorRatings as ratings2 WHERE TutorRatings.tutorId = TutorialInfo.tutorialInfoId GROUP BY TutorialInfo.tutorialInfoId ORDER BY avgrating DESC LIMIT 20";
        mpucpst = mpconn.prepareStatement(getmostpopular);
        mprs = mpucpst.executeQuery();
	  	while(mprs.next()){
	  		out.println("<tr>");
            out.println("<td width=\"1\" bgcolor=\"#122222\"></td>");
            out.println("<td bgcolor=\"#FFFFFF\" style=\"padding-left:10px; height:24px\"><a href=\"tutorSearchResultsDetails.jsp?start=1&id=" + mprs.getString("tutorialInfoId") + "\" class=\"mostpopular\">" + mprs.getString("tutorialName") + "</a></td>");
            out.println("<td width=\"1\" bgcolor=\"#122222\"></td>");
            out.println("</tr><tr><td colspan=\"3\" bgcolor=\"#122222\" height=\"1\"></td></tr>");
	  	}
    	%>
 	<tr>
		<td width="1" rowspan="3" bgcolor="#122222"></td>
		<td height="5" bgcolor="#122222"></td>
		<td width="1" rowspan="3" bgcolor="#122222"></td>
	</tr>     
	<tr>
		<td height="1" bgcolor="#122222" colspan="3"></td>
	</tr>
 </table>