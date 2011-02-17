<table width="351" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222"></td>
            <td width="348"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" height="20" align="center" bgcolor="#122222"><a href="#" title="Student Login" class="navmain" onclick="showStudentLogin()">Students</a></td>
                <td width="5">&nbsp;</td>
                <td align="center"></td>
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
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
              
                <td align="left">
                <span class="basic">Welcome back <% Connection siconn = null;
			PreparedStatement sipst = null;
			PreparedStatement siucpst = null;
			ResultSet sirs = null;
			try{
				siconn = DBConnection.GetConnection();
				String usercheck = "SELECT firstName, lastName, school, username, email FROM students WHERE username = ? AND studentID = ?";
				siucpst = siconn.prepareStatement(usercheck);
				siucpst.setString(1, session.getAttribute("userName").toString());
				siucpst.setInt(2, Integer.parseInt(session.getAttribute("userID").toString()));
				sirs = siucpst.executeQuery();
				sirs.next();
				out.print(sirs.getString("firstName"));
			}catch(Exception e) {
       	     e.printStackTrace();
     	   }
     	   finally {
     	     siconn.close();
     	   }
			%>!</span></td>
                </tr>
              <tr>
                <td>&nbsp;</td>
                </tr>
              <tr>
                <td align="left"><a href="students.jsp?page=accountinfo" target="_self" class="rightmenunav">Account Information</a></td>
                </tr>
              <tr>
                <td align="left"><a href="students.jsp?page=savedtutors" class="rightmenunav">My Tutors</a></td>
              </tr>
              <tr>
                <td align="left"><a href="students.jsp?page=classes" class="rightmenunav">Class Lists</a></td>
              </tr>
              <tr>
                <td align="left"><a href="logout.jsp" target="_self" class="rightmenunav">Click to logout</a></td>
              </tr>
              </table>
            
            </td>
            </tr>
          <tr>
            <td height="1" bgcolor="#122222"></td>
            </tr>
          </table>