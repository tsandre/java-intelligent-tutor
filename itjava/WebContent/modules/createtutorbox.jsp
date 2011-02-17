<script type="text/javascript" language="javascript" src="js/main.js"></script> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
 	<tr>
		<td height="10"></td>
	</tr>
	<tr>
    	<td class="basic">
    		<table border="0" cellspacing="0" cellpadding="0" width="600" align="center">
        	<%
            	if(request.getParameter("myaction") != null && request.getParameter("myaction").equals("5")){
            		out.println("<tr><td height=\"45\" bgcolor=\"#5ba623\" style=\"color:#000\" colspan=\"3\">Your tutor was created successfully, please login or create an account to save your new tutor for future access. You may also use the tutor search feature to access your tutor anonymously.</td></tr><tr><td colspan=\"3\" height=\"35\"></td></tr>");
            	}
            %>
            <tr style="color:#fff;">
            	<td colspan="3" bgcolor="#122222">Create a Tutor</td>
            </tr>
            <tr>
            	<td width="1" bgcolor="#122222"></td>
            	<td>
					<form action="CodeSearchServlet" method="get" name="codeSearchForm" id="codeSearchForm">
  						<p>Enter a Search Term to Begin<br />  
  							<input type="text" name="query" id="query" placeholder="Enter query"/><br />
  							<input name="btnSearch" type="submit" id="btnSearch" onclick="return showProgress();" value="Generate Tutor"/>
  						</p>
						  <% 
						  String noResultClass = null;
						  if(request.getParameter("error") != null && request.getParameter("error").equals("5")){ 
							  noResultClass = "noResult";
						  }
						  else noResultClass = "hideResult";
						  %>
  						<div id="<%= noResultClass %>">* We did not find any results for the query.</div>
					</form>
					<table width="0" align="center">
						<tr>
							<td>
								<div id="divProgress">
									<img src="images/loopLoader.gif" /><br />
									Searching the web for code snippets...<br>
									Please Wait</br>
								</div>
							</td>
						</tr>
					</table>
				</td>
				<td width="1" bgcolor="#122222"></td>
			</tr>
			<tr>
				<td colspan="3" bgcolor="#122222" height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>