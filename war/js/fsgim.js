$(document).ready(function() {
	var userId = localStorage.getItem("userId");
	if(!userId) {
		console.log("No logged in user. Redirecting to login page...");
		
		// Disable all links
		$('a').bind("click.myDisable", function() { return false; });
		showNotification("No logged in user. Redirecting to login page...");
		window.location.replace("/login.html");
	} else {
		// Add a footer for users to use.
		var userName = localStorage.getItem("userName");
		var divHtml = '<div id="footerDiv">' +
		'<span>Welcome ' + userName + '!!</span>' +
		'<a href="login.html" class="close-notify">Logout</a> '+
		'</div>';
		$('body').append(divHtml);
	}
});