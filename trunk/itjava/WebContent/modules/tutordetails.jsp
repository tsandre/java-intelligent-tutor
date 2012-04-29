<%@ page import="itjava.model.*, itjava.data.*, itjava.db.*, java.util.HashMap, java.util.ArrayList, itjava.util.*, java.sql.*, itjava.view.*, java.util.Enumeration, java.security.*"%>
<link href="css/prettify.css" type="text/css" rel="stylesheet" /><script type="text/javascript" src="js/prettify.js"></script>
<script type="text/javascript" language="javascript">
function checkanswer(ansnum){
	var givenans = document.getElementById("answer_"+ansnum).value;
	if(givenans != Edges[ansnum]){
		document.getElementById("answer_"+ansnum).style.borderColor="rgb(144,0,0)";
	}else{
		document.getElementById("answer_"+ansnum).style.borderColor="rgb(9,144,0)";
	}
}
function checkanswers(tutorID, j){
	var totalanswers = document.getElementById("totalanswers").value;
	var totalquestions = Edges.length;
	var totalcorrect = 0;
	for(var i=0; i<totalquestions; i++){
		var givenans = document.getElementById("answer_"+i).value;
		if(givenans != Edges[i]){
			document.getElementById("answer_"+i).style.borderColor="rgb(144,0,0)";
		}else{
			document.getElementById("answer_"+i).style.borderColor="rgb(9,144,0)";
			totalcorrect++;
		}
	}
	if(totalcorrect == totalquestions){
		alert("You completed this quiz successfully!");
		getnext(tutorID, j);
	}else{
		alert("You missed some! Please complete the quiz to continue.");
	}
}

function getnext(tutorID, j){
	document.forms["formMainTest"].submit();
	//window.location = "?page=tutordetails&id=" + tutorID;
}

function showHint(hintnum){
	alert(Hints[hintnum]);
}

$(document).ready(function() {
    $('a.tTip').tinyTips('title');
});

</script>
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
</script onload="prettyPrint()">
<table width="600" border="0" cellspacing="0" cellpadding="0">
	<tr>
        <td valign="top" style="padding:0px 5px 5px 5px; color: #333; font-size: 12px; font-family: Arial, Helvetica, sans-serif;">
        	<table width="600" border="0" cellspacing="0" cellpadding="0">
				<tr>
            		<td height="10"></td>
          		</tr> 
            	<tr>
            		<td align="center">
			            <%
			
						int tutorialInfoId = Integer.parseInt(request.getParameter("id"));
						
						//DeliverableLauncher deliverableLauncher = (DeliverableLauncher)session.getAttribute("deliverableLauncher");
						//if (deliverableLauncher == null) {
						//	System.err.println("Landed on studentMainTest.jsp from a wrong source..");
						//	response.sendRedirect("studentWelcome.jsp");
						//}
						//else {
						//	String studentId = session.getAttribute("studentId").toString();
						//	deliverableLauncher.setStudentId(Integer.parseInt(studentId));
						//	deliverableLauncher.setTutorialInfoId(tutorialInfoId);
						//}
						
						HashMap<String, String> whereClause = new HashMap<String, String>();
						whereClause.put("tutorialInfoId", Integer.toString(tutorialInfoId));
						session.setAttribute("tutorialInfoId", tutorialInfoId);
						ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SelectInfo(whereClause);
						
						//KeyValue<Integer, String> deliveryKeyValue = (KeyValue<Integer, String>)session.getAttribute("deliveryKeyValue");
						//int deliverableId = -1;
						//String deliverableName = null;
						//if (deliveryKeyValue == null  && request.getParameter("start").trim().equals("1")) {
						//	deliveryKeyValue = deliverableLauncher.GetFirstDeliverableName();
						//}
						//else if (deliveryKeyValue == null)
						//{
						//	response.sendRedirect("studentFinalPage.jsp");
						//}
						//deliverableId = deliveryKeyValue.getKey();
						//deliverableName = deliveryKeyValue.getValue();
						
						%>
						<form id="formMainTest" method="post" action="DeliverableSelection2Servlet">
						<div id="divMainContent">
						<table border="0" cellpadding="0" cellspacing="0" id="tableMeta" align="center"><tbody>
							<tr>
								<td>
  									<table id="tableMain" border="0" align="center" cellpadding="0"  cellspacing="0"><tbody>
  									<input type="hidden" name="userid" id="userid" value="<% out.print(session.getAttribute("userID")); %>" />
  									<input type="hidden" name="tutorid" id="tutorid" value="<% out.print(request.getParameter("id")); %>" />
  									<input type="hidden" name="userLevel" id="userLevel" value="<% out.print(session.getAttribute("userLevel")); %>" />
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
												out.println("</td><td align=\"center\" valign=\"top\" bgcolor=\"#F4F4F4\" class=\"tdBold\" width=\"90\" rowspan=\"3\">");
												if(session.getAttribute("userName") != null && session.getAttribute("userID") != null && session.getAttribute("userLevel") != null && !session.getAttribute("userLevel").equals("unknown") && !session.getAttribute("userID").equals("0")){
						                            
						                            Connection conn = null;
						                			PreparedStatement pst = null;
						                			PreparedStatement pst2 = null;
						                			ResultSet rs = null;

						                		   try
						                		   { 
						                			    conn = DBConnection.GetConnection();
						                			    String sql = "Select user_tutor_id FROM UserTutors WHERE userID = ? AND tutorID = ? AND userLevel = ?";
						                			    pst = conn.prepareStatement(sql);
					                					pst.setString(1, session.getAttribute("userID").toString());
					                					pst.setString(2, request.getParameter("id").toString());
					                					pst.setString(3, session.getAttribute("userLevel").toString());
						                				rs = pst.executeQuery();
						                				if(rs.next()){
						                					out.println("<div id=\"addtofavoritesgroup\" style=\"display:none\"><a href=\"Javascript: addtofavorites();\"><img src=\"images/addtofavorite.png\" width=\"50\" height=\"50\" border=\"0\" /></a><br>Add To Favorites</div><div id=\"favoritedgroup\"><img src=\"images/addtofavorite.png\" width=\"50\" height=\"50\" border=\"0\" /><br>Favorited!</div>");
						                				}else{
						                					out.println("<div id=\"addtofavoritesgroup\"><a href=\"Javascript: addtofavorites();\"><img src=\"images/addtofavorite.png\" width=\"50\" height=\"50\" border=\"0\" /></a><br>Add To Favorites</div><div id=\"favoritedgroup\" style=\"display:none\"><img src=\"images/addtofavorite.png\" width=\"50\" height=\"50\" border=\"0\" /><br>Favorited!</div>");
						                				}
						                		   }
						                		   catch(Exception e) {
						                      	     e.printStackTrace();
						                      	   }
						                      	   finally {
						                      	     conn.close();
						                      	   }	
												
												}
									            out.println("</td></tr>");
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
									            out.print("<td colspan=\"4\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\"><br />");
												out.print(tutorialInfo.getTutorialDescription());
												out.println("</td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td height=\"5\" colspan=\"4\" bgcolor=\"#F4F4F4\" style=\"padding-left:10px;\"></td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td height=\"1\" colspan=\"4\" style=\"padding-left:10px;\" bgcolor=\"#F4F4F4\"></td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td height=\"5\" colspan=\"4\" bgcolor=\"#F4F4F4\" style=\"padding-left:10px;\"></td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td colspan=\"4\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\">");
									           // out.println("<input type=\"submit\" id=\"btnLaunch\" name=\"btnLaunch\" value=\"Begin Lesson\" class=\"tdRegular\" onclick=\"return launchNext('ProcessBuilderinjava', 'Example1');\" />");
									            out.println("</td>");
									            out.println("</tr>");
									            out.println("<tr>");
									            out.println("<td colspan=\"4\" bgcolor=\"#F4F4F4\" class=\"tdRegular\" style=\"padding-left:10px;\" height=\"10px\"></td>");
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
										String tutorId = request.getParameter("id").toString();
										String folderName = tutorialInfo.getFolderName();
										String buttonLabel = "Begin Lesson";
										
										
										
								
									
										//String disabledLaunch = "";
										//if (deliverableName == null) {
										//	disabledLaunch = "disabled";
										//}
										//else {
										//	session.setAttribute("deliveryKeyValue", deliveryKeyValue);
										//	session.setAttribute("tutorialInfoId", tutorialInfoId);
										//	session.setAttribute("studentId", 99);
										//	session.setAttribute("deliverableLauncher", deliverableLauncher);
										//}
										
										
										%>
								</tbody></table>
							</td>
						</tr>
					</tbody></table>
					<table border="0" cellspacing="0" cellpadding="0" align="center"><tr><td width="600">
					<%
					String messageToUser = null;
					//if (deliverableName.contains("Example")) {
						//messageToUser = "<b>Example Tutorial Instructions</b><br/><i>Read through it and try to understand the code.</i><br/><i>Key API related methods and its usages are italicized.</i><br/><i>Dont forget to click Done and close the window once you read through it.</i><br/><i>If prompted to save, click Yes.</i><br/><i>Click Next button on the browser page to fetch the next tutorial for the topic.</i><br/>";
					//}
					//else if (deliverableName.contains("Quiz")) {
						messageToUser = "<b>Quiz Instruction</b><br/><i>Blanks appear in between the code. Fill in the blanks with appropriate code and click Done.</i><br/><i>If the answer you entered in the blank is correct, text box turns green in color.</i><br/><i>If the answer is wrong, the blank turns red in color. In this case, you can use hints and try re-typing the answer. (Info reg. using hints is given below).</i><br/><i>Fill in all the blanks in the Quiz. Click DONE to advance to the next step in the jTutor.<br/><i>The final hint is always answer to be filled in the blank. </i><br/><br/><b>Example Instructions</b><br/><i>Read through the example code snippet and click DONE when you feel you have understood the snippet. The Quiz that follows the example might be related to the example snippet, so read carefully!</i><br/><br/>";
					//}
					%>
					</td></tr></table>
					<div id="divHelpMessage" class="divHelpMessage"><%= messageToUser %></div>
					<input type="button" onclick="window.open('tutorViewer.jsp?tutorID=<%= request.getParameter("id").toString() %>&pagenum=Example-1&nextQuiz=1&nextExample=1')" value="Launch jTutor" />
					<table></table>
					</div>
				</form>
			</td>
    	</tr>
	</table>
</td>
</tr>
</table>