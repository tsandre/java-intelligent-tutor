        <table width="351" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222"></td>
            <td width="348"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" bgcolor="#3e4854" align="center"><a href="#" title="Teacher Login" class="navmain" onclick="showTeacherLogin()">Teacher Login</a></td>
                <td width="5">&nbsp;</td>
                <td height="20" align="center" bgcolor="#122222"></td>
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
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left"><span class="basic">
                <%  
                Connection conn = null;
        	    PreparedStatement pst = null;
			    PreparedStatement ucpst = null;
    		    ResultSet rs = null;
        	    try{
        	     conn = DBConnection.GetConnection();
        	     String usercheck = "SELECT firstName, lastName, school, username, email FROM teachers WHERE username = ? AND teacherID = ?";
 				 ucpst = conn.prepareStatement(usercheck);
 				 ucpst.setString(1, session.getAttribute("userName").toString());
 				 ucpst.setInt(2, Integer.parseInt(session.getAttribute("userID").toString()));
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
                <td align="left"><a href="teachers.jsp?page=accountinfo" target="_self" class="rightmenunav">Account Information</a></td>
                </tr>
              <tr>
                <td align="left"><a href="teachers.jsp?page=savedtutors" class="rightmenunav">My Tutors</a></td>
              </tr>
              <tr>
                <td align="left"><a href="teachers.jsp?page=classes" class="rightmenunav">Class Lists</a></td>
              </tr>
              <tr>
                <td align="left"><a href="logout.jsp" target="_self" class="rightmenunav">Click to logout</a></td>
              </tr>
              </table>
            
            </td>
            </tr>
          <tr>
            <td height="1" bgcolor="#3E4854"></td>
            </tr>
          </table>