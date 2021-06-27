var authority = 1;
$(document).ready(function() {
	changeAction();
});

function changeAction() {
	var request = {
	};
	$.ajax({
		url : "Calendar",
		type : "POST",
		data : request,
		success : function(data) {
		}
	});
}