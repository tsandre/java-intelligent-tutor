<div id="teacherLogin" name="teachersLogin">
        <table width="250" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="10" colspan="3"></td>
            </tr>
          <tr>
            <td bgcolor="#122222" width="1"></td>
            <td width="248"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="140" bgcolor="#122222" align="center"><a href="#" title="Login" class="navmain" onclick="showTeacherLogin()">Login</a></td>
                <td width="5">&nbsp;</td>
                <td height="20"></td>
                <td>&nbsp;</td>
                </tr>
              </table></td>
            <td width="1"></td>
            </tr>
          <tr>
            <td width="1" rowspan="3" bgcolor="#122222"></td>
            <td height="5" bgcolor="#122222"></td>
            <td width="1" rowspan="3" bgcolor="#122222"></td>
            </tr>
          <tr>
            <td height="100" style="padding: 5px 5px 5px 5px; vertical-align:top; text-align: right; font-family: Arial, Helvetica, sans-serif; color: #333; font-size: 12px;">
           
            
            <form id="form2" name="form2" method="post" action="LoginTeacherServlet">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <% if(request.getParameter("error") != null && request.getParameter("error").equals("4")){ %><tr>
                  <td align="center">*Login failed. Please try again.</td>
                </tr><br>
				<% } %>
                
                <tr>
                  <td height="5" align="center"></td>
                </tr>
                <tr>
                  <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
       					<td width="110" align="right" class="basic" style="text-align:right;">I am a:</td>
       					<td width="5">&nbsp;</td>
       					<td align="left"><%@ include file="/modules/headers/userlevelselect2.jsp" %></td>
     				</tr>
                    <tr>
                      <td width="110" align="right" class="basic">Username:</td>
                      <td width="5">&nbsp;</td>
                      <td align="left"><input name="username2" type="text" class="basic1" id="username2" style="width:140px" /></td>
                    </tr>
                    <tr>
                      <td align="right" class="basic">Password:</td>
                      <td>&nbsp;</td>
                      <td align="left"><input name="password2" type="password" class="basic1" id="password2" style="width:140px" onKeyPress="if(submitformbutton(this, event)){ checkForm2(); }" /></td>
                    </tr>
                    <tr>
                      <td height="5" colspan="3" align="right"></td>
                      </tr>
                    <tr>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td align="left">
                      <input name="button3" type="button" class="basicbutton" id="button3" value="Login" style="width:125px;" onmousedown="Javascript: checkForm2();" /></td>
                    </tr>
                    <tr>
                      <td align="right">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
       					<td align="center" colspan="3"></td>
     				</tr>
     				<tr>
       					<td align="center" colspan="3"><a href="teachers.jsp" class="mostpopular">Create Account</a></td>
     				</tr>
                  </table></td>
                </tr>
              </table>
            </form>
            </td>
            </tr>
          <tr>
            <td height="1" bgcolor="#122222"></td>
            </tr>
          </table>
          </div>