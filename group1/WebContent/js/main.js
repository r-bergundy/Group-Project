// The root URL for the RESTful services
var rootURL = "http://localhost:8080/group1/rest/custservrep";

//---------------------Find Call Failure with ID--------------------
$('#btnFindImsi').click(function() {
	findCallFailureById($('#txtCallFailureId').val());
	return false;
});

function findCallFailureById(id) {
	$.ajax({
		type: 'GET',
		url: rootURL + '/findimsi/' + id,
		dataType: "json", // data type of response
		success: function(data){
			renderImsi(data);
		}
	});	
	
}

function renderImsi(imsi) {
	$('#txtImsi').val(imsi);
}


//---------------------Find unique Cause Codes for Imsi--------------------

$('#btnCauseCodes').click(function() {
	findCauseCodes($('#txtImsi').val());
	return false;
});

function findCauseCodes(imsi) {
	$.ajax({
		type: 'GET',
		url: rootURL + '/findUniqueCauseCodes/' + imsi,
		dataType: "json", // data type of response
		success: function(data){
			renderList(data);
		}
	});	
	
}

function renderList(data) {
	alert('rendering');
	var items = data == null ? [] : (data instanceof Array ? data : [ data ]);

	var myTable = document.getElementById('tblTodo');
	
	myTable.find('tr:gt(0)').remove();
	for (var i = 0; i < items.length; i++) {
		


		myTable.append('<tr><td></td></tr>');
		var rowCount = $("#tblTodo > tbody > tr").length - 1;

		myTable.rows[rowCount].cells[0].innerHTML = item[i];
		
	}
}