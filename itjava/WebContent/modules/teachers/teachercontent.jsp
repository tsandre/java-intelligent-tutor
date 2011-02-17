<table>
<tr>
            <td class="titles">Welcome Teachers!</td>
          </tr>
          <tr>
            <td class="basic">This website will help you create intelligent tutors which can be distributed to your students. They will be able to learn the Java API material more quickly and easily than they can with the traditional example based methods. Please take time to register with us before creating your first tutor. This will help you keep track of your tutors and make it possible to set up online classrooms. This will make the distribution of material to your students quick and easy. Lets get started, please fill out the form below to begin.</td>
          </tr>
          <tr>
            <td class="basic">&nbsp;</td>
          </tr>
          <tr>
            <td align="center">
            
            <form id="form1" name="form1" method="post" action="CreateTeacherServlet">
              <table width="450" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="1" rowspan="21" align="center" bgcolor="#3E4854" class="titles"></td>
                  <td colspan="3" align="center" bgcolor="#3E4854" class="titles"><span style="color:white; font:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold;">Sign Up Now!</span></td>
                  <td width="1" rowspan="21" align="center" bgcolor="#3E4854" class="titles"></td>
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
                
                <tr>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td align="left">&nbsp;</td>
                </tr>
                <tr>
                  <td width="140" align="right">First Name:</td>
                  <td width="5">&nbsp;</td>
                  <td align="left"><input name="firstName" type="text" class="inputFieldText" id="firstName" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Last Name:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="lastName" type="text" class="inputFieldText" id="lastName" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Your School:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="school" type="text" class="inputFieldText" id="school" style="width:200px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Email Address:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="email" type="text" class="inputFieldText" id="email" style="width:200px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Username:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="username" type="text" class="inputFieldText" id="username" style="width:140px" /> <label>
                    <input type="button" name="button2" class="basicbutton" id="button2" value="Check Availability" onmousedown="Javascript: checkAvailability();" />
                  </label></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="password" type="password" class="inputFieldText" id="password" style="width:140px" /></td>
                  </tr>
                <tr>
                  <td height="4" align="right"></td>
                  <td height="4" align="right"></td>
                  <td align="right"></td>
                  </tr>
                <tr>
                  <td align="right">Confirm Password:</td>
                  <td>&nbsp;</td>
                  <td align="left"><input name="passwordConfirm" type="password" class="inputFieldText" id="passwordConfirm" style="width:140px" onKeyPress="if(submitformbutton(this, event)){ checkForm(); }" /></td>
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
                    <input name="subbutton" type="button" class="basicbutton" id="button" value="Join" style="width:125px;" onmousedown="Javascript: checkForm();" />
                  </td>
                  </tr>
                <tr>
                  <td colspan="3" align="right">&nbsp;</td>
                </tr>
                <tr>
                  <td height="1" colspan="3" align="right" bgcolor="#3E4854"></td>
                  </tr>
              </table>
            </form></td>
          </tr>
          </table>