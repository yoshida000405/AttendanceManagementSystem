function editEvents(info){
    var id = info.event.id;
	var title = info.event.title;

	var yearStart = info.event.start.getFullYear();
    var monthStart = info.event.start.getMonth() + 1;
	var dayStart = info.event.start.getDate();
	var hourStart = info.event.start.getUTCHours();
	var minStart = info.event.start.getMinutes();
	if(monthStart < 10){
		var month = "0" + String(monthStart);
	}else{
		var month = String(monthStart);
	}
	if(dayStart < 10){
		var day = "0" + String(dayStart);
	}else{
		var day = String(dayStart);
	}
	if(hourStart < 10){
		var hour = "0" + String(hourStart);
	}else{
		var hour = String(hourStart);
	}
	if(minStart < 10){
		var min = "0" + String(minStart);
	}else{
		var min = String(minStart);
	}

	$('#idEdit').val([id]);
	$('#eventsEdit').val([title]);
	$('#yearStartEdit').val(yearStart);
	$('#monthStartEdit').val(month);
	$('#dayStartEdit').val(day);
	$('#hourStartEdit').val(hour);
	$('#minStartEdit').val(min);

	var yearEnd = info.event.end.getFullYear();
    var monthEnd = info.event.end.getMonth() + 1;
	var dayEnd = info.event.end.getDate();
	var hourEnd = info.event.end.getUTCHours();
	var minEnd = info.event.end.getMinutes();
	if(monthEnd < 10){
		var month = "0" + String(monthEnd);
	}else{
		var month = String(monthEnd);
	}
	if(dayEnd < 10){
		var day = "0" + String(dayEnd);
	}else{
		var day = String(dayEnd);
	}
	if(hourEnd < 10){
		var hour = "0" + String(hourEnd);
	}else{
		var hour = String(hourEnd);
	}
	if(minEnd < 10){
		var min = "0" + String(minEnd);
	}else{
		var min = String(minEnd);
	}

	$('#yearEndEdit').val(yearEnd);
	$('#monthEndEdit').val(month);
	$('#dayEndEdit').val(day);
	$('#hourEndEdit').val(hour);
	$('#minEndEdit').val(min);

	document.getElementById("editBtn").click();

	info.el.style.borderColor = 'red';

}

function addEvents(calendar,info){

	$('#yearStartAdd').val(info.dateStr.substr( 0, 4 ));
	$('#monthStartAdd').val(info.dateStr.substr( 5, 2 ));
	$('#dayStartAdd').val(info.dateStr.substr( 8, 2 ));
	$('#hourStartAdd').val("09");
	$('#minStartAdd').val("00");

	$('#yearEndAdd').val(info.dateStr.substr( 0, 4 ));
	$('#monthEndAdd').val(info.dateStr.substr( 5, 2 ));
	$('#dayEndAdd').val(info.dateStr.substr( 8, 2 ));
	$('#hourEndAdd').val("10");
	$('#minEndAdd').val("00");

	document.getElementById("addBtn").click();

}