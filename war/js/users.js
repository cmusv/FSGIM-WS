// Validate for good data
function validatePage() {
	var userName = $("#userName").val();
	var password = $("#password").val();
	var passwordRe = $("#passwordRe").val();

	if (!(validateEmptyString(userName, 'User name') && validateEmptyString(
			password, 'Password'))) {
		return false;
	}

	if (password != passwordRe) {
		showNotification("Error: Passwords do not match. Please try again");
		return false;
	}
	
	return true;
}

// Convert the data on the page to JSON
function dataToJSON() {
	return JSON.stringify({
		userName : $("#userName").val(),
		password : $("#password").val()
	});
}

// Function to clear all the fields on the page,
// to get the page ready for the next query.
function clearAllFields() {
	showNotification("User saved successfully");
	$("#userName").val("");
	$("#password").val("");
	$("#passwordRe").val("");
}

function onLoadCreatePage() {
	$("#addBtn").click(
			function() {
				if (!validatePage()) {
					return false;
				}
				showNotification("Saving information. Please wait ... ");

				request = $.ajax({
					url : "/rest/users",
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					data : dataToJSON()
				});

				// callback handler that will be called on success
				request.done(function(response, textStatus, jqXHR) {
					// log a message to the console
					clearAllFields();
				});

				// callback handler that will be called on failure
				request.fail(function(jqXHR, textStatus, errorThrown) {
					// log the error to the console
					console.error("The following error occured: " + textStatus,
							errorThrown);

					showNotification("Error: " + errorThrown);
				});
			});
}

function onLoadShowPage() {
	request = $.ajax({
		url : "/rest/users",
		type : "GET",
		contentType : 'application/json',
		dataType : 'json'
	});

	// callback handler that will be called on success
	request
			.done(function(response, textStatus, jqXHR) {
				// log a message to the console
				console.log("Response obtained:: " + response);

				// Populate the results table on the page.
				$("#usersListingTbl")
						.append(
								"<tr><td align='center' colspan='2'>"
										+ "<b>Following is a list of all available Users</b></td></tr>");
				var headerRow = "<tr>" + "<td><b>User Name</b></td>" 
								+ "<td><b>Action</b></td>" 
								+  "</tr>";
				$(headerRow).appendTo("#usersListingTbl > tbody");
				$
						.each(
								response,
								function(i, user) {
									var deleteButton = "<input type='button' value='Delete' onclick='performDelete("
											+ user.id + ")' />";
									var queryRow = "<tr id ='" + user.id + "'>"
											+ "<td>" + user.userName + "</td>"
											+ "<td>" + deleteButton + "</td>"
											+ "</tr>";
									$("#usersListingTbl").append(queryRow);
								});
			});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}

function performDelete(userId) {
	console.log("User ID = " + userId);
	
	// Current user cannot delete his account. This is a fail safe to make 
	// sure there is at least one valid account to login into the system.
	if(userId == localStorage.getItem('userId')) {
		showNotification('ERROR: You cannot delete the account that is currently logged in.');
		return;
	}
		
	localStorage.setItem('delUserId', userId);

	req = $.ajax({
		url : "/rest/users/" + userId,
		type : "DELETE",
		contentType : 'application/json',
		dataType : 'json'
	});

	// callback handler that will be called on success
	req.done(function(response, textStatus, jqXHR) {
		showNotification("User deleted successfully.");
		var uId = localStorage.getItem('delUserId');
		$('table#usersListingTbl tr#' + uId).remove();
		console.log('Row with id ' + uId + ' removed from table');
		localStorage.removeItem('delUserId');
	});

	// callback handler that will be called on failure
	req.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus);
		showNotification("ERROR when deleting user: " + errorThrown);
	});
}
