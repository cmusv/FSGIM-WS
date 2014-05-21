function populateModelNames(modelNameComboBoxId) {
	showNotification("Loading model names. Please wait ... ");
	console.log("Getting model names to populate in combo box: " + modelNameComboBoxId);
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
		console.log("Model Names Response obtained:: " + response);

		// Empty the list before adding new model names
		$("#" + modelNameComboBoxId).empty();
		
		// Populate the results table on the page.
		$.each(response, 
				function(i, model) {
					$("#" + modelNameComboBoxId).append($('<option></option>').val(model.modelName).html(model.modelName));
				});
		try {
			callBackAfterPopulateModelNames(modelNameComboBoxId);
		} catch (err) {
			console.log("INFO: Callback method not implemented. Error details below:");
			console.log(err);
		}
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
		console.log("Model Versions Response obtained:: " + response);

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
		try {
			callBackAfterPopulateModelVersions(modelNameComboBoxId, modelVersionComboBoxId);
		} catch (err) {
			console.log("INFO: Callback method not implemented. Error details below:");
			console.log(err);
		}
	});

	// callback handler that will be called on failure
	request.fail(function(jqXHR, textStatus, errorThrown) {
		// log the error to the console
		console.log("The following error occured: " + textStatus, errorThrown);
	});
}
