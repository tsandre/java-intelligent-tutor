<form id="userlevelselect" name="userlevelselect" method="post" action="userLevelSelect" style="padding-bottom:14px;">
<input name="currurl" id="currurl" type="hidden" value="<% out.println((request.getRequestURL()).toString()); %>" />
  I am a: <select name="newuserlevel" id="newuserlevel" onchange="document.forms['userlevelselect'].submit();">
    <option value="student" <% if(session.getAttribute("userLevel") == null || !session.getAttribute("userLevel").equals("teacher")){ out.print("selected=\"selected\""); } %>>Student</option>
    <option value="teacher" <% if(session.getAttribute("userLevel") != null && session.getAttribute("userLevel").equals("teacher")){ out.print("selected=\"selected\""); } %>>Teacher</option>
  </select>
</form>