<%@ page import="itjava.model.*, itjava.data.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<script language="javascript" type="text/javascript">
var launchCounts = 0;
function launchNext(folderName, deliverableName) {
	if (launchCounts == 0) {
		launchCounts++;
		window.open("delivery/" + folderName + "/" + deliverableName + ".jnlp");
		document.getElementById("btnLaunch").value = "Save & Next >>";
		document.getElementById("alternateLaunch").style.display = 'block';
		return false;
	}
	else {
		launchCounts = 0;
		return true;
	}
}

function launchNow(folderName, deliverableName) {
	window.open("delivery/" + folderName + "/" + deliverableName + ".jnlp");
	launchCounts = 1;
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
        <td valign="top" style="padding:0px 5px 5px 5px; color: #333; font-size: 12px; font-family: Arial, Helvetica, sans-serif;">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
            		<td height="10"></td>
          		</tr> 
            	<tr>
            		<td align="center">
			            <%
			
						int tutorialInfoId = Integer.parseInt(request.getParameter("id"));
						
						DeliverableLauncher deliverableLauncher = (DeliverableLauncher)session.getAttribute("deliverableLauncher");
						if (deliverableLauncher == null) {
							System.err.println("Landed on studentMainTest.jsp from a wrong source..");
							response.sendRedirect("studentWelcome.jsp");
						}
						else {
							String studentId = session.getAttribute("studentId").toString();
							deliverableLauncher.setStudentId(Integer.parseInt(studentId));
							deliverableLauncher.setTutorialInfoId(tutorialInfoId);
						}
						
						HashMap<String, String> whereClause = new HashMap<String, String>();
						whereClause.put("tutorialInfoId", Integer.toString(tutorialInfoId));
						session.setAttribute("tutorialInfoId", tutorialInfoId);
						ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(whereClause);
						
						KeyValue<Integer, String> deliveryKeyValue = (KeyValue<Integer, String>)session.getAttribute("deliveryKeyValue");
						int deliverableId = -1;
						String deliverableName = null;
						if (deliveryKeyValue == null  && request.getParameter("start").trim().equals("1")) {
							deliveryKeyValue = deliverableLauncher.GetFirstDeliverableName();
						}
						else if (deliveryKeyValue == null)
						{
							response.sendRedirect("studentFinalPage.jsp");
						}
						deliverableId = deliveryKeyValue.getKey();
						deliverableName = deliveryKeyValue.getValue();
						%>
						<form id="formMainTest" method="post" action="DeliverableSelection2Servlet">
						<div id="divMainContent">
						<table border="0" cellpadding="0" cellspacing="0" id="tableMeta" align="center"><tbody>
							<tr>
								<td>
  									<table id="tableMain" border="0" align="center" cellpadding="0"  cellspacing="0"><tbody>
									      <%
											TutorialInfo tutorialInfo = new TutorialInfo();
											if (tutorialInfoList.size() != 1) {
												System.err.println("TutorialInfo table should have returned only 1 tuple..");
												out.println("Error seeking Tutorial Information..");
											}
											else {
												tutorialInfo = tutorialInfoList.get(0);
												int timesAccessed = tutorialInfo.getTimesAccessed() + 1;
												TutorialInfoStore.UpdateInfo(tutorialInfoId, 
														new KeyValue<String, String>("timesAccessed", Integer.toString(timesAccessed)));
									      
									      		out.println("<tr>");
									        	out.println("<td height=\"1\" colspan=\"3\" bgcolor=\"#333333\"></td>");
									      		out.println("</tr>");
									      		out.println("<tr>");
									        	out.println("<td width=\"1\" bgcolor=\"#333333\"></td>");
									        	out.println("<td width=\"600\">");
									          	out.println("<table width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
									            out.println("<tbody>");
									            out.println("<tr>");
									            out.print("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdBold\" style=\"padding-left:10px;\">");
												out.print(tutorialInfo.getTutorialName());
												out.println("</td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.print("<td bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\" >Created by : ");
												out.print(tutorialInfo.getCreatedBy());
												out.println("</td>");
									            out.print("<td bgcolor=\"#F4F4F4\" class=\"tdRegular\">Date : ");
												out.print(tutorialInfo.getCreationDate().toString());
												out.println("</td>");
									            out.print("<td bgcolor=\"#F4F4F4\" class=\"tdRegular\">Downloads : ");
												out.print(tutorialInfo.getTimesAccessed());
												out.println("</td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.print("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\"><br />");
												out.print(tutorialInfo.getTutorialDescription());
												out.println("</td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td height=\"5\" colspan=\"3\" bgcolor=\"#F4F4F4\" style=\"padding-left:10px;\"></td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td height=\"1\" colspan=\"3\" style=\"padding-left:10px;\" bgcolor=\"#F4F4F4\"></td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td height=\"5\" colspan=\"3\" bgcolor=\"#F4F4F4\" style=\"padding-left:10px;\"></td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\">");
									           // out.println("<input type=\"submit\" id=\"btnLaunch\" name=\"btnLaunch\" value=\"Begin Lesson\" class=\"tdRegular\" onclick=\"return launchNext('ProcessBuilderinjava', 'Example1');\" />");
									            out.println("</td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td colspan=\"3\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\" height=\"10px\"></td>");
									            out.println("</tr>");
									            out.println("</tbody>");
									          	out.println("</table>");
									        	out.println("</td>");
									        	out.println("<td width=\"1\" bgcolor=\"#333333\"></td>");
									      		out.println("</tr>");
									      		out.println("<tr>");
									        	out.println("<td colspan=\"3\" bgcolor=\"#333333\" height=\"1\"></td>");
									      		out.println("</tr>");
										}
										String folderName = tutorialInfo.getFolderName();
										String buttonLabel = "Begin Lesson";
									
										String disabledLaunch = "";
										if (deliverableName == null) {
											disabledLaunch = "disabled";
										}
										else {
											session.setAttribute("deliveryKeyValue", deliveryKeyValue);
											session.setAttribute("tutorialInfoId", tutorialInfoId);
											session.setAttribute("studentId", 99);
											session.setAttribute("deliverableLauncher", deliverableLauncher);
										}
										%>
								</tbody></table>
							</td>
						</tr>
					</tbody></table>
					<%
					String messageToUser = null;
					if (deliverableName.contains("Example")) {
						messageToUser = "<b>Example Tutorial Instructions</b><br/><i>Read through it and try to understand the code.</i><br/><i>Key API related methods and its usages are italicized.</i><br/><i>Dont forget to click Done and close the window once you read through it.</i><br/><i>If prompted to save, click Yes.</i><br/><i>Click Next button on the browser page to fetch the next tutorial for the topic.</i><br/>";
					}
					else if (deliverableName.contains("Quiz")) {
						messageToUser = "<b>Quiz Instruction</b><br/><i>Blanks appear in between the code. Fill in the blanks with appropriate code and click Done.</i><br/><i>If the answer you entered in the blank is correct, text box turns green in color.</i><br/><i>If the answer is wrong, the blank turns red in color. In this case, you can use hints and try re-typing the answer. (Info reg. using hints is given below).</i><br/><i>Fill in all the blanks in the Quiz similarly. Click DONE and close the tutor window. When prompted to Save, click Yes.</i><br/><br/><b>Using hints in a Quiz:</b><br/><i>If you are NOT sure about what code to fill in the blank, click Hint button in the right pane. Use the arrow keys beside it to toggle between different hints. The lesser the hints you use, the higher is your knowledge in the topic.</i><br/><i>The final hint is usually answer to be filled in the blank.</i><br/>";
					}
					%>
					<div id="divHelpMessage" class="divHelpMessage"><%= messageToUser %></div>
					<table id="divContainer" align="center"><tbody>
						<tr>
							<td>
							<div id="divNavigate">
							<input type="submit" id="btnLaunch" name="btnLaunch" value="<%= buttonLabel%>" 
							onclick="return launchNext('<%= folderName%>', '<%= deliverableName%>');"
							<%= disabledLaunch%>/>
							</div>
							<div id="alternateLaunch">
							If you closed the pop-up by mistake, click on this <input type="button" value="button" onclick="return launchNow('<%= folderName%>', '<%= deliverableName%>');"/> to re-take the quiz.
							</div>
							</td>
						</tr>
					</tbody></table>
					</div>
				</form>
			</td>
    	</tr>
	</table>
</td>
</tr>
</table>