<script type="text/javascript" language="javascript" src="js/main.js"></script> 
<table width="250" border="0" cellspacing="0" cellpadding="0">
 	<tr>
		<td height="10"></td>
	</tr>
	<tr>
    	<td class="basic">
    		<table border="0" cellspacing="0" cellpadding="0" width="250" align="center">
	            <tr style="color:#fff;">
	            	<td colspan="3" bgcolor="#122222">Create a Tutor From a Common Term</td>
	            </tr>
	            <tr>
		            <td style="width:1px; background-color:#122222"></td>
		            <td align="justify" style="padding: 5px;" width="248">
		            <%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
					<%
			        Connection ctconn = null;
			  		PreparedStatement ctucpst = null;
			  		ResultSet ctrs = null;
			  		ctconn = DBConnection.GetConnection();		
			        String getcommonterms = "SELECT ClassInstanceTerms.* FROM ClassInstanceTerms ORDER BY numOccurrences DESC LIMIT 30";
			        ctucpst = ctconn.prepareStatement(getcommonterms);
			        ctrs = ctucpst.executeQuery();
			        int sizenum = 1;
				  	while(ctrs.next()){
			            out.println("<a href=\"CodeSearchServlet?query=" + ctrs.getString("term") + "&btnSearch=Generate+Tutor\" class=\"mostpopular" + sizenum + "\" onclick=\"showProgress()\">" + ctrs.getString("term") + "</a> ");
				  		if(sizenum == 5){
				  			sizenum = 1;
				  		}else{
				  			sizenum++;
				  		}
				  	}
			    	%>
					</td>
					<td style="width:1px; background-color:#122222"></td>
				</tr>
				<tr>
					<td colspan="3" bgcolor="#122222" height="1"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>