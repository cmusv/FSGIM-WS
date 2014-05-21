// Convert the data on the page to JSON
function dataToJSON() {
	return JSON.stringify({
		modelName : $("#modelName").val(), 
		versionNumber : $("#versionNumber").val(),
		description : $("#description").val()
	});
}

// Function to clear all the fields on the page,
// to get the page ready for the next query.
function clearAllFields() {
	$("#modelName").val("");
	$("#versionNumber").val("");
	$("#description").val("");
}

function onLoadCreatePage() {
	$("#addBtn").click(
			function() {
				showNotification("Saving information. Please wait ... ");

				request = $.ajax({
					url : "/rest/versions",
					type : "POST",
					contentType : 'application/json',
					dataType : 'json',
					data : dataToJSON()
				});

				// callback handler that will be called on success
				request.done(function(response, textStatus, jqXHR) {
					// log a message to the console
					showNotification("Version saved successfully");
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
		url : "/rest/versions",
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
				$("#versionsListingTbl")
						.append(
								"<tr><td align='center'>"
										+ "<b>Following is a list of all available Versions</b></td></tr>");
				var headerRow = "<tr>" 
					+ "<td><b>Model Name</b></td>"
					+ "<td><b>Version Number</b></td>" 
					+ "<td><b>Description</b></td>"
					+ "</tr>";
				$(headerRow).appendTo("#versionsListingTbl > tbody");
				$
						.each(
								response,
								function(i, version) {
									var deleteButton = "<input type='button' value='Delete' onclick='performDelete("
											+ version.id + ")' />";
									var queryRow = "<tr id ='" + version.id + "'>"
											+ "<td>" + version.modelName + "</td>"
											+ "<td>" + version.versionNumber + "</td>"
											+ "<td>" + version.description + "</td>"
											+ "<td>" + deleteButton + "</td>"
											+ "</tr>";
									$("#versionsListingTbl").append(queryRow);
								});
			});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}

function performDelete(versionId) {
	console.log("Version ID = " + versionId);
	localStorage.setItem('delVersionId', versionId);

	req = $.ajax({
		url : "/rest/versions/" + versionId,
		type : "DELETE",
		contentType : 'application/json',
		dataType : 'json'
	});

	// callback handler that will be called on success
	req.done(function(response, textStatus, jqXHR) {
		showNotification("Version deleted successfully.");
		var uId = localStorage.getItem('delVersionId');
		$('table#versionsListingTbl tr#' + uId).remove();
		console.log('Row with id ' + uId + ' removed from table');
		localStorage.removeItem('delVersionId');
	});

	// callback handler that will be called on failure
	req.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus);
	});
}
