<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0"> -->
<!-- 	<tr> -->
<!--         <td width="125" align="center"><img id="createTutorIMG" src="images/create_tutor_over.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('teacherAccountIMG', 'teacher_account_over.jpg')" onmouseout="changebg('teacherAccountIMG', 'teacher_account_up.jpg')"><a href="teachers.jsp" class="cursorover"><img id="teacherAccountIMG" src="images/teacher_account_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('studentAccountIMG', 'student_account_over.jpg')" onmouseout="changebg('studentAccountIMG', 'student_account_up.jpg')"><a href="students.jsp" class="cursorover"><img id="studentAccountIMG" src="images/student_account_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('findTutorsIMG', 'find_tutors_over.jpg')" onmouseout="changebg('findTutorsIMG', 'find_tutors_up.jpg')"><a href="findTutors.jsp" class="cursorover"><img id="findTutorsIMG" src="images/find_tutors_up.jpg" border="0" /></a></td> -->
<!--         <td background="images/background_nav.jpg">&nbsp;</td> -->
<!--    </tr> -->
<!-- </table> -->



<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0"> -->
<!-- 	<tr> -->
<!--         <td width="125" align="center" onmouseover="changebg('createTutorIMG', 'create_tutor_over.jpg')" onmouseout="changebg('createTutorIMG', 'create_tutor_up.jpg')"><a href="createTutor.jsp" class="cursorover"><img id="createTutorIMG" src="images/create_tutor_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('teacherAccountIMG', 'teacher_account_over.jpg')" onmouseout="changebg('teacherAccountIMG', 'teacher_account_up.jpg')"><a href="teachers.jsp" class="cursorover"><img id="teacherAccountIMG" src="images/teacher_account_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('studentAccountIMG', 'student_account_over.jpg')" onmouseout="changebg('studentAccountIMG', 'student_account_up.jpg')"><a href="students.jsp" class="cursorover"><img id="studentAccountIMG" src="images/student_account_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('findTutorsIMG', 'find_tutors_over.jpg')" onmouseout="changebg('findTutorsIMG', 'find_tutors_up.jpg')"><a href="findTutors.jsp" class="cursorover"><img id="findTutorsIMG" src="images/find_tutors_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('findTutorsIMG', 'find_tutors_over.jpg')" onmouseout="changebg('findTutorsIMG', 'find_tutors_up.jpg')"><a href="findTutors.jsp" class="cursorover"><img id="findTutorsIMG" src="images/find_tutors_up.jpg" border="0" /></a></td> -->
<!--         <td width="125" align="center" onmouseover="changebg('findTutorsIMG', 'find_tutors_over.jpg')" onmouseout="changebg('findTutorsIMG', 'find_tutors_up.jpg')"><a href="findTutors.jsp" class="cursorover"><img id="findTutorsIMG" src="images/find_tutors_up.jpg" border="0" /></a></td> -->
<!--         <td background="images/background_nav.jpg">&nbsp;</td> -->
<!--     </tr> -->
<!-- </table> -->

<script src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	var timeout = 500;
	var closetimer = 0;
	var ddmenuitem = 0;

	function jsddm_open() {
		jsddm_canceltimer();
		jsddm_close();
		ddmenuitem = $(this).find('ul').css('visibility', 'visible');
	}

	function jsddm_close() {
		if (ddmenuitem)
			ddmenuitem.css('visibility', 'hidden');
	}

	function jsddm_timer() {
		closetimer = window.setTimeout(jsddm_close, timeout);
	}

	function jsddm_canceltimer() {
		if (closetimer) {
			window.clearTimeout(closetimer);
			closetimer = null;
		}
	}

	$(document).ready(function() {
		$('#jsddm > li').bind('mouseover', jsddm_open)
		$('#jsddm > li').bind('mouseout', jsddm_timer)
	});

	document.onclick = jsddm_close;
</script>
<link href="css/jddsm.css" rel="stylesheet" type="text/css" />
<ul id="jsddm">
	<li><a href="createTutor.jsp">Create Tutor</a></li>
	<li><a href="teachers.jsp">Teacher Account</a></li>
	<li><a href="students.jsp">Student Account</a></li>
	<li><a href="findTutors.jsp">Find Tutors</a></li>
	<li><a href="#">Industry Code</a>
		<ul>
			<li><a href="codeUpload.jsp">Code Upload</a></li>
			<li><a href="searchIndustry.jsp">Code Search</a></li>
		</ul></li>
</ul>