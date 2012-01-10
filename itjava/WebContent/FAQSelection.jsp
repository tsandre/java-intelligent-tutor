<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.util.LinkedHashSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="itjava.model.*,itjava.db.*,java.util.HashMap,java.util.ArrayList,itjava.util.*,java.sql.*,itjava.scraper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>FAQ Selection</title>
<link href="css/maincss.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript"
	src="js/ratingsys.js"></script>
<script type="text/javascript" language="javascript" src="js/main.js"></script>
<script src="http://js.nicedit.com/nicEdit-latest.js"
	type="text/javascript"></script>
<script type="text/javascript">
	bkLib.onDomLoaded(nicEditors.allTextAreas);
	function open_win(linkText) {
		window.open(linkText);
	}

	function formSubmit() {

	}
</script>
</head>

<body>
	<table width="1024" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td colspan="3"><%@ include file="/modules/headers/header4.jsp"%></td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="#122222" height="1"></td>
		</tr>




		<tr>
			<td width="1" bgcolor="#122222"></td>
			<td
				style="padding: 10px 5px 10px 5px; color: #333; font-size: 12px; font-family: Arial, Helvetica, sans-serif;"
				align="center">
				<form id="faqSelectionForm" name="faqSelectionForm" method="post"
					action="FAQSelectionForm" onsubmit="formSubmit();">
					<table border="0" align="center" cellpadding="0" cellspacing="0"
						width="100%">

						<tr>
							<td colspan="3" height="1" bgcolor="#122222" id="tabletitle">Please
								select the type and order for FAQS</td>
						</tr>



						<%
							ArrayList<FAQItem> FAQList = new ArrayList<FAQItem>();
							FAQList.add(new FAQItem("FAQ 1", "DESC 1",
									"http://www.google.com/", 1, 1));
							FAQList.add(new FAQItem("FAQ 2", "DESC 2",
									"http://www.microsoft.com/", 2, 1));
							FAQList.add(new FAQItem("FAQ 3", "DESC 3",
									"http://www.amazon.com/", 3, 1));
							FAQList.add(new FAQItem("FAQ 4", "DESC 4",
									"http://www.netflix.com/", 4, 1));
							String query = "Reading a file using Java";
							LinkedHashSet<ScrapeData> scrapeFinalObj = InfoScrape
									.ScrapeSites(query);
							session.setAttribute("scrapeFinalObj", scrapeFinalObj);

							for (ScrapeData item : scrapeFinalObj) {
						%>
						<tr>
							<td style="padding: 10px 5px 10px 5px;">
								<table border="1" align="center" cellpadding="0" cellspacing="0"
									width="95%">
									<tr bgcolor="#797A7E">

										<td width="100%" id="faqTitleTD">
											<%
												out.print(item.getInfoTopic());
											%>
										</td>

										<td id="faqOrderTD"><select
											name="FAQTARating<%=item.getScrapeId()%>"
											style="background-color: #BFBBBF">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
										</select></td>
									</tr>
									<tr bgcolor="#DCD8D5">
										<td colspan="2">
											<%
												out.print(item.getInfoTopicLinkPreview() + "...");
											%> <a name="viewFaqLink" style="font-size: 10px;"
											href="javascript:open_win('<%=item.getInfoTopicURL()%>')">
												View FAQ </a>
										</td>
									</tr>
									<tr bgcolor="#B9BABE">
										<td id="faqTypeTD" colspan="2">Select Item Type: <input
											type="radio" name="faqTypeSelector<%=item.getScrapeId()%>"
											value="1">FAQ</input> <input type="radio"
											name="faqTypeSelector<%=item.getScrapeId()%>" value="2">Tutorial</input>
											<input type="radio"
											name="faqTypeSelector<%=item.getScrapeId()%>" value="3">Article</input>
											<input type="radio"
											name="faqTypeSelector<%=item.getScrapeId()%>" value="4">Related
												Links</input> <input type="radio"
											name="faqTypeSelector<%=item.getScrapeId()%>" value="5"
											checked="checked">Discard</input></td>
									</tr>
								</table>
							</td>
						</tr>
						<%
							}
						%>


						<tr>
							<td align="center"><input type="submit" onclick="submit"
								name="btnSubmit"></input></td>
						</tr>

					</table>
				</form>
				<div id="divProgress">
					<img src="images/loopLoader.gif" /><br /> Creating
					Deliverables....<br />Please Wait
				</div>
			</td>
			<td width="1" bgcolor="#122222"></td>
		</tr>


		<tr>
			<td height="1" colspan="3" bgcolor="#122222"></td>
		</tr>
	</table>
</body>
</html>