var monthSelectE;
var daySelectE;
var type;
var year;
var month;
var day;
document.addEventListener('DOMContentLoaded', function () {
	var calendarEl = document.getElementById('calendar');

	var calendar = new FullCalendar.Calendar(calendarEl, {
		plugins: ['interaction', 'dayGrid', 'timeGrid', 'list'],
		timeZone: 'UTC',
		themeSystem: 'bootstrap',
		businessHours: true, // display business hours
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
		},
		weekNumbers: false,
		navLinks: true, // can click day/week names to navigate views
		editable: false,
		eventLimit: true, // allow "more" link when too many events
		events: "SetEvents",
		eventTimeFormat: { hour: '2-digit', minute: '2-digit', hour12: false },
		displayEventEnd:true,
		dateClick: function (info) {
			$('#messageModal1').modal('show');
			$('#year').val(Number(info.dateStr.substring(0,4)));
			$('#monthSelectS').val(Number(info.dateStr.substring(5,7)));
			$('#daySelectS').val(Number(info.dateStr.substring(8,10)));
			$('#monthSelectS').attr('disabled', true);
			$('#daySelectS').attr('disabled', true);
			year = Number(info.dateStr.substring(0,4));
			month = Number(info.dateStr.substring(5,7));
			day = Number(info.dateStr.substring(8,10));
			var request = {
				year : Number(info.dateStr.substring(0,4)),
				month : Number(info.dateStr.substring(5,7)),
				day : Number(info.dateStr.substring(8,10)),
				type : 2
			};
			$.ajax({
				url : "SearchRental",
				type : "POST",
				data : request,
				success : function(data) {
					monthSelectE = data["monthSelectE"];
					daySelectE = data["daySelectE"];
					notEquipment = Number(data["notEquipment"]);
					numbersEquipment = Number(data["numbersEquipment"]);
					type = data["type"];
					for (let i = 1; i <= numbersEquipment; i++) {
						$('select#equipmentSelect option[value='+data["equipmentId"+i]+']').attr('disabled', false);
					}
					for (let i = 1; i <= notEquipment; i++) {
						$('select#equipmentSelect option[value='+data["notEquipmentId"+i]+']').attr('disabled', true);
					}
					$('#monthSelectE').val(Number(data["monthSelectE"]));
					$('#daySelectE').val(Number(data["daySelectE"]));


					$('#equipmentSelect').val(data["okEquipmentId"]);
					$('#imgR').attr('src', "resources/image/equipment/"+data["okEquipmentId"]+".jpeg");

					if(type==2){
						for (let i = 1; i <= 31; i++) {
							if(i>Number(data["daySelectE"])){
								$('select#daySelectE option[value='+i+']').attr('disabled', true);
							}else{
								$('select#daySelectE option[value='+i+']').attr('disabled', false);
							}
						}
					}else{
						for (let i = 1; i <= 31; i++) {
							if(i<Number(info.dateStr.substring(8,10))){
								$('select#daySelectE option[value='+i+']').attr('disabled', true);
							}else{
								$('select#daySelectE option[value='+i+']').attr('disabled', false);
							}
						}
					}
					changeMonthSelect(Number(data["monthSelectE"]));
					if(type==2){
						for (let i = Number(data["monthSelectE"]); i <= Number(data["monthSelectE"])+12; i++) {
							if(Number(data["monthSelectE"])<Number(info.dateStr.substring(5,7))){
								if(i>=Number(info.dateStr.substring(5,7))&&i<=Number(data["monthSelectE"])+12){
									if(i>12){
										$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', false);
									}else{
										$('select#monthSelectE option[value='+i+']').attr('disabled', false);
									}
								}else{
									if(i>12){
										$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', true);
									}else{
										$('select#monthSelectE option[value='+i+']').attr('disabled', true);
									}
								}
							}else{
								if(i>12){
									if((i-12)>=Number(info.dateStr.substring(5,7))&&(i-12)<=Number(data["monthSelectE"])){
										$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', false);
									}else{
										$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', true);
									}
								}else{
									if(i>=Number(info.dateStr.substring(5,7))&&i<=Number(data["monthSelectE"])){
										$('select#monthSelectE option[value='+i+']').attr('disabled', false);
									}else{
										$('select#monthSelectE option[value='+i+']').attr('disabled', true);
									}
								}
							}
						}
					}else{
						for (let i = 1; i <= 12; i++) {
							if(i==Number(data["monthSelectE"])){
								$('select#monthSelectE option[value='+i+']').attr('disabled', false);
							}else{
								$('select#monthSelectE option[value='+i+']').attr('disabled', true);
							}
							if(Number(data["monthSelectE"])==12){
								if(i==1){
									$('select#monthSelectE option[value='+i+']').attr('disabled', false);
								}
							}else{
								if(i==Number(data["monthSelectE"])+1){
									$('select#monthSelectE option[value='+i+']').attr('disabled', false);
								}
							}
						}
					}
				}
			});

		},

		eventClick: function(info) {
			$('#messageModal2_'+info.event.id).modal('show');
		}

	});
			calendar.render();
});


$('#monthSelectE').change(function() {
	changeMonthSelectE($('#monthSelectE').val());
});


$('#daySelectE').change(function() {
	changeDaySelectE($('#monthSelectE').val(),$('#daySelectE').val())
});

$('#equipmentSelect').change(function() {
	var request = {
			id : $('#equipmentSelect').val(),
			year : year,
			month : month,
			day : day,
			type : 3
	};
	$.ajax({
		url : "SearchRental",
		type : "POST",
		data : request,
		success : function(data) {
			$('#monthSelectE').val(Number(data["monthSelectE"]));
			$('#daySelectE').val(Number(data["daySelectE"]));
			monthSelectE = data["monthSelectE"];
			daySelectE = data["daySelectE"];
			type = data["type"];
			changeMonthSelectE(Number(data["monthSelectE"]));
			changeMonthSelect(Number(data["monthSelectE"]));
			changeDaySelectE(Number(data["monthSelectE"]),Number(data["daySelectE"]))
			$('#imgR').attr('src', "resources/image/equipment/"+$('#equipmentSelect').val()+".jpeg");
		}
	});
});

function changeMonthSelectE(months) {
	if(type==1){
		if(months!=monthSelectE){
			for (let i = 1; i <= 31; i++) {
				if(i>Number(day)){
					$('select#daySelectE option[value='+i+']').attr('disabled', true);
				}else{
					$('select#daySelectE option[value='+i+']').attr('disabled', false);
				}
			}
		}else{
			for (let i = 1; i <= 31; i++) {
				if(i<=Number(day)){
					$('select#daySelectE option[value='+i+']').attr('disabled', true);
				}else{
					$('select#daySelectE option[value='+i+']').attr('disabled', false);
				}
			}
		}
	}else{
		if(months==monthSelectE){
			for (let i = 1; i <= 31; i++) {
				if(i>Number(daySelectE)){
					$('select#daySelectE option[value='+i+']').attr('disabled', true);
				}else{
					$('select#daySelectE option[value='+i+']').attr('disabled', false);
				}
			}
		}else if(months==Number(month)){
			for (let i = 1; i <= 31; i++) {
				if(i>Number(day)){
					$('select#daySelectE option[value='+i+']').attr('disabled', false);
				}else{
					$('select#daySelectE option[value='+i+']').attr('disabled', true);
				}
			}
		}else{
			for (let i = 1; i <= 31; i++) {
				$('select#daySelectE option[value='+i+']').attr('disabled', false);
			}
		}
	}
}

function changeDaySelectE(months,days) {
	if(type==1){
		if(type==1){
			if(months == monthSelectE ){
				if(changeDaySelect(days,months)==1){
					$('select#monthSelectE option[value='+(Number(months)+1)+']').attr('disabled', false);
				}else{
					$('select#monthSelectE option[value='+(Number(months)+1)+']').attr('disabled', true);
				}
			}else{
				if(changeDaySelect(days,months)==1){
					$('select#monthSelectE option[value='+(Number(months)-1)+']').attr('disabled', false);
				}else{
					$('select#monthSelectE option[value='+(Number(months)-1)+']').attr('disabled', true);
				}
			}
		}
	}else{
		for (let i = Number(monthSelectE); i <= Number(monthSelectE)+12; i++) {
			if(Number(monthSelectE)<Number(month)){
				if(i>=Number(month)&&i<=Number(monthSelectE)+12){
					if(i>12){
						if(changeDaySelect(days,(i-12))==1){
							$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', false);
						}else{
							$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', true);
						}
					}else{
						if(changeDaySelect(days,i)==1){
							$('select#monthSelectE option[value='+i+']').attr('disabled', false);
						}else{
							$('select#monthSelectE option[value='+i+']').attr('disabled', true);
						}
					}
				}else{
					if(i>12){
						if(changeDaySelect(days,(i-12))==1){
							$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', false);
						}else{
							$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', true);
						}
					}else{
						if(changeDaySelect(days,i)==1){
							$('select#monthSelectE option[value='+i+']').attr('disabled', false);
						}else{
							$('select#monthSelectE option[value='+i+']').attr('disabled', true);
						}
					}
				}
			}else{
				if(i>12){
					if((i-12)>=Number(month)&&(i-12)<=Number(monthSelectE)){
						$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', false);
					}else{
						$('select#monthSelectE option[value='+(i-12)+']').attr('disabled', true);
					}
				}else{
					if(i>=Number(month)&&i<=Number(monthSelectE)){
						$('select#monthSelectE option[value='+i+']').attr('disabled', false);
					}else{
						$('select#monthSelectE option[value='+i+']').attr('disabled', true);
					}
				}
			}
		}
	}
}
