function isValidEmail(str){
	return(str.indexOf(".")>2)&&(str.indexOf("@")>0);
}
function checkForm(){
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var school = document.getElementById("school").value;
	var email = document.getElementById("email").value;
	var username = document.getElementById("username").value;
	var password1 = document.getElementById("password").value;
	var password2 = document.getElementById("passwordConfirm").value;
	
	if(firstName.length < 1){
		alert("Please enter your first name.");
		document.form1.firstName.focus();
	}else if(lastName.length < 1){
		alert("Please enter your last name.");
		document.form1.lastName.focus();
	}else if(school.length < 1){
		alert("Please enter your school.");
		document.form1.school.focus();
	}else if(!isValidEmail(email)){
		alert("Please enter a valid email.");
		document.form1.email.focus();
	}else if(username.length < 1){
		alert("Please enter your username.");
		document.form1.username.focus();
	}else if(password1.length < 6 || password1.length > 12){
		alert("Please enter a valid password. Passwords must be 6-12 characters.");
		document.form1.password.focus();
	}else if(password1 != password2){
		alert("The passwords do not match! Please re-enter your password to ensure they are correct.");
		document.form1.password.focus();
	}else{
		document.forms["form1"].submit();
	}
}

function checkForm2(){
	var username = document.getElementById("username2").value;
	var password = document.getElementById("password2").value;
	
	if(username.length < 1){
		alert("Please enter your username to login.");
		document.form1.username2.focus();
	}else if(password.length < 1){
		alert("Please enter your password");
		document.form1.password2.focus();
	}else{
		document.forms["form2"].submit();
	}
}

function checkForm3(){
	var username = document.getElementById("username3").value;
	var password = document.getElementById("password3").value;
	
	if(username.length < 1){
		alert("Please enter your username to login.");
		document.form1.username2.focus();
	}else if(password.length < 1){
		alert("Please enter your password");
		document.form1.password2.focus();
	}else{
		document.forms["form3"].submit();
	}
}

function checkForm4(){
	var firstName = document.getElementById("firstName3").value;
	var lastName = document.getElementById("lastName3").value;
	var school = document.getElementById("school3").value;
	var password1 = document.getElementById("password3").value;
	var password2 = document.getElementById("passwordConfirm3").value;

	if(firstName.length < 1){
		alert("Please enter your first name.");
		document.form3.firstName.focus();
	}else if(lastName.length < 1){
		alert("Please enter your last name.");
		document.form3.lastName.focus();
	}else if(school.length < 1){
		alert("Please enter your school.");
		document.form3.school.focus();
	}else if(password1.length != 0 && (password1.length < 6 || password1.length > 12)){
		alert("Please enter a valid password. Passwords must be 6-12 characters.");
		document.form3.password.focus();
	}else if(password1 != password2){
		alert("The passwords do not match! Please re-enter your password to ensure they are correct.");
		document.form3.password.focus();
	}else{
		document.forms["form3"].submit();
	}
}

function checkForm5(){
	var firstName = document.getElementById("firstName3").value;
	var lastName = document.getElementById("lastName3").value;
	var school = document.getElementById("school3").value;
	var password1 = document.getElementById("password3").value;
	var password2 = document.getElementById("passwordConfirm3").value;

	if(firstName.length < 1){
		alert("Please enter your first name.");
		document.form3.firstName.focus();
	}else if(lastName.length < 1){
		alert("Please enter your last name.");
		document.form3.lastName.focus();
	}else if(school.length < 1){
		alert("Please enter your school.");
		document.form3.school.focus();
	}else if(password1.length != 0 && (password1.length < 6 || password1.length > 12)){
		alert("Please enter a valid password. Passwords must be 6-12 characters.");
		document.form3.password.focus();
	}else if(password1 != password2){
		alert("The passwords do not match! Please re-enter your password to ensure they are correct.");
		document.form3.password.focus();
	}else{
		document.forms["form3"].submit();
	}
}

function checkAvailability1(){
	window.open('modules/checkAvailability.jsp?username='+document.getElementById("username").value,'mywindow','width=400,height=200')
}

function checkAvailability2(){
	window.open('modules/checkAvailability2.jsp?username='+document.getElementById("username").value,'mywindow','width=400,height=200')
}

function changebg(imgid, imgsrc){
	document.getElementById(imgid).src = "images/"+imgsrc;
}

function subformsome(tutorid){
	window.open("updateRatingServlet?tutorID=" + document.getElementById("tutorID_" + tutorid).value + "&ratingValue=" + document.getElementById("ratingValue_" + tutorid).value, 'mywindow', 'width=400, height=200');
}

function gotoURL(URL) {
	window.location = URL;
}

function showProgress() {
	document.getElementById("divProgress").style.display = 'block';
}

function checkgotoURL(URL){
	var yesgo= confirm("Are you sure you want to leave the tutor creation process? If you leave this page now your progress will be lost.");
	if(yesgo==true){
		gotoURL(URL);
	}
}

function checksubmitform(theform){
	var yesgo= confirm("Are you sure you want to leave the tutor creation process? If you leave this page now your progress will be lost.");
	if(yesgo==true){
		document.forms[theform].submit();
	}
}

function submitformbutton(myfield, e){
	var keycode;
	if (window.event) keycode = window.event.keyCode;
	else if (e) keycode = e.which;
	else return false;

	if (keycode == 13){
		return true;
	}else
		return false;
}
