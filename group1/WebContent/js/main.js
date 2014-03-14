// The root URL for the RESTful services
var rootURL = "http://localhost:8080/group1/rest/custservrep";


//---------------------Logon User--------------------


$("#loginbutton").click(function() {
	//alert("Button works");
    findUserByUserName($('#username').val());
    return false;
});


function findUserByUserName(username) {
   
    $.ajax({
        type : 'GET',
        url : rootURL + '/findUser/' + username,
        dataType : "json", // data type of response
        success : function(data) {
            if(data.password == $("#loginfield").val()){
                alert("User found");
                logIn(data);
                }
            else{
                alert('Incorrect Password');
                $('#username').focus();
            }
        }
    });

}


function logIn(user) {
    currentUser = user;
    
    //document.getElementById('user').innerHTML="Test";
    window.location.href= "http://localhost:8080/group1/index.html";
   
}


//---------------------Register User--------------------


$("#btnregister").click(function() {
	if (dataValid()){
    	addUser();
    }

	//addUser();
	
	return false;
});

function dataValid() {
    
    var txtUser = document.getElementById('username');
    var txtPwrd1 = document.getElementById('regPassword');
    var txtPwrd2 = document.getElementById('confirmPassword');
    

    if ( notEmpty(txtUser, 'Username may not be blank') && notEmpty(txtPwrd1, 'Password may not be blank')) {
    	//alert("username blank");
        if (passwordsMatch(txtPwrd1, txtPwrd2, 'Passwords do not match') &&
            passwordValid(txtPwrd1, 'Password must be at least 6 characters long and contain at least one number and one letter')){
     
                return true;
            }; 
         
    } else return false;
     
}

function passwordValid(elem, helperMsg) {
	 
    var re = /^.*(?=.{6,})(?=.*[a-zA-Z])(?=.*\d).*$/;
    var OK = re.exec(elem.value);
    if (!OK){
        alert(helperMsg);
        elem.focus();
        return false;
    }
 
    return true;   
 
}
 
function passwordsMatch(elem1, elem2, helperMsg) {
    if (elem1.value == elem2.value){
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

function formUserToJSON() {
	var e = document.getElementById("cbxUserType");
	var strUser = e.options[e.selectedIndex].value;
	alert(strUser);
    return JSON.stringify({
        "userName" : $('#username').val(),
        "password" : $('#regPassword').val(),
        "userType" : strUser
    });
}
 
 

//$('#tableColumns').hide();
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

// ---------------------Find unique Cause Codes for Imsi (query 6) --------------------

$('#btnCauseCodes').click(function() {
	findCauseCodes($('#txtImsi').val());
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

//-----------Find EventIds and Cause Codes for Given Imsi(UserStory 4)--------------------

$('#btnEventIdCauseCode').click(function() {
	findEventIdsCauseCodes($('#txtImsi').val());
	$('#results').hide();
	$('#tableColumns').show();
	//how do i get the line below to stay at top of page-i used html
	//$("#tblQueryData").append('Event ID' + "\t\t" + 'CauseCode');
	return false;
});

function findEventIdsCauseCodes(imsi) {
	$.ajax({
		type : 'GET',
		url : rootURL + '/findEventIdsCauseCodes/' + imsi,
		dataType : "json", // data type of response
		success : function(data) {
			renderList(data);
	
		}
	
	});
	
}

function renderList(data) {
	var resultset = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
	if($("#tblQueryData tr").length > 1) {
		$(this).parent().parent("tr").remove(); }
	var rowCount = 0;
	for (var i = 0; i < resultset.length; i++) {

		$("#tblQueryData").append('<tr><td>' + resultset[i] + '</td></tr>');

		rowCount++;

	}
	
}



//---------------------Find count of failures in given time for a tac (query 6) --------------------
//queryCountFailuresForTacInTime('21060800', '2013-01-11T17:15', '2013-01-11T17:18');

$('#btn???').click(function() {
	queryCountFailuresForTacInTime($('#txtTac').val(), $('#dltStartTime').val(), $('#dltEndTime').val());
	return false;
});

function queryCountFailuresForTacInTime(tac, startTime, endTime) {
	alert('etc');
	$.ajax({
		type : 'GET',
		url : rootURL + '/query8/' + tac + "|" + startTime + "|" + endTime,
		dataType : "json", // data type of response
		success : function(data) {

			renderQuery8(data);
		}
	});
}

function renderQuery8(data) {
	alert(data);
}}

