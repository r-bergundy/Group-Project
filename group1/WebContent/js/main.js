//The root URL for the RESTful services
var rootURL = "http://localhost:8080/group1/rest/custservrep";

$('#querybar').hide();
$('#tableColumns').hide();
var loggingIn = false;

// $('#results').hide();

// -------------- ExecuteQuery button--------------
$('#btn_execute_query').click(function() {
	selectQuery(mylist.options[mylist.selectedIndex].value);
	return false;
});

// -------------- Update Visibility of Query Elements--------------

function updateVisibility() {

	var children = document.getElementById('querybar')
			.getElementsByTagName('*');

	$('#querybar').show();
	for (var i = 0; i < children.length; i++) {
		if (hasClass(children[i], selectedQuery)) {
			children[i].style.visibility = 'visible';
		} else {
			children[i].style.visibility = 'hidden';
		}
	}
}

function hasClass(elem, klass) {
	return (" " + elem.className + " ").indexOf(" " + klass + " ") > -1;
}

// -------------- Select Query to execute--------------
function selectQuery(query) {

	switch (parseFloat(query)){
	case 1: 
		if (imsiValid()){
			findEventIdsCauseCodes($('#txtImsi').val());
			$('#tableColumns').show();
			$('#results').hide();
		}
		break;
	case 2:
		if (imsiValid()){
			document.getElementById("mainBody").innerHTML = "";
			// ???????????????????
		}
		break;	
	case 3:
		if (imsiValid()){
			findCauseCodes($('#txtImsi').val());
		}
		break;
	case 4:
		// ????????????????
		break;
	case 5:
		if (tacValid()){
			queryCountFailuresForTacInTime($('#txtTac').val(), $('#dltStartTime')
					.val(), $('#dltEndTime').val());
		}
		break;

	}

}

// ---------------------Log in User--------------------

$("#btnLogin").click(function() {
	loggingIn = true;
	findUserByUserName($('#username').val());
	return false;
});

function findUserByUserName(username) {

	$.ajax({
		type : 'GET',
		url : rootURL + '/findUser/' + username,
		dataType : "json", // data type of response
		success : function(data) {
			if (loggingIn) {
				if (data.password == $("#loginfield").val()) {
					addCookie(data);
					logIn(data);
				} else {
					alert('Incorrect Password');
					$('#username').focus();
				}
				loggingIn = false;
			} else {
				logIn(data);
			}
		},
		error : function() {
			alert('hid');
			if (loggingIn) {
				alert('User not found');
			}
		}
	});
}

function logIn(user) {
	currentUser = user;
	window.location.href = "http://localhost:8080/group1/index.html";
//	document.getElementById('lblUser').innerHTML = getCookie('user');
//	document.getElementById('lblUserType').innerHTML = getCookie('userType');	

}

function hidePrivilegedElements(){

	if (getCookie("userType") != "NETWORK_MANAGEMENT_ENGINEER"){
		var nmeElements = document.querySelectorAll('*.nme');
		for (var i=0; i < nmeElements.length; i++) {
			nmeElements[i].style.display='none';
		}
		if (getCookie("userType") != "SUPPORT_ENGINNER"){
			var seElements = document.querySelectorAll('*.se');

			for (var i=0; i < seElements.length; i++) {
				seElements[i].style.display='none';
			}
		}
	}

}

$("#btnLogout").click(function() {
	document.cookie = 'user=; path=http://localhost:8080/group1/';
	document.cookie = 'userType=; path=http://localhost:8080/group1/';
	window.location.href = "http://localhost:8080/group1/login.html";
	return false;
});

function addCookie(user) {
	document.cookie = "user=" + user.userName
			+ "; path=http://localhost:8080/group1/index.html";
	document.cookie = "userType=" + user.userType
			+ "; path=http://localhost:8080/group1/index.html";
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) == 0)
			return c.substring(name.length, c.length);
	}
	return "";
}

// ---------------------Register User--------------------

$("#btnRegister").click(function() {
	if (dataValid()) {
		addUser();
	}

	return false;
});

function addUser() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL + '/addUser/',
		dataType : "json",
		data : formUserToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Registered successfully');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('User add failed');
			$('#txtUsername').focus();
		}
	});
}

function formUserToJSON() {
	var e = document.getElementById("cbxUserType");
	var strUser = e.options[e.selectedIndex].value;
	return JSON.stringify({
		"userName" : $('#txtUsername').val(),
		"password" : $('#regPassword').val(),
		"userType" : strUser
	});
}

// ---------------------Find Call Failure with ID--------------------
function findCallFailureById(id) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/findimsi/' + id,
		dataType : "json", // data type of response
		success : function(data){
			alert('Connection present');
		}
	});
}

// ---------------------Find unique Cause Codes for Imsi (query 6)
// --------------------

function findCauseCodes(imsi) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/findUniqueCauseCodes/' + imsi,
		dataType : "json", // data type of response
		success : function(data) {
			renderList(data);
		}
	});

}

// -----------Find EventIds and Cause Codes for Given Imsi(UserStory
// 4)--------------------

function findEventIdsCauseCodes(imsi) {

	$.ajax({
		type : 'GET',
		url : rootURL + '/findEventIdsCauseCodes/' + imsi,
		dataType : "json",
		success : function(data) {
			renderList(data);

		}

	});

}

function renderList(data) {
	var resultset = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
	if ($("#tblQueryData tr").length > 1) {
		$(this).parent().parent("tr").remove();
	}
	var rowCount = 0;
	for (var i = 0; i < resultset.length; i++) {

		$("#tblQueryData").append('<tr><td>' + resultset[i] + '</td></tr>');

		rowCount++;

	}

}

// ---------------------Find count of failures in given time for a tac (query 8)

function queryCountFailuresForTacInTime(tac, startTime, endTime) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/query8/' + tac + "," + startTime + "," + endTime,
		dataType : "json", // data type of response
		success : function(data) {
			renderQuery8(data);
		}
	});
}

function renderQuery8(data) {
	alert(data);
}

// -------------------------Validation ---------------------------------

function tacValid() {
	var tac = $('#txtTac').val();

	if (IsNumeric(tac) && tac > 0) {
		return true;
	}
	alert("Invalid TAC");
	return false;
}

function imsiValid() {

	var imsi = $('#txtImsi').val();

	if (imsi.length != 15 || !IsNumeric(imsi)) {
		alert("Invalid IMSI");
		return false;
	}
	return true;

}


function dataValid() {
	var txtUser = document.getElementById('txtUsername');
	var txtPwrd1 = document.getElementById('regPassword');
	var txtPwrd2 = document.getElementById('confirmPassword');
	if (notEmpty(txtUser, 'Username may not be blank')
			&& notEmpty(txtPwrd1, 'Password may not be blank')) {
		alert('2r');
		if (passwordsMatch(txtPwrd1, txtPwrd2, 'Passwords do not match')
				&& passwordValid(
						txtPwrd1,
						'Password must be at least 6 characters long and contain at least one number and one letter')) {

			return true;
		}
		;

	}

	return false;

}

function passwordValid(elem, helperMsg) {

	var re = /^.*(?=.{6,})(?=.*[a-zA-Z])(?=.*\d).*$/;
	var OK = re.exec(elem.value);
	if (!OK) {
		alert(helperMsg);
		elem.focus();
		return false;
	}

	return true;

}

function passwordsMatch(elem1, elem2, helperMsg) {
	if (elem1.value == elem2.value) {
		return true;
	}
	alert(helperMsg);
	elem1.focus();
	return false;
}

function notEmpty(elem, helperMsg) {
	if (elem.value.length == 0) {
		alert(helperMsg);
		elem.focus();
		return false;
	}
	return true;
}

//----------------------------- Connectivity ----------------------------------

$("#btn_check_connectivity").click(function() {
	findCallFailureById(1);
	return false;
});