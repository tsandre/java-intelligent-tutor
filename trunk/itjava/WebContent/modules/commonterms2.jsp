<script type="text/javascript" language="javascript" src="js/main.js"></script> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
 	<tr>
		<td height="10"></td>
	</tr>
	<tr>
    	<td class="basic">
    		<table border="0" cellspacing="0" cellpadding="0" width="600" align="center">
            <tr style="color:#fff;">
            	<td colspan="3" bgcolor="#122222">Common Search Terms</td>
            </tr>
            <%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
			<%
	        Connection ctconn = null;
	  		PreparedStatement ctucpst = null;
	  		ResultSet ctrs = null;
	  		ctconn = DBConnection.GetConnection();		
	        String getcommonterms = "SELECT ClassInstanceTerms.* FROM ClassInstanceTerms ORDER BY numOccurrences DESC LIMIT 35";
	        ctucpst = ctconn.prepareStatement(getcommonterms);
	        ctrs = ctucpst.executeQuery();
		  	while(ctrs.next()){
		  		out.println("<tr>");
	            out.println("<td width=\"1\" bgcolor=\"#122222\"></td>");
	            out.println("<td bgcolor=\"#FFFFFF\" style=\"padding-left:10px; height:24px\"><a href=\"TutorSearchServlet?query=" + ctrs.getString("term") + "&btnSearch=Generate+Tutor\" class=\"mostpopular\" onclick=\"showProgress()\">" + ctrs.getString("term") + "</a></td>");
	            out.println("<td width=\"1\" bgcolor=\"#122222\"></td>");
	            out.println("</tr><tr><td colspan=\"3\" bgcolor=\"#122222\" height=\"1\"></td></tr>");
		  	}
	    	%>
			
			<tr>
				<td colspan="3" bgcolor="#122222" height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>