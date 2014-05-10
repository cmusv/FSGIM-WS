// Validate input on the page
function validatePage() {
	if(validateEmptyString($("#userName").val(), 'User name') && 
			validateEmptyString($("#password").val(), 'Password')) {
		return true;
	}
	
	return false;
}

// Convert the data on the page to JSON
function dataToJSON() {
	return JSON.stringify({
		userName : $("#userName").val(),
		password : $("#password").val()
	});
}

function onLoadCreatePage() {
	$("#loginBtn").click(
			function() {
				if(!validatePage()) {
					return false;
				}
				
				request = $.ajax({
					url : "/rest/login",
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					data : dataToJSON()
				});

				// callback handler that will be called on success
				request.done(function(response, textStatus, jqXHR) {
					// Navigate to index.html page.
					console.log("User login successful !!!");
					localStorage.setItem("userId", response.id);
					localStorage.setItem("userName", response.userName);
					window.location.replace("index.html");
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