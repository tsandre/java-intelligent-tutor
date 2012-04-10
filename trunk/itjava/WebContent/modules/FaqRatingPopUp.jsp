<%@page import="java.util.Map"%>
<%@page import="itjava.scraper.UserRatingData"%>
<%@page import="itjava.scraper.FAQRaterDataRetriever"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate</title>
</head>
<script>
	function getXMLObject() //XML OBJECT
	{
		var xmlHttp = false;
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP"); // For Old Microsoft Browsers
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); // For Microsoft IE 6.0+
			} catch (e2) {
				xmlHttp = false // No Browser accepts the XMLHTTP Object then false
			}
		}
		if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
			xmlHttp = new XMLHttpRequest(); //For Mozilla, Opera Browsers
		}
		return xmlHttp; // Mandatory Statement returning the ajax object created
	}

	var xmlhttp = new getXMLObject(); //xmlhttp holds the ajax object

	function saveRating(scrapeId, userId, userRatingYes, userRatingNo) {
		if (xmlhttp) {

			xmlhttp.open("POST", "FaqRatingClickServlet", true); //getname will be the servlet name
			xmlhttp.onreadystatechange = handleServerResponse;
			xmlhttp.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded');
			
			xmlhttp.send("scrapeId=" + scrapeId + "&username=" + userId + "&userRatingNo=" + userRatingNo + "&userRatingYes=" + userRatingYes);

		}
		ajax_hideTooltip();
	}

	function handleServerResponse() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {

			} else {
				alert("Error during AJAX call. Please try again");
			}
		}
	}
</script>
<body>


	<%
		Integer scrapeId = Integer.parseInt(request
				.getParameter("scrapeId"));
		String userId = String.valueOf(session.getAttribute("userName"));
		FAQRaterDataRetriever faqData = new FAQRaterDataRetriever();
		Map<String, Integer> ratingMap = faqData
				.getGeneralScrapeRatingData(scrapeId);
		if (faqData.isScrapeRatedByUser(userId, scrapeId)) {
			UserRatingData userRD = faqData.getUserRatingDetailsForScrape(
					userId, scrapeId);
	%>
	<br>You rated this as
	<%=(userRD.getUserRatingYes() == 1) ? "Yes" : "No"%>
	on
	<%=userRD.getTimeAccessed()%>
	<br><br> Re-Rate:
	<%
		}
	%>


	<br> Was this link helpful to you?
	<br />
	<a href="javascript: saveRating(<%=scrapeId%>,'<%=userId%>','1','0')">Yes</a>
	<a href="javascript: saveRating(<%=scrapeId%>,'<%=userId%>','0','1')">No</a>
	<br>
	<br />
	<%=ratingMap.get("RatingYes")%>
	out of
	<%=ratingMap.get("RatingYes") + ratingMap.get("RatingNo")%>
	people found this helpful.
	<br />
	<a href="#" onclick="ajax_hideTooltip();return false">Close</a>
</body>
</html>