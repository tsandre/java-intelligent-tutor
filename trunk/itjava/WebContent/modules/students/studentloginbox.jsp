<table width="351" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="10" colspan="3"></td>
		</tr>
		<tr>
			<td bgcolor="#122222"></td>
            <td width="348">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
                		<td width="140" height="20" align="center" bgcolor="#122222"><a href="#" title="Student Login" class="navmain" onclick="showStudentLogin()">Student Login</a></td>
                		<td width="5">&nbsp;</td>
                		<td></td>
                		<td>&nbsp;</td>
                	</tr>
            	</table>
            </td>
            <td width="2"></td>
		</tr>
		<tr>
			<td width="1" rowspan="3" bgcolor="#122222"></td>
            <td height="5" bgcolor="#122222"></td>
            <td width="2" rowspan="3" bgcolor="#122222"></td>
		</tr>
		<tr>
            <td height="100" style="padding: 5px 5px 5px 5px; vertical-align:top; text-align: right; font-family: Arial, Helvetica, sans-serif; color: #333; font-size: 12px;">
            	<form id="form3" name="form3" method="post" action="LoginStudentServlet">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                		<% if(request.getParameter("error") != null && request.getParameter("error").equals("4")){ %>
                		<tr>
							<td align="center">*Login failed. Please try again.</td>
						</tr>
						<% } %>
                		<tr>
							<td align="center"><span class="basic" style="font-weight:bold">Student Login</span></td>
                		</tr>
                		<tr>
                  			<td height="5" align="center"></td>
                		</tr>
                		<tr>
                  			<td align="center">
                  				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                   	 				<tr>
                      					<td width="110" align="right" class="basic" style="text-align:right;">Username:</td>
                      					<td width="5">&nbsp;</td>
                      					<td align="left"><input name="username3" type="text" class="basic1" id="username3" style="width:140px" /></td>
                    				</tr>
                    				<tr>
                      					<td align="right" class="basic" style="text-align:right;">Password:</td>
                      					<td>&nbsp;</td>
                      					<td align="left"><input name="password3" type="password" class="basic1" id="password3" style="width:140px" onKeyPress="if(submitformbutton(this, event)){ checkForm3(); }" /></td>
                    				</tr>
                    				<tr>
                      					<td height="5" colspan="3" align="right"></td>
                      				</tr>
                    				<tr>
                      					<td align="right">&nbsp;</td>
                      					<td>&nbsp;</td>
                      					<td align="left"><input name="button4" type="button" class="basicbutton" id="button4" value="Login" style="width:125px;" onmousedown="Javascript: checkForm3();" /></td>
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
                      					<td align="center" colspan="3"><a href="students.jsp" class="mostpopular">Create Account</a></td>
                    				</tr>
                  				</table>
                  			</td>
						</tr>
              		</table>
            	</form>
            </td>
		</tr>
		<tr>
			<td height="1" bgcolor="#122222"></td>
		</tr>
</table>