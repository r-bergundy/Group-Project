// The root URL for the RESTful services
var rootURL = "http://localhost:8080/group1/rest/custservrep";

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

// ---------------------Find unique Cause Codes for Imsi--------------------

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