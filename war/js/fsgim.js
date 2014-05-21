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
	showNotification("Loading model names. Please wait ... ");
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
		
		callBackAfterPopulateModelNames(comboBoxId);
	});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}

function populateModelVersions(modelNameComboBoxId, modelVersionComboBoxId) {
	showNotification("Loading model versions. Please wait ... ");
	console.log("Getting model versions to populate in combo box: " + modelVersionComboBoxId);
	request = $.ajax({
		url : "/rest/versions",
		type : "GET",
		contentType : 'application/json',
		dataType : 'json'
	});

	// callback handler that will be called on success
	request.done(
		function(response, textStatus, jqXHR) {
		// log a message to the console
		console.log("Response obtained:: " + response);

		// Empty the list before adding new versions
		$("#" + modelVersionComboBoxId).empty();
		
		var modelName = $("#" + modelNameComboBoxId).find(":selected").text();
		console.log("Selected modelName = " + modelName);
		// Populate the results table on the page.
		$.each(response, 
				function(i, version) {
					console.log("modelName=" + version.modelName + ", versionNumber=" + version.versionNumber);
					if(version.modelName == modelName) {
						$("#" + modelVersionComboBoxId).append($('<option></option>').val(version.versionNumber).html(version.versionNumber));
					}
				});
		callBackAfterPopulateModelVersions(modelNameComboBoxId, modelVersionComboBoxId);
	});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}