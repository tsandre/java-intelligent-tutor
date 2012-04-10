<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.util.LinkedHashSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="itjava.model.*,itjava.db.*,itjava.view.*,java.util.HashMap,java.util.ArrayList,itjava.util.*,java.sql.*,itjava.scraper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>FAQ Viewer</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript"
	src="js/ratingsys.js"></script>
<script type="text/javascript" language="javascript" src="js/main.js"></script>
<script src="http://js.nicedit.com/nicEdit-latest.js"
	type="text/javascript"></script>
<script type="text/javascript">
	bkLib.onDomLoaded(nicEditors.allTextAreas);
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

	function ajaxFunction(scrapeId) {
		if (xmlhttp) {

			xmlhttp.open("POST", "FaqViewerLinkClickServlet", true); //getname will be the servlet name
			xmlhttp.onreadystatechange = handleServerResponse;
			xmlhttp.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded');
			xmlhttp.send("scrapeId=" + scrapeId); //Posting txtname to Servlet
		}
	}

	function handleServerResponse() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {

			} else {
				alert("Error during AJAX call. Please try again");
			}
		}
	}
	function open_win(linkText, scrapeId) {

		ajaxFunction(scrapeId);
		window.open(linkText);
	}

	function open_pop() {

		if (document.getElementById('showimage').style.display == 'none')
			document.getElementById('showimage').style.display = 'block';
		else
			document.getElementById('showimage').style.display = 'none';

	}
	function formSubmit() {

	}
	function rate_mouse_over(scrapeId) {

		ajax_showTooltip(window.event, 'modules/FaqRatingPopUp.jsp?scrapeId='
				+ scrapeId, this);
		return false
	}
</script>
<script type="text/javascript" src="js/ajax-dynamic-content.js"></script>
<script type="text/javascript" src="js/ajax.js"></script>
<script type="text/javascript" src="js/ajax-tooltip.js"></script>
<link rel="stylesheet" href="css/ajax-tooltip.css" media="screen"
	type="text/css" />
<link rel="stylesheet" href="css/ajax-tooltip-demo.css" media="screen"
	type="text/css" />
</head>

<body>
	<%
		System.out.println("userName: "+session.getAttribute("userName"));
		String userName = session.getAttribute("userName").toString();
		Integer tutorID = Integer.parseInt(request.getParameter("tutorID").toString());
		HashMap<Integer, ArrayList<ScrapeData>> faqItemMap = (new FAQRetriever())
				.getFAQforTutorial(tutorID);
		ArrayList<ScrapeData> faqItemList = faqItemMap.get(new Integer(1));
		ArrayList<ScrapeData> tutorialItemList = faqItemMap
				.get(new Integer(2));
		ArrayList<ScrapeData> articleItemList = faqItemMap.get(new Integer(
				3));
		ArrayList<ScrapeData> rlItemList = faqItemMap.get(new Integer(4));
		
	%>
	<form action="FaqViewerLinkClickServlet" method="post" name="linkClick">
		<table width="1024" border="0" align="center" cellpadding="0"
			cellspacing="5">
			<tr>
				<td colspan="2"><%@ include file="/modules/headers/header4.jsp"%></td>
			</tr>

			<tr>
				<td colspan="2" height="1" bgcolor="#122222" style="color: white"
					align="center">Related Links and Frequently Asked Questions
					for current tutor</td>
			</tr>

			<tr>
				<td colspan="2" bgcolor="#122222" height="1"></td>
			</tr>

			<!-- 			<div id="divT"  -->
			<!-- 				style="position: absolute; left: 362px; bottom: 51px; top: auto; height: 118px; width: 278px; display: none;"> -->
			<%-- 				<jsp:include page="/modules/FaqRatingPopUp.jsp" /> --%>
			<!-- 				</div> -->


			<tr>
				<td width="512" height="100%" valign="baseline">

					<table border="1" cellpadding="0" cellspacing="0">

						<tr>
							<td height="1" bgcolor="#122222" id="tabletitle">FAQ</td>
						</tr>
						<%
							for (ScrapeData item : faqItemList) {
						%>
						<tr>

							<td>
								<table width="100%">
									<tr width="100%">
										<td align="left" width="100%"><a name="viewFaqLink"
											href="javascript:open_win('<%=item.getInfoTopicURL()%>','<%=item.getScrapeId()%>')">
												<%=item.getInfoTopic()%>
										</a></td>
										<td align="right"><div
												onclick="ajax_showTooltip(window.event,'modules/FaqRatingPopUp.jsp?scrapeId=<%=item.getScrapeId()%>',this);return false">
												<span class="test" title="Click to Rate"><a href='#'>Rate</a></span>
											</div></td>
									</tr>
								</table> <br /><%=item.getInfoTopicLinkPreview()%></td>
						</tr>
						<%
							}
						%>


					</table>

				</td>
				<td height="100%" valign="baseline"><table border="1"
						cellpadding="0" cellspacing="0">

						<tr>
							<td height="1" bgcolor="#122222" id="tabletitle">Tutorials</td>
						</tr>
						<%
							for (ScrapeData item : tutorialItemList) {
						%>

						<tr>

							<td>
								<table width="100%">
									<tr width="100%">
										<td align="left" width="100%"><a name="viewFaqLink"
											href="javascript:open_win('<%=item.getInfoTopicURL()%>','<%=item.getScrapeId()%>')">
												<%=item.getInfoTopic()%>
										</a></td>
										<td align="right"><div
												onclick="ajax_showTooltip(window.event,'modules/FaqRatingPopUp.jsp?scrapeId=<%=item.getScrapeId()%>',this);return false">
												<span class="test" title="Click to Rate"><a href='#'>Rate</a></span>
											</div></td>
									</tr>
								</table> <br /><%=item.getInfoTopicLinkPreview()%></td>
						</tr>
						<%
							}
						%>
					</table>
			</td>
			</tr>

			<tr>
				<td colspan="2" bgcolor="#122222" height="1"></td>
			</tr>

			<tr>
				<td width="512" valign="baseline"><table border="1"
						cellpadding="0" cellspacing="0">

						<tr>
							<td height="1" bgcolor="#122222" id="tabletitle">Articles</td>
						</tr>
						<%
							for (ScrapeData item : articleItemList) {
						%>

						<tr>

							<td>
								<table width="100%">
									<tr width="100%">
										<td align="left" width="100%"><a name="viewFaqLink"
											href="javascript:open_win('<%=item.getInfoTopicURL()%>','<%=item.getScrapeId()%>')">
												<%=item.getInfoTopic()%>
										</a></td>
										<td align="right"><div
												onclick="ajax_showTooltip(window.event,'modules/FaqRatingPopUp.jsp?scrapeId=<%=item.getScrapeId()%>',this);return false">
												<span class="test" title="Click to Rate"><a href='#'>Rate</a></span>
											</div></td>
									</tr>
								</table> <br /><%=item.getInfoTopicLinkPreview()%></td>
						</tr>
						<%
							}
						%>
					</table></td>
				<td valign="baseline"><table border="1" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="1" bgcolor="#122222" id="tabletitle">Related
								Links</td>
						</tr>
						<%
							for (ScrapeData item : rlItemList) {
						%>

						<tr>

							<td>
								<table width="100%">
									<tr width="100%">
										<td align="left" width="100%"><a name="viewFaqLink"
											href="javascript:open_win('<%=item.getInfoTopicURL()%>','<%=item.getScrapeId()%>')">
												<%=item.getInfoTopic()%>
										</a></td>
										<td align="right"><div
												onclick="ajax_showTooltip(window.event,'modules/FaqRatingPopUp.jsp?scrapeId=<%=item.getScrapeId()%>',this);return false">
												<span class="test" title="Click to Rate"><a href='#'>Rate</a></span>
											</div></td>
									</tr>
								</table> <br /><%=item.getInfoTopicLinkPreview()%></td>
						</tr>
						<%
							}
						%>
					</table></td>
			</tr>






		</table>
	</form>

</body>
</html>