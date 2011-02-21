<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, itjava.model.*, org.eclipse.jdt.core.dom.Message;"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<style>
body {
	font-family: segoe ui, verdana;
}
div {
	border: 0px solid gray;
}
td {
	padding: 5px;
	vertical-align:top;
}

#divWrapperMain {
	width: 1024px;
	margin: 0px auto;
}
#divSubmit {
	width: 500px;
	margin: 0px auto;
	text-align: center;
}
#divMessages {
	background-color: beige;
	font-family: Consolas;
	font-size: 0.75em;
	padding: 3px;
}
textarea {
	font-family: Consolas;
	font-size:0.8em;
}
</style>
</head>

<body>
<%
CompilationUnitFacade facade = (CompilationUnitFacade)session.getAttribute("facade");
String url = facade.getUrl();
%>

<div id="divWrapperMain">
<form id="formEdit" method="post" action="EditSnippetServlet">
<div style="display:none;">
<input type="text" name="url" id="url" value="<%out.print(url);%>"/>
</div>
<span style="font-size: 0.9em; color: blue;">
To read more about this snippet visit: 
<a href="
<% out.println(url.replaceFirst("^\\d*", "")); %>
" target="_blank" >^ link.</a>
</span>
<table>
<tbody>
<tr>
<td>
<div id="divEditCode">
<textarea id="taEdit" name="taEdit" rows="30" cols="70">
<%
for (String currLine : facade.getLinesOfCode()) {
	out.println(currLine);
}
%>
</textarea>
</div>
</td>
<td>
<input type="submit" id="btnCompile" name="btnSubmit" value="Compile" />
</td>
<td width="100%">
<span style="background-color: #e0e9f2;"> Compiler messages..</span>
<div id="divMessages">
<hr />
<%
for(Message msg : facade.getMessages()) {
	out.println("<br />At char: " + msg.getStartPosition() + "::" + msg.getMessage());
}
if (facade.getMessages().length == 0) {
	out.println("All is well!!");
}
%>
</div>
</td>
</tr>
</tbody>
</table>
<div id="divSubmit">
<input type="submit" id="btnSubmitFinal" name="btnSubmit" value="Confirm Changes" />
<input type="submit" id="btnCancel" name="btnSubmit" value="Cancel Changes" />
<% 
String error = request.getParameter("error");
String display = "none";
if (error != null && error.equals("1")) {
	display = "block";
}
%>
<span id="errorMessage" style="color:red; display: <%= display%>">*Please rectify errors before confirming.</span>
</div>
</form>
</div>
</body>
</html>