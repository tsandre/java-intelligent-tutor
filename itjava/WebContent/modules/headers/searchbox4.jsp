<form action="FAQSearchServlet" method="get" name="tutorSearchForm" id="tutorSearchForm">
<table align="center" style="padding-top: 10px; margin: 0px;" cellspacing="0" cellpadding="0">
	<tr style="color:#fff;">
            <td colspan="3" bgcolor="#122222" align="center" style="padding: 0px; margin: 0px; color:#FFF; font-weight:bold; font-family:Arial, Helvetica, sans-serif; font-size:12px;">Frequently asked questions (FAQ)</td>
        </tr>
        <tr>
            <td style="width:1px; background-color:#122222; padding: 0px; margin: 0px; height:100px;"></td>
            <td style="width:510px" align="center">
                <table cellspacing="0" cellpadding="0" >
                    <tr>
                        <td colspan="3" align="center">Enter a query term to get related FAQ links.</td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="query" id="query" style="width:400px;" />
                        </td>
                        <td width="1"></td>
                        <td><input type="submit" name="Searchbtn" id="Searchbtn" onclick="return showProgress();" value="Search" /></td>
                    </tr>
                </table>
            </td>
            <td style="width:1px; background-color:#122222; padding: 0px; margin: 0px;"></td>
        </tr>
        <tr>
            <td colspan="3" bgcolor="#122222" height="1" style="padding: 0px; margin: 0px;"></td>
        </tr>
</table>
</form>