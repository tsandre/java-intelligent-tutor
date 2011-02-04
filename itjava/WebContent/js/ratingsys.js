/*
Author: Addam M. Driver
Date: 10/31/2006
*/

var sMax=5;	// Isthe maximum number of stars
var holder; // Is the holding pattern for clicked state
var preSet; // Is the PreSet value onces a selection has been made
var rated;

// Rollover for image Stars //
function rating(num, tutorid, s){	
	for(i=1; i<=sMax; i++){		
		if(i<=s){
			document.getElementById("_"+tutorid+"_"+i).className = "on";
		}else{
			document.getElementById("_"+tutorid+"_"+i).className = "";
		}
	}
	/*
	if(s==0){
		document.getElementById("rateStatus"+"_"+tutorid).innerHTML = "";
	}else if(s==1){
		document.getElementById("rateStatus"+"_"+tutorid).innerHTML = "Poor";
	}else if(s==2){
		document.getElementById("rateStatus"+"_"+tutorid).innerHTML = "Not Bad";
	}else if(s==3){
		document.getElementById("rateStatus"+"_"+tutorid).innerHTML = "Good";
	}else if(s==4){
		document.getElementById("rateStatus"+"_"+tutorid).innerHTML = "Great";
	}else if(s==5){
		document.getElementById("rateStatus"+"_"+tutorid).innerHTML = "Excellent";
	}*/
}

// For when you roll out of the the whole thing //
function off(me, tutorid, s){
	rating(me, tutorid, document.getElementById("ratingValue_"+tutorid).value);	
}

// When you actually rate something //
function rateIt(me, tutorid, s){
	//document.getElementById("rateStatus_"+tutorid).innerHTML = document.getElementById("ratingSaved_"+tutorid).innerHTML + me.title;
	document.getElementById("ratingValue_"+tutorid).value = s;
	sendRate(me, tutorid);
	rating(me, tutorid, s);
}

// Send the rating information somewhere using Ajax or something like that.
function sendRate(sel, tutorid){
	subformsome(tutorid);
}

