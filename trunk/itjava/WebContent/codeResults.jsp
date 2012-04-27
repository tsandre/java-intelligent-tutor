<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.google.gson.internal.*"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*"%>
<%@ page
	import="itjava.scraper.*,itjava.presenter.*,itjava.model.*,itjava.view.*,java.util.HashMap,java.util.ArrayList,java.sql.*,java.util.Enumeration,java.security.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon"
	href="http://experiments.eecs.oregonstate.edu:8080/favicon.ico"
	type="image/x-icon" />
<title>Code Results..</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script type="text/javascript" language="javascript"
	src="js/ratingsys.js"></script>
<script type="text/javascript" src="js/jquery.syntaxhighlighter.min.js"></script>
<script type="text/javascript" language="javascript" src="js/main.js"></script>
<script src="MD5.js"></script>
<!-- Include jQuery Syntax Highlighter 
<script type="text/javascript"
	src="http://balupton.github.com/jquery-syntaxhighlighter/scripts/jquery.syntaxhighlighter.min.js"></script>
<!-- Initialise jQuery Syntax Highlighter -->
<script type="text/javascript">
	$.SyntaxHighlighter.init();
</script>
</head>

<body>
<table width="1024" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td colspan="3"><%@ include file="/modules/headers/header5.jsp"%></td>
	</tr>
	<tr>
		<td width="1" bgcolor="#122222"></td>
		<td width="1022">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="5"></td>
				<td valign="top" style="width: 522px;" align="center">
				<form action="CodeDisplayServlet" method="post"
					name="codeDisplayForm" id="codeDisplayForm"
					enctype="multipart/form-data">
				<table align="center" style="padding-top: 10px; margin: 0px;"
					cellspacing="0" cellpadding="0">
					<tr style="color: #fff;">
						<td colspan="3" bgcolor="#122222" align="center"
							style="padding: 0px; margin: 0px; color: #FFF; font-weight: bold; font-family: Arial, Helvetica, sans-serif; font-size: 12px;">Industry
						Code Results</td>
					</tr>
					<tr>
						<td
							style="width: 1px; background-color: #122222; padding: 0px; margin: 0px; height: 100px;"></td>
						<td align="center">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td>
								<%
									ArrayList<String> industryCodePaths = (ArrayList<String>) request
											.getAttribute("fileLocation");
									if (industryCodePaths.size() == 0) {
								%> There are no results found for specified query. <%
									}
									ArrayList<Pair<String, Double>> relSortedList = new ArrayList<Pair<String, Double>>();

									System.out.println(request.getAttribute("query"));
									String searchQuery = request.getAttribute("query").toString();
									for (String codePath : industryCodePaths) {
										BufferedReader input = new BufferedReader(new FileReader(
												codePath));
										String line = "";
										int occurrenceCount = 0, lineCount = 0;
										while ((line = input.readLine()) != null) {
											lineCount++;
											if (line.toLowerCase().contains(searchQuery.toLowerCase())) {
												occurrenceCount++;
											}

										}
										Double relScore = (double) (lineCount / occurrenceCount);

										Pair<String, Double> fileScore = new Pair<String, Double>(
												codePath, relScore);
										relSortedList.add(fileScore);
										input.close();
									}

									for (int i = 0; i < relSortedList.size(); i++) {
										for (int j = 0; j < relSortedList.size() - 1; j++) {
											if (relSortedList.get(j).second > relSortedList.get(j + 1).second) {
												Pair<String, Double> tempPair = relSortedList.get(j);
												relSortedList.set(j, relSortedList.get(j + 1));
												relSortedList.set(j + 1, tempPair);
											}
										}
									}
									ArrayList<String> industryCodePathsResult = new ArrayList<String>();
									for (Pair<String, Double> item : relSortedList) {
										System.out.println(item.first + ":" + item.second);
										industryCodePathsResult.add(item.first);

									}
									session.setAttribute("fileLocation", industryCodePaths);

									for (String codePath : industryCodePathsResult) {
										String fileName = codePath
												.substring(codePath.lastIndexOf('/') + 1);
								%> <br />
								<a id="link_<%=fileName.substring(0, fileName.indexOf('.'))%>"
									href="javascript: void(window.open('/itjava/codeViewer.jsp?linkIndex=<%=industryCodePaths.indexOf(codePath)%>','_newtab<%=industryCodePaths.indexOf(codePath)%>'));">File:
								<%=fileName%></a> <br />
								<pre class="language-java">					
							    <%
												    	BufferedReader input = new BufferedReader(new FileReader(
												    				codePath));
												    		String line = "";
												    		int previewLineCount = 0;
												    		while ((line = input.readLine()) != null
												    				&& previewLineCount < 3) {
												    			if (line.toLowerCase().contains(searchQuery.toLowerCase())) {
												    				out.println(line);
												    				previewLineCount++;
												    			}

												    		}
												    		input.close();
												    %> 
								</pre> <br />
								<%
									}
								%>
								</td>
							</tr>
						</table>
						</td>
						<td
							style="width: 1px; background-color: #122222; padding: 0px; margin: 0px;"></td>
					</tr>
					<tr>
						<td colspan="3" bgcolor="#122222" height="1"
							style="padding: 0px; margin: 0px;"></td>
					</tr>
				</table>
				</form>
				</td>
				<td width="5"></td>
			</tr>
			<tr>
				<td height="10" colspan="2"></td>
				<td></td>
			</tr>
		</table>
		</td>
		<td width="1" bgcolor="#122222"></td>
	</tr>
	<tr>
		<td height="1" bgcolor="#122222" colspan="3"></td>
	</tr>
</table>
</body>
</html>