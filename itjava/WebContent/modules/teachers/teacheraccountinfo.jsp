<%@ page import="itjava.model.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, itjava.data.*"%>
<table align="center"><tr><td align="center">
            <form id="form3" name="form3" method="post" action="UpdateTeacherServlet">
              <table width="450" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="1" rowspan="21" align="center" bgcolor="#122222" class="titles"></td>
                  <td colspan="3" align="center" bgcolor="#122222" class="titles"><span style="color:white; font: Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; font-family: Arial, Helvetica, sans-serif">Update Account Information</span></td>
                  <td width="1" rowspan="21" align="center" bgcolor="#122222" class="titles"></td>
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
                <%  Connection taconn = null;
        	   PreparedStatement tapst = null;
			   PreparedStatement taucpst = null;
    		   ResultSet tars = null;
        	   try{
        	     taconn = DBConnection.GetConnection();
        	     String tausercheck = "SELECT firstName, lastName, school, username, email FROM teachers WHERE username = ? AND teacherID = ?";
 				 taucpst = taconn.prepareStatement(tausercheck);
 				 taucpst.setString(1, (String) session.getAttribute("userName"));
 				 taucpst.setString(2, (String) session.getAttribute("userID"));
 				 tars = taucpst.executeQuery();
 				 tars.next();
				 out.println("<tr>");
                  out.println("<td width=\"140\" align=\"right\">First Name:</td>");
                  out.println("<td width=\"5\">&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"firstName3\" type=\"text\" class=\"inputFieldText\" id=\"firstName3\" style=\"width:140px\" value=\"" + tars.getString("firstName") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td align=\"right\">Last Name:</td>");
                  out.println("<td>&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"lastName3\" type=\"text\" class=\"inputFieldText\" id=\"lastName3\" style=\"width:140px\" value=\"" + tars.getString("lastName") + "\" /></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td height=\"4\" align=\"right\"></td>");
                  out.println("<td align=\"right\"></td>");
                  out.println("</tr>");
                out.println("<tr>");
                  out.println("<td align=\"right\">Your School:</td>");
                  out.println("<td>&nbsp;</td>");
                  out.println("<td align=\"left\"><input name=\"school3\" type=\"text\" class=\"inputFieldText\" id=\"school3\" style=\"width:200px\" value=\"" + tars.getString("school") + "\" onKeyPress=\"if(submitformbutton(this, event)){ checkForm5(); }\" /></td>");
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
        	     taconn.close();
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
                  <td align="left"><input name="passwordConfirm3" type="password" class="inputFieldText" id="passwordConfirm3" style="width:140px" onKeyPress="if(submitformbutton(this, event)){ checkForm5(); }" /></td>
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
                    <input name="subbutton3" type="button" class="basicbutton" id="button3" value="Update" style="width:125px;" onmousedown="Javascript: checkForm5();" />
                  </td>
                  </tr>
                <tr>
                  <td colspan="3" align="right">&nbsp;</td>
                </tr>
                <tr>
                  <td height="1" colspan="3" align="right" bgcolor="#122222"></td>
                  </tr>
              </table>
            </form>
            </td></tr></table>