// Validate for good data
function validatePage() {
	if( validateEmptyString($("#modelName").val(), 'Model Name') &&
			validateEmptyString($("#modelVersion").val(), 'Model Version') && 
			validateEmptyString($("#queryClassification").val(), 'Query Classification') && 
			validateEmptyString($("#queryName").val(), 'Query Name') &&
			validateEmptyString($("#queryString").val(), 'Query String')) {
		return true;
	}
		
	return false;
}

// Convert the data on the page to JSON
function dataToJSON() {
	return JSON.stringify({ queryClassification : $("#queryClassification").val(),
		queryName : $("#queryName").val(),
		queryString : $("#queryString").val(),
		modelName : $("#modelName").val(),
		modelVersion: $("#modelVersion").val() });
}

// Function to clear all the fields on the page,
// to get the page ready for the next query.
function clearAllFields() {
	showNotification("Query saved successfully");
    $("#queryClassification").val("");
    $("#queryName").val("");
    $("#queryString").val("");
    $("#modelName").val("");
    $("#modelVersion").val("");
}

function onLoadCreatePage() {
	$("#addBtn").click(function() {
		if(!validatePage()) {
			return false;
		}
	    showNotification("Saving information. Please wait ... ");
		request = $.ajax({
	        url: "/rest/queries",
	        type: "POST",
	        contentType: 'application/json',
	        dataType: 'json',
	        data: dataToJSON()	
	    });
		
		// callback handler that will be called on success
	    request.done(function (response, textStatus, jqXHR){
	        // log a message to the console
	        console.log("Yay, Query is added successfully!");
	        clearAllFields();
	    });

	    // callback handler that will be called on failure
	    request.fail(function (jqXHR, textStatus, errorThrown){
	        // log the error to the console
	        console.error("The following error occured: "+
	            textStatus, errorThrown
	        );
	        
	        showNotification("The following error occured: "+
	        		errorThrown);
	    });
	});
}

function onLoadShowPage() {
	request = $.ajax({
        url: "/rest/queries",
        type: "GET",
        contentType: 'application/json',
        dataType: 'json'
    });
	
	// callback handler that will be called on success
    request.done(function (response, textStatus, jqXHR){
        // log a message to the console
        console.log("Response obtained:: " + response);
        
        // Populate the results table on the page.
        $("#queriesListingTbl").append("<tr><td colspan=5 align='center'>" +
        "<b>Following is a list of all available Queries</b></td></tr>");
        var headerRow = "<tr>" + 
            "<td><b>Model Name</b></td>" +
            "<td><b>Model Version</b></td>" +
			"<td><b>Query Classification</b></td>" +
			"<td><b>Query Name</b></td>" +
			"<td><b>Query String</b></td>" +
			"<td>&nbsp;</td>" +
			"</tr>";
        $(headerRow).appendTo("#queriesListingTbl > tbody");
        $.each(response, function(i, query) {
              var deleteButton = "<input type='button' value='Delete' onclick='performDelete(" + query.id + ")' />";
        	  var queryRow = "<tr id ='" + query.id + "'>" +
				"<td>" + query.modelName + "</td>" +
				"<td>" + query.modelVersion + "</td>"+
				"<td>" + query.queryClassification + "</td>" +
                "<td>" + query.queryName + "</td>"+
                "<td>" + query.queryString + "</td>" +
                "<td>" + deleteButton + "</td>" +
				"</tr>";	  
        	  $("#queriesListingTbl").append(queryRow);
        }); 
    });

    // callback handler that will be called on failure
    request.fail(function (jqXHR, textStatus, errorThrown){
        // log the error to the console
        console.log("The following error occured: "+ textStatus, errorThrown
        );
    });
}

function performDelete(queryId) {
    console.log("Query ID = " + queryId);
    localStorage.setItem('delQueryId', queryId);
    
    req = $.ajax({
        url: "/rest/queries/" + queryId,
        type: "DELETE",
        contentType: 'application/json',
        dataType: 'json'
    });
    
    // callback handler that will be called on success
    req.done(function (response, textStatus, jqXHR){
        showNotification("Query deleted successfully.");
        var qId = localStorage.getItem('delQueryId');
        $('table#queriesListingTbl tr#' + qId).remove();
        console.log('Row with id ' + qId + ' removed from table');
        localStorage.removeItem('delQueryId');
    });
    
    // callback handler that will be called on failure
    req.fail(function (jqXHR, textStatus, errorThrown){
        // log the error to the console
        console.log("The following error occured: "+ textStatus);
    });
}


