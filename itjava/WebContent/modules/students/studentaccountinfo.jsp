<%@ page import="itjava.model.*, itjava.data.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<table width="250" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10"></td>
          </tr>
          <%
          out.println("<tr><td align=\"center\">");
            out.println("<form id=\"form3\" name=\"form3\" method=\"post\" action=\"UpdateStudentServlet\">");
            out.println("<table width=\"450\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            out.println("<tr>");
            out.println("<td width=\"1\" rowspan=\"21\" align=\"center\" bgcolor=\"#3E4854\" class=\"titles\"></td>");
            out.println("<td colspan=\"3\" align=\"center\" bgcolor=\"#122222\" class=\"titles\"><span style=\"color:white; font: segoe ui, verdana; font-size:12px; font-weight:bold;\">Update Account Information</span></td>");
            out.println("<td width=\"1\" rowspan=\"21\" align=\"center\" bgcolor=\"#3E4854\" class=\"titles\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td height=\"4\" align=\"right\"></td>");
            out.println("<td height=\"4\" align=\"right\"></td>");
            out.println("<td align=\"right\"></td>");
            out.println("</tr> ");
            if(request.getParameter("error") != null){
				out.print("<tr><td align=\"left\" colspan=\"3\" style=\"padding:5px 5px 5px 5px\">");
				if(request.getParameter("error").equals("3") || request.getParameter("error").equals("1")){ 
					out.print("<p>*The username you have selected all ready exists. Please be sure to check the availability before submitting.</p>");
            	} 
				if(request.getParameter("error").equals("3") || request.getParameter("error").equals("2")){
            		out.print("<p>*The email you have used is already connected to another account. To recover your password <a href=\"teacherPasswordRecover.jsp\" title=\"click here\" target=\"_self\">click here</a>.</p>");
				} 
				out.print("</td></tr>");
			} 
			if(request.getParameter("success") != null){
                  	out.print("<tr><td align=\"left\" colspan=\"3\" style=\"padding:5px 5px 5px 5px\"><p>*Your information has been updated successfully!</p></td></tr>");
			}
			out.println("<tr>");
			out.println("<td align=\"right\">&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("<td align=\"left\">&nbsp;</td>");
			out.println("</tr>");
                	
			Connection saconn = null;
			PreparedStatement saucpst = null;
    		ResultSet sars = null;
        	
    		try{
        	     saconn = DBConnection.GetConnection();
        	     String usercheck = "SELECT firstName, lastName, school, username, email FROM students WHERE username = ? AND studentID = ?";
        	     saucpst = saconn.prepareStatement(usercheck);
        	     saucpst.setString(1, (String) session.getAttribute("userName"));
        	     saucpst.setInt(2, (Integer) session.getAttribute("userID"));
        	     sars = saucpst.executeQuery();
        	     sars.next();
				 out.println("<tr>");
                  out.println("<td width=\"140\" class=\"basic\" style=\"text-align:right\">First Name:</td>");
                  out.println("<td width=\"5\">&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"firstName3\" type=\"text\" class=\"inputFieldText\" id=\"firstName3\" style=\"width:140px\" value=\"" + sars.getString("firstName") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td class=\"basic\" style=\"text-align:right\">Last Name:</td>");
                  out.println("<td>&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"lastName3\" type=\"text\" class=\"inputFieldText\" id=\"lastName3\" style=\"width:140px\" value=\"" + sars.getString("lastName") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td class=\"basic\" style=\"text-align:right\">Your School:</td>");
                  out.println("<td>&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"school3\" type=\"text\" class=\"inputFieldText\" id=\"school3\" style=\"width:200px\" value=\"" + sars.getString("school") + "\" onKeyPress=\"if(submitformbutton(this, event)){ checkForm4(); }\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
				}
        	   catch(Exception e) {
        	     e.printStackTrace();
        	   }
        	   finally {
        		   saconn.close();
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
                  <td class="basic" style="text-align:right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">Change Password: (Optional)</td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td class="basic" style="text-align:right">Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="password3" type="password" class="inputFieldText" id="password3" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Confirm Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="passwordConfirm3" type="password" class="inputFieldText" id="passwordConfirm3" style="width:140px" onKeyPress="if(submitformbutton(this, event)){ checkForm4(); }" /></td>
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
                    <input name="subbutton3" type="button" class="basicbutton" id="button3" value="Update" style="width:125px;" onmousedown="Javascript: checkForm4();" />
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
            
        </table>