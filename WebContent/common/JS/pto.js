html = '<tr id="pto"><th scope="row" class="p-0 m-0"><h5 class="p-2 m-0" id="id"></h5></th><td class="p-0 m-0"><h5 class="p-2 m-0" id="name"></h5></td><td class="p-0 m-0"><div class="w-25 h-100 float-left m-0 border-right"><h5 class="p-2 m-0" id="wait"></h5></div><div class="w-25 h-100 float-left border-right"><h5 class="p-2 m-0" id="consume"></h5></div><div class="w-25 h-100 float-left border-right"><h5 class="p-2 m-0" id="lost"></h5></div><div class="w-25 h-100 float-left"><h5 class="p-2 m-0" id="remain"></h5></div></td><th scope="row" class="p-0 m-0"><button type="submit" class="btn btn-primary" id="output">出力</button></th></tr>';
var htmlWaitModal = '<div class="modal fade" id="sampleWaitModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body" id="waitModal"></div></div></div></div>';
var htmlConsumeModal = '<div class="modal fade" id="sampleConsumeModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body"><div class="overflow-auto" id="consumeModal" style="width:100%;height:500px;"></div></div></div></div></div>';
var htmlWait = '<div><h5 class="mt-4 mr-1 ml-1 d-inline-block" id="targetMonthW"></h5><h5 class="mt-4 d-inline-block">/</h5><h5 class="mt-4 mr-1 ml-1 d-inline-block" id="targetDayW"></h5><h5 class="mt-4 ml-5  d-inline-block" id="divisionW"></h5><h5 class="mt-4" id="reasonW"></h5><button type="submit" class="btn btn-primary mt-4 permitW" id="permitW">承認</button><button type="submit" class="btn btn-primary ml-4 mt-4 cancelW" id="cancelW">取消</button></div>';
var htmlConsume = '<div><h5 class="mt-4 mr-1 ml-1 d-inline-block" id="targetMonthC"></h5><h5 class="mt-4 d-inline-block">/</h5><h5 class="mt-4 mr-1 ml-1 d-inline-block" id="targetDayC"></h5><h5 class="mt-4 ml-5  d-inline-block" id="divisionC"></h5><button type="submit" class="btn btn-primary ml-4 mt-4 cancelC" id="cancelC">取消</button></div>';
var numbersAplication = 0;
var authority = 1;
var waitNumber = 1;
var consumeNumber = 1;
var inputCheck=0;

$(document).ready(function() {
	todayYear = (new Date().getYear() + 1900);
	changeYear(todayYear,0);
	for(let i=todayYear; i>todayYear-10; i--){
		if(new Date().getMonth()+1>10){
			$('#year').append($('<option>').html((i+1)+"年").val(i+1));
		}
		if(i>2015){
			$('#year').append($('<option>').html(i+"年").val(i));
		}
	}
	setTimeout(function() {
		$('#year').val(todayYear);
	}, 1000);
});

$('#year').change(function() {
	changeYear($('#year').val(),1);
});

$('#request').on('click', function() {
	request();
});

$('#monthSelect').change(function() {
	changeMonthSlect($('#monthSelect').val());
});

$('#daySelect').change(function() {
	changeDaySlect($('#daySelect').val());
});
function appendForm(){
	numbersAplication++;
	$('#append').append(html);
	$('#pto').attr('id', 'pto'+numbersAplication);
	$('#id').attr('id', 'id'+numbersAplication);
	$('#name').attr('id', 'name'+numbersAplication);
	$('#wait').attr('id', 'wait'+numbersAplication);
	$('#consume').attr('id', 'consume'+numbersAplication);
	$('#lost').attr('id', 'lost'+numbersAplication);
	$('#remain').attr('id', 'remain'+numbersAplication);
	$('#output').attr('id', 'output'+numbersAplication);

}
function changeDaySlect(num) {
	if(num==31){
		$('select#monthSelect option[value=2]').attr('disabled', true);
		$('select#monthSelect option[value=4]').attr('disabled', true);
		$('select#monthSelect option[value=6]').attr('disabled', true);
		$('select#monthSelect option[value=9]').attr('disabled', true);
		$('select#monthSelect option[value=11]').attr('disabled', true);
	}else if(num==30){
		$('select#monthSelect option[value=2]').attr('disabled', true);
		$('select#monthSelect option[value=4]').attr('disabled', false);
		$('select#monthSelect option[value=6]').attr('disabled', false);
		$('select#monthSelect option[value=9]').attr('disabled', false);
		$('select#monthSelect option[value=11]').attr('disabled', false);

	}else if(num==29){
		if(new Date().getMonth()+1>=$('#monthSelect').val()){
			if(new Date().getYear()%4==0){
				$('select#monthSelect option[value=2]').attr('disabled', false);
				$('select#monthSelect option[value=4]').attr('disabled', false);
				$('select#monthSelect option[value=6]').attr('disabled', false);
				$('select#monthSelect option[value=9]').attr('disabled', false);
				$('select#monthSelect option[value=11]').attr('disabled', false);
			}else{
				$('select#monthSelect option[value=2]').attr('disabled', true);
				$('select#monthSelect option[value=4]').attr('disabled', false);
				$('select#monthSelect option[value=6]').attr('disabled', false);
				$('select#monthSelect option[value=9]').attr('disabled', false);
				$('select#monthSelect option[value=11]').attr('disabled', false);
			}
		}else{
			if((new Date().getYear()+1)%4==0){
				$('select#monthSelect option[value=2]').attr('disabled', false);
				$('select#monthSelect option[value=4]').attr('disabled', false);
				$('select#monthSelect option[value=6]').attr('disabled', false);
				$('select#monthSelect option[value=9]').attr('disabled', false);
				$('select#monthSelect option[value=11]').attr('disabled', false);
			}else{
				$('select#monthSelect option[value=2]').attr('disabled', true);
				$('select#monthSelect option[value=4]').attr('disabled', false);
				$('select#monthSelect option[value=6]').attr('disabled', false);
				$('select#monthSelect option[value=9]').attr('disabled', false);
				$('select#monthSelect option[value=11]').attr('disabled', false);
			}
		}
	}else{
		$('select#monthSelect option[value=2]').attr('disabled', false);
		$('select#monthSelect option[value=4]').attr('disabled', false);
		$('select#monthSelect option[value=6]').attr('disabled', false);
		$('select#monthSelect option[value=9]').attr('disabled', false);
		$('select#monthSelect option[value=11]').attr('disabled', false);
	}
}

function changeMonthSlect(num) {
	if(num==2){
		if(new Date().getMonth()+1>=num){
			if(new Date().getYear()%4==0){
				month = "22";
			}else{
				month = num;
			}
		}else{
			if((new Date().getYear()+1)%4==0){
				month = "22";
			}else{
				month = num;
			}
		}
	}else{
		month = num;
	}
	if (month == "22") {
		$('select#daySelect option[value=29]').attr('disabled', false);
		$('select#daySelect option[value=30]').attr('disabled', true);
		$('select#daySelect option[value=31]').attr('disabled', true);
	} else {
		switch (month) {
		case "2":
			$('select#daySelect option[value=29]').attr('disabled', true);
			$('select#daySelect option[value=30]').attr('disabled', true);
			$('select#daySelect option[value=31]').attr('disabled', true);
			break;
		case "1":
		case "3":
		case "5":
		case "7":
		case "8":
		case "10":
		case "12":
			$('select#daySelect option[value=29]').attr('disabled', false);
			$('select#daySelect option[value=30]').attr('disabled', false);
			break;
		case "4":
		case "6":
		case "9":
		case "11":
			$('select#daySelect option[value=29]').attr('disabled', false);
			$('select#daySelect option[value=31]').attr('disabled', true);
			break;
		}
	}
}
function changeYear(num,check) {
	var request = {
			year : num
	};
	$.ajax({
		url : "SearchPTO",
		type : "POST",
		data : request,
		success : function(data) {
			if(check==0){
				for(let i=1; i<=data["numbersMembers"]; i++){
					$('#userSelect').append($('<option>').html(data["member"+i]).val(data["memberId"+i]));
				}
			}
			authority = data["authority"];
			if(authority==1){
				$("#nextGiveDate").text("次回付与日 : "+data["nextGiveDate"]);
				$('#managedNav').remove();
				$('#editInfoNav').remove();
				$('#registerNav').remove();
				$('#mailNav').remove();
				$('#settingNav').remove();
				$('#homeNav').attr('class', "nav-item col-md-18 text-center");
				$('#attendanceNav').attr('class', "nav-item col-md-18 text-center");
				$('#demandNav').attr('class', "nav-item col-md-18 text-center");
				$('#skillNav').attr('class', "nav-item col-md-18 text-center");
				$('#ptoNav').attr('class', "nav-item col-md-18 text-center");
				$('#boardNav').attr('class', "nav-item col-md-18 text-center");
				$('#rentalNav').attr('class', "nav-item col-md-18 text-center");
				$('#logoutNav').attr('class', "nav-item col-md-18 text-center");
			}
			//モーダル情報をリセット
			for (let i = 1; i <= numbersAplication; i++) {
				if(authority==2){
					$('#pto' + i).remove();
				}
				$('#sampleWaitModal' + i).remove();
				$('#sampleDoneModal' + i).remove();
				$('#sampleConsumeModal' + i).remove();
				waitNumber = 1;
				doneNumber = 1;
				consumeNumber = 1;
			}
			numbersAplication = 0;
			numbersUser = Number(data["numbersUser"]);
			for (let i = 1; i <= numbersUser; i++) {
				if(authority==2){
					appendForm();
					$('#id'+i).text(data["id"+i]);
					$('#name'+i).text(data["name"+i]);
					$('#output' + i).val(data["id"+i]);
					$('#output'+i).on('click', function() {
						output($('#output' + i).val());
					});
				}else{
					numbersAplication++;
				}
				$('#append').append(htmlWaitModal);
				$('#append').append(htmlConsumeModal);

				$('#wait'+i).append('<a data-toggle="modal" data-target="#sampleWaitModal'+i+'" id="waitText"></a>');
				$('#waitText').attr('id', 'waitText'+i);
				$('#sampleWaitModal').attr('id', 'sampleWaitModal'+i);
				$('#waitModal').attr('id', 'waitModal'+i);
				$('#waitText'+i).text(data["wait"+i]);
				if(data["wait"+i]==0){
					$('#waitText'+i).attr('data-target','');
				}

				$('#consume'+i).append('<a data-toggle="modal" data-target="#sampleConsumeModal'+i+'" id="consumeText"></a>');
				$('#consumeText').attr('id', 'consumeText'+i);
				$('#sampleConsumeModal').attr('id', 'sampleConsumeModal'+i);
				$('#consumeModal').attr('id', 'consumeModal'+i);
				$('#consumeText'+i).text(data["consume"+i]);
				if(data["consume"+i]==0){
					$('#consumeText'+i).attr('data-target','');
				}

				$('#lost'+i).text(data["lost"+i]);
				$('#remain'+i).text(data["remain"+i]);
				for(let j=waitNumber; j<waitNumber+Number(data["wait"+i]); j++){
					$('#waitModal'+i).append(htmlWait);
					$('#targetMonthW').attr('id', 'targetMonthW'+j);
					$('#targetDayW').attr('id', 'targetDayW'+j);
					$('#divisionW').attr('id', 'divisionW'+j);
					$('#reasonW').attr('id', 'reasonW'+j);
					$('#permitW').attr('id', 'permitW'+j);
					$('#cancelW').attr('id', 'cancelW'+j);
				}
				for(let j=consumeNumber; j<consumeNumber+Math.ceil(Number(data["consumeNumber"+i])); j++){
					$('#consumeModal'+i).append(htmlConsume);
					$('#targetMonthC').attr('id', 'targetMonthC'+j);
					$('#targetDayC').attr('id', 'targetDayC'+j);
					$('#divisionC').attr('id', 'divisionC'+j);
					$('#cancelC').attr('id', 'cancelC'+j);
				}
				waitNumber+=Number(data["wait"+i]);
				consumeNumber+=Math.ceil(Number(data["consumeNumber"+i]));
			}

			numbersPTO = Number(data["numbersPTO"]);
			for (let i=1,w=1,d=1; i <= numbersPTO; i++,w++,d++) {
				if(data["flag"+i]==0){
					$('#targetMonthW'+w).text(data["targetMonth"+i]);
					$('#targetDayW'+w).text(data["targetDay"+i]);
					$('#reasonW'+w).text(data["reason"+i]);
					$('#cancelW'+w).val(data["RecordIdW"+i]);
					$('#cancelW'+w).on('click', function() {
						cancel($('#cancelW'+w).val(),1);
					});
					if(authority==1){
						$('#permitW'+w).remove();
						$('#cancelW'+w).attr('class',"btn btn-primary mt-3");
					}else{
						$('#permitW'+w).val(data["RecordIdW"+i]);
						$('#permitW'+w).on('click', function() {
							permit($('#permitW'+w).val());
						});
					}
					switch(data["division"+i]){
					case "1":
						$('#divisionW'+w).text("終日");
						break;
					case "2":
						$('#divisionW'+w).text("午前休");
						break;
					case "3":
						$('#divisionW'+w).text("午後休");
						break;
					}
					d--;
				}else{
					w--;
				}
			}
			numbersPTOConsume = Number(data["numbersPTOConsume"]);
			for (let i=1; i <= numbersPTOConsume; i++) {
				$('#targetMonthC'+i).text(data["targetMonthC"+i]);
				$('#targetDayC'+i).text(data["targetDayC"+i]);
				switch(data["divisionC"+i]){
				case "1":
					$('#divisionC'+i).text("終日");
					break;
				case "2":
					$('#divisionC'+i).text("午前休");
					break;
				case "3":
					$('#divisionC'+i).text("午後休");
					break;
				}
				if(authority==1){
					$('#cancelC'+i).remove();
				}else{
					$('#cancelC'+i).attr('class',"btn btn-primary ml-5");
					$('#cancelC'+i).val(data["RecordIdC"+i]);
					$('#cancelC'+i).on('click', function() {
						cancel($('#cancelC'+i).val(),2);
					});
					nowYear = new Date().getFullYear();
					nowMonth = new Date().getMonth();
					if(nowYear!=$('#year').val()){
						if(nowYear-1==$('#year').val()){
							if(nowMonth+12!=Number(data["targetMonthC"+i])){
								$('#cancelC'+i).remove();
							}
						}else{
							$('#cancelC'+i).remove();
						}
					}
					if(nowMonth>0){
						if(nowMonth!=Number(data["targetMonthC"+i])&&nowMonth+1!=Number(data["targetMonthC"+i])){
							$('#cancelC'+i).remove();
						}
					}
				}
			}
			$('#monthText').text(num+"年度");
		}
	});
}

function request() {
	var answer = confirm('申請してもよろしいですか？');
	if(answer){
		inputCheck=0;
		if($('#reason').val()==""){
			alert("取得理由を入力してください！")
			inputCheck=1;
		}
		if($('#reason').val().length>199){
			alert("取得理由は200字以内で入力してください！")
			inputCheck=1;
		}
		if(inputCheck==0){
			var request = {
					month : $('#monthSelect').val(),
					day : $('#daySelect').val(),
					reason : $('#reason').val(),
					division : $('#divisionSelect').val()
			};
			if(authority==2){
				request["id"] = $('#userSelect').val();
			}
			$.ajax({
				url : "RequestPTO",
				type : "POST",
				data : request,
				success : function(data) {
					if(data["check"]=="error"){
						alert("既に申請されています！")
					}else{
						location.reload();
					}
				}
			});
		}
	}
}

function permit(id) {
	var answer = confirm('承認してもよろしいですか？');
	if(answer){
	var request = {
			id : id
	};
	$.ajax({
		url : "PermitPTO",
		type : "POST",
		data : request,
		success : function(data) {
			location.reload();
		}
	});
	}
}

function cancel(id,type) {
	var answer = confirm('取り消してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			type : type
	};
	$.ajax({
		url : "CancelPTO",
		type : "POST",
		data : request,
		success : function(data) {
			location.reload();
		}
	});
	}
}

function output(id){
	var request = {
		type : 3,
		year : $('#year').val(),
		id : id,
		all : "no"
	};
	$.ajax({
		url : "OutputExcel",
		type : "POST",
		data : request,
		success : function(data) {
			window.location.href = data["url"];
		}
	});
}