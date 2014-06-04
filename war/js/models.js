function validatePage() {
	if(validateEmptyString($("#modelName").val(), 'Model Name') && 
			validateEmptyString($("#queriesURI").val(), 'Queries URI') ) {
		return true;
	}
	
	return false;
}

// Convert the data on the page to JSON
function dataToJSON() {
	return JSON.stringify({
		modelName : $("#modelName").val(), 
		queriesURI : $("#queriesURI").val(), 
		description : $("#description").val()
	});
}

// Function to clear all the fields on the page,
// to get the page ready for the next query.
function clearAllFields() {
	$("#modelName").val("");
	$("#queriesURI").val("");
	$("#description").val("");
}

function onLoadCreatePage() {
	$("#addBtn").click(
			function() {
				if(!validatePage()) {
					return false;
				}
				showNotification("Saving information. Please wait ... ");

				request = $.ajax({
					url : "/rest/models",
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					data : dataToJSON()
				});

				// callback handler that will be called on success
				request.done(function(response, textStatus, jqXHR) {
					// log a message to the console
					showNotification("Model saved successfully");
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
		url : "/rest/models",
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
				$("#modelsListingTbl")
						.append(
								"<tr><td align='center'>"
										+ "<b>Following is a list of all available Versions</b></td></tr>");
				var headerRow = "<tr>" 
					+ "<td><b>Model Name</b></td>"
					+ "<td><b>Queries URI</b></td>"
					+ "<td><b>Description</b></td>"
					+ "</tr>";
				$(headerRow).appendTo("#modelsListingTbl > tbody");
				$
						.each(
								response,
								function(i, model) {
									var deleteButton = "<input type='button' value='Delete' onclick='performDelete("
											+ model.id + ")' />";
									var queryRow = "<tr id ='" + model.id + "'>"
											+ "<td>" + model.modelName + "</td>"
											+ "<td>" + model.queriesURI + "</td>"
											+ "<td>" + model.description + "</td>"
											+ "<td>" + deleteButton + "</td>"
											+ "</tr>";
									$("#modelsListingTbl").append(queryRow);
								});
			});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}

function performDelete(modelId) {
	console.log("Model ID = " + modelId);
	localStorage.setItem('delModelId', modelId);

	req = $.ajax({
		url : "/rest/models/" + modelId,
		type : "DELETE",
		contentType : 'application/json',
		dataType : 'json'
	});

	// callback handler that will be called on success
	req.done(function(response, textStatus, jqXHR) {
		showNotification("Model deleted successfully.");
		var uId = localStorage.getItem('delModelId');
		$('table#modelsListingTbl tr#' + uId).remove();
		console.log('Row with id ' + uId + ' removed from table');
		localStorage.removeItem('delModelId');
	});

	// callback handler that will be called on failure
	req.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus);
	});
}
