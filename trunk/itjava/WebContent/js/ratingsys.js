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
}

// For when you roll out of the the whole thing //
function off(me, tutorid, s){
	rating(me, tutorid, document.getElementById("ratingValue_"+tutorid).value);	
}

// When you actually rate something //
function rateIt(me, tutorid, s){
	document.getElementById("ratingValue_"+tutorid).value = s;
	sendRate(me, tutorid);
	rating(me, tutorid, s);
}

// Send the rating information somewhere using Ajax or something like that.
function sendRate(sel, tutorid){
	subformsome(tutorid);
}

