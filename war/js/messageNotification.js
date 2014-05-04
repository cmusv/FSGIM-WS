$(document).ready(function() {
	var divHtml = '<div id="msgDiv" style="display: none;">' +
	'<span id="messg">NO MESSAGE</span>' +
	'<a href="#" class="close-notify" onclick="closeNotification()">OK</a> '+
	'</div>';
	$('body').append(divHtml);
});

function closeNotification() {
    $("#msgDiv").fadeOut("slow");
}

function showNotification(message) {
	$("#messg").empty();
	$("#messg").append(message);
	$("#msgDiv").fadeIn("slow");
}