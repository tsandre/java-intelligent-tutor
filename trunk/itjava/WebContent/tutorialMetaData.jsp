<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Enter Tutorial description</title>
<script type="text/javascript">
function showProgress() {
	document.getElementById("divProgress").style.display = 'block';
}
</script>
<style>
#divProgress {
	display: none;
	width: 200px;
	margin:0 auto;
	position: relative;
}
body {
	font-family: segoe ui, verdana;
}
</style>
</head>
<body>
<form id="formTutorialMetaData" name="formTutorialMetaData" action="TutorialDeliveryServlet" 
	method="post">
<pre>
Tutorial Name        : <input type="text" id="txtTutorialName" name="txtTutorialName" />
Tutorial Description : <textarea rows="5" cols="20" id="txtTutorialDescription" name="txtTutorialDescription"></textarea>
<input type="submit" id="submitMetaData" name="submitMetaData" value="Save Info" onclick="return showProgress();"/>
</pre>
</form>
<div id="divProgress">
<img src="images/loopLoader.gif" /><br />
Creating deliverables..
</div>
</body>
</html>