//The root URL for the RESTful services
var rootURL = "http://localhost:8080/group1/rest/custservrep";

$('#querybar').hide();
$('#tableColumns').hide();
var loggingIn = false;

// $('#results').hide();

// -------------- ExecuteQuery button--------------
$('#btn_execute_query').click(function() {
	selectQuery();
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
function selectQuery() {

	// mylist.options[mylist.selectedIndex].addEventListener('change',
	// function() { alert("TEST EventListener"); });

	if (mylist.options[mylist.selectedIndex].value == "1") {
		findEventIdsCauseCodes($('#txtImsi').val());
		$('#tableColumns').show();
		$('#results').hide();
	} else if (mylist.options[mylist.selectedIndex].value == "2") {

		document.getElementById("mainBody").innerHTML = "";
		// ???????????????????
	} else if (mylist.options[mylist.selectedIndex].value == "3") {
		findCauseCodes($('#txtImsi').val());
	} else if (mylist.options[mylist.selectedIndex].value == "4") {
		// ????????????????
	} else if (mylist.options[mylist.selectedIndex].value == "5") {
		queryCountFailuresForTacInTime($('#txtTac').val(), $('#dltStartTime')
				.val(), $('#dltEndTime').val());
	} else {
		alert("the final else in selectQuery");
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
			if (loggingIn) {
				alert('User not found');
			}
		}
	});
}

function logIn(user) {
	currentUser = user;
	// document.getElementById('user').innerHTML="getCookie(user)";
	window.location.href = "http://localhost:8080/group1/index.html";
	
	hidePrivilegedElements();

}

function hidePrivilegedElements(){
	var allElements = document.getElementsByTagName("*");

	for (var i=0; i < allElements.length; i++) {
	     if (hasClass(allElements[i], "si")){
	    	 alert(allElements[i].id);
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
	findUserByUserName(user.userName);
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
	if (dataValid() == true) {
		addUser();
	}

	return false;
});

function dataValid() {
	alert('yes');
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

function addUser() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL + '/addUser/',
		dataType : "json",
		data : formUserToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Registered successfully');
			logIn(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('User already exists');
			$('#txtRegUsername').focus();
		}
	});
}

function formUserToJSON() {
	var e = document.getElementById("cbxUserType");
	var strUser = e.options[e.selectedIndex].value;
	return JSON.stringify({
		"userName" : $('#username').val(),
		"password" : $('#regPassword').val(),
		"userType" : strUser
	});
}

// ---------------------Find Call Failure with ID--------------------
$('#btnFindImsi').click(function() {
	findCallFailureById($('#txtCallFailureId').val());
	return false;
});

function findCallFailureById(id) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/findimsi/' + id,
		dataType : "json", // data type of response
		success : function(data) {
			renderImsi(data);
		}
	});
}

function renderImsi(imsi) {
	$('#txtImsi').val(imsi);
}

// ---------------------Find unique Cause Codes for Imsi (query 6)
// --------------------

$('#btnCauseCodes').click(function() {

	if (dataValid(3)) {
		findCauseCodes($('#txtImsi').val());
	}
	return false;
});

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

function dataValid(query) {

	switch (query) {
	case 1:
		if (!imsiValid()) {
			alert('Invalid IMSI');
			return false;
		}
	case 2:
		if (!imsiValid()) {
			alert('Invalid IMSI');
			return false;
		}
	case 3:
		if (!imsiValid()) {
			alert('Invalid IMSI');
			return false;
		}
	case 4:
		if (!imsiValid()) {
			alert('Invalid IMSI');
			return false;
		}
	case 5:
		if (!imsiValid()) {
			alert('Invalid IMSI');
			return false;
		}
	}

	return true;

}

function tacValid() {
	var tac = $('#txtTac').val();

	if (IsNumeric(tac) && tac > 0) {
		return true;
	}
	return false;
}

function imsiValid() {

	var imsi = $('#txtImsi').val();

	if (imsi.length != 15) {
		return false;
	}
	if (!IsNumeric(imsi)) {
		return false;
	}
	return true;

}