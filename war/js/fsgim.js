$(document).ready(
		function() {
			var userId = localStorage.getItem("userId");
			var sURL = document.URL;
			var baseURL = sURL.substring(0, sURL.indexOf('/', 7));

			if (!userId) {
				if(sURL.indexOf('login.html') > 0) {
					console.log('Already on login page. Ignore redirect.')
					return;
				}
				console.log("No logged in user. Redirecting to login page...");
				window.location.replace(baseURL + "/login.html");
			} else {
				// Add a footer for users to use.
				var userName = localStorage.getItem("userName");
				var spaces = '&nbsp;&nbsp;&nbsp;&nbsp;';
				var divHtml = '<div id="headerDiv"><br/><br/>' + '<b>Welcome '
						+ userName + ' !</b>' + spaces + '<a id="home" href="'
						+ baseURL + '/index.html">Home</a>' + spaces
						+ '<a id="logout" href="' + baseURL
						+ '/login.html">Logout</a> ' + '</div>';
				$('body').prepend(divHtml);

				// Add onclick listener for logout link
				$('#logout').click(function() {
					localStorage.removeItem('userId');
					localStorage.removeItem('userName');
				});
			}
		});

// Utility method to check if a field has empty value
// and shows a notification.
function validateEmptyString(value, fieldName) {
	if ("" == value.trim()) {
		showNotification(fieldName + " is mandatory.");
		return false;
	}
	
	return true;
}

function populateModelNames(comboBoxId) {
	console.log("Getting model names to populate in combo box: " + comboBoxId);
	request = $.ajax({
		url : "/rest/models",
		type : "GET",
		contentType : 'application/json',
		dataType : 'json'
	});

	// callback handler that will be called on success
	request.done(
		function(response, textStatus, jqXHR) {
		// log a message to the console
		console.log("Response obtained:: " + response);

		// Populate the results table on the page.
		$.each(response, 
				function(i, model) {
					$("#" + comboBoxId).append($('<option></option>').val(model.modelName).html(model.modelName));
				});
	});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}