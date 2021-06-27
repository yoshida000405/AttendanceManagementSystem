html1 = '<tr id="rental"><td class="p-0 m-0"><h5 class="p-2 m-0" id="id"></h5></td><td class="p-0 m-0"><h5 class="p-2 m-0" id="equipment"></h5></td><td class="p-0 m-0"><h5 class="p-2 m-0" id="state"></h5></td><td class="p-0 m-0"><h5 class="p-2 m-0" id="reservation"></h5></td><td class="p-0 m-0"><h5 class="p-2 m-0" id="purchase"></h5></td></tr>';
html2 = '<tr id="rental"><td class="p-0 m-0"><h5 class="p-2 m-0" id="equipment"></h5></td><td class="p-0 m-0"><h5 class="p-2 m-0" id="state"></h5></td><td class="p-0 m-0"><h5 class="p-2 m-0" id="finish"></h5></td></tr>';
var equipmentModal = '<div class="modal fade" id="equipmentModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body"><div class="overflow-auto" style="width:100%;height:500px;"><img width="80%" height="80%" src="" id="img" alt="準備中" class="equipmentIMG" border="0" /><div><h5 class="mt-3" id="title"></div><div></h5><h5 class="mt-3" id="culmn1title"><h5 class="mt-3" id="culmn1value"></h5></div><div><h5 class="mt-3 d-inline-block" id="culmn2title"><h5 class="mt-3 ml-3 d-inline-block" id="culmn2value"></div><div><h5 class="mt-3 d-inline-block" id="culmn3title"></h5><h5 class="mt-3 ml-3 d-inline-block" id="culmn3value"></h5></div></div></div></div></div></div>';
var stateModal1 = '<div class="modal fade" id="stateModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body"><div class="col-12 mt-2 text-center"><h5>申請日</h5><h5 class="mt-3" id="requestDate"></h5><h5 class="mt-3">期間</h5><h5 id="targetDate"></h5><h5 class="mt-5" id="waitNumber"></h5></div><div class="col-12 mt-5 text-center clearfix"><div class="float-left w-50"><button type="submit" class="btn btn-primary" id="permit">開始</button></div><div class="float-left w-50"><button type="submit" class="btn btn-primary" id="cancel">取消</button></div><div class="d-none"><input id="period" type="text" class="form-control" name="period"></div></div><div class="modal-footer"></div></div></div></div>';
var stateModal2 = '<div class="modal fade" id="stateModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body"><div class="col-12 mt-2 text-center"><h5>使用者</h5><h5 class="mt-3" id="playStaff"></h5><h5 class="mt-3">期間</h5><h5 id="targetDate"></h5></div><div class="modal-footer"></div></div></div></div>';
var stateModal3 = '<div class="modal fade" id="stateModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body"><div class="col-12 mt-2 text-center"><h5>期間</h5><h5 id="targetDate"></h5></div><div class="col-12 mt-5 text-right"><button type="submit" class="btn btn-primary" id="cancel">返却</button></div></div></div></div>';
var reservationModal = '<div class="modal fade" id="reservationModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span>×</span></button></div><div class="modal-body" id="reservationModalContent"></div></div></div></div>';
var reservationModalContent = '<div class="col-12 mt-4 text-center"><h5>申請者</h5><h5 class="mt-3" id="requestStaff"></h5><h5 class="mt-3">申請日</h5><h5 class="mt-3" id="requestDate"><h5 class="mt-3">期間</h5><h5 id="targetDateR"></h5></div></div>';
var numbersEquipment = 0;
var numbersRental = 0;
var authority = 1;
var numbers=0;
var date = {};
var inputCheck=0;
var monthSelectS=0;
var daySelectS=0;

$(document).ready(function() {
	todayMonth = (new Date().getMonth() + 1);
	changePage(todayMonth);
});

function appendForm(){
	numbers++;
	$('#append1').append(html1);
	$('#rental').attr('id', 'rental'+numbers);
	$('#id').attr('id', 'id'+numbers);
	$('#equipment').attr('id', 'equipment'+numbers);
	$('#state').attr('id', 'state'+numbers);
	$('#reservation').attr('id', 'reservation'+numbers);
	$('#purchase').attr('id', 'purchase'+numbers);
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

function changePage(num) {
	var request = {
			month : num,
			type : 1
	};
	$.ajax({
		url : "SearchRental",
		type : "POST",
		data : request,
		success : function(data) {
			authority = data["authority"];
			if(authority==1){
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
			if(authority==2){
				for (let i = 1; i <= numbersEquipment; i++) {
					$('#rental' + i).remove();
					$('#equipmentModal' + i).remove();
					$('#stateModal' + i).remove();
					$('#reservationModal' + i).remove();
				}
			}else{

			}
			numbersEquipment = Number(data["numbersEquipment"]);
			numbersRental = Number(data["numbersRental"]);

			number=1;
			for (let i = 1; i <= numbersEquipment; i++) {
				appendForm()
				$('#id').attr('id', 'id'+i);
				$('#id'+i).text(data["equipmentId"+i]);
				$('#equipment').attr('id', 'equipment'+i);
				$('#append1').append(equipmentModal);
				$('#equipment'+i).append('<a data-toggle="modal" data-target="#equipmentModal'+i+'" id="equipmentText"></a>');
				$('#equipmentText').attr('id', 'equipmentText'+i);
				$('#equipmentModal').attr('id', 'equipmentModal'+i);
				$('#equipmentText'+i).text(data["equipment"+i]);
				$('#state').attr('id', 'state'+i);
				$('#append1').append(stateModal2);
				$('#state'+i).append('<a data-toggle="modal" data-target="#stateModal'+i+'" id="stateText"></a>');
				$('#stateText').attr('id', 'stateText'+i);
				$('#stateModal').attr('id', 'stateModal'+i);
				if(data["flag"+i]==1){
					$('#stateText'+i).text("レンタル中");
				}else{
					$('#stateText'+i).attr('data-target','');
					$('#stateText'+i).text("貸し出し可");
				}
				$('#playStaff').attr('id', 'playStaff'+i);
				$('#playStaff'+i).text(data["playStaff"+i]);
				$('#targetDate').attr('id', 'targetDate'+i);
				$('#reservation').attr('id', 'reservation'+i);
				$('#append1').append(reservationModal);
				$('#reservation'+i).append('<a data-toggle="modal" data-target="#reservationModal'+i+'" id="reservationText"></a>');
				$('#reservationText').attr('id', 'reservationText'+i);
				$('#reservationModal').attr('id', 'reservationModal'+i);
				$('#reservationModalContent').attr('id', 'reservationModalContent'+i);
				$('#reservationText'+i).text(data["reservationNumber"+i]);
				if(data["reservationNumber"+i]==0){
					$('#reservationText'+i).attr('data-target','');
				}
				if(data["flag"+i]==1){
					end = number+Number(data["reservationNumber"+i])+1;
				}else{
					end = number+Number(data["reservationNumber"+i]);
				}

				for(let j=number; j<end;j++){
					if(j==number&&data["flag"+i]==1){
						$('#targetDate'+i).text(data["start"+j].substring(5,10)+"〜"+data["finish"+j].substring(5,10));
					}else{
						$('#reservationModalContent'+i).append(reservationModalContent);
						$('#requestStaff').attr('id', 'requestStaff'+j);
						$('#requestStaff'+j).text(data["staffName"+j]);
						$('#requestDate').attr('id', 'requestDate'+j);
						$('#requestDate'+j).text(data["request"+j]);
						$('#targetDateR').attr('id', 'targetDateR'+j);
						$('#targetDateR'+j).text(data["start"+j].substring(5,10)+"〜"+data["finish"+j].substring(5,10));
						$('#cancel').attr('id', 'cancel'+j);
						$('#cancel'+j).val(data["rentalId"+j]);
						$('#cancel'+j).on('click', function() {
							cancel($('#cancel'+j).val(),1);
						});
					}
				}
				if(data["flag"+i]==1){
					number+=Number(data["reservationNumber"+i])+1;
				}else{
					number+=Number(data["reservationNumber"+i]);
				}
				$('#purchase').attr('id', 'purchase'+i);
				$('#purchase'+i).text(data["purchase"+i]);

				$('#img').attr('id', 'img'+i);
				$('#img'+i).attr('src', "resources/image/equipment/"+data["equipmentId"+i]+".jpeg");
				$('#title').attr('id', 'title'+i);
				$('#culmn1title').attr('id', 'culmn1title'+i);
				$('#culmn2title').attr('id', 'culmn2title'+i);
				$('#culmn3title').attr('id', 'culmn3title'+i);
				$('#culmn1value').attr('id', 'culmn1value'+i);
				$('#culmn2value').attr('id', 'culmn2value'+i);
				$('#culmn3value').attr('id', 'culmn3value'+i);
				$('#title'+i).text(data["equipment"+i]);
				if(data["division"+i]==1){
					$('#culmn1title'+i).text("CPU");
					$('#culmn2title'+i).text("メモリ");
					$('#culmn3title'+i).text("HDD/SSD");
					$('#culmn1value'+i).text(data["cpu"+i]);
					$('#culmn2value'+i).text(data["memory"+i]);
					$('#culmn3value'+i).text(data["volume"+i]);
				}
			}

		}
	});
}

function request() {
	inputCheck=0;
	var answer = confirm('申請してもよろしいですか？');
	if(answer){
		var request = {
				startMonth : $('#monthSelectS').val(),
				startDay : $('#daySelectS').val(),
				finishMonth : $('#monthSelectE').val(),
				finishDay : $('#daySelectE').val(),
				equipmentId : $('#equipmentSelect').val(),
				type : $('#type').val(),
				period : $('#daySelect').val(),
				monthSelectS : monthSelectS,
				daySelectS : daySelectS
		};
		if(type==2){
			if($('#monthSelectS').val()==$('#monthSelectE').val()){
				if($('#daySelectS').val()>$('#daySelectE').val()){
					inputCheck=1;
					alert("正しい期間を入力してください!");
				}
			}else if(Number($('#monthSelectS').val())==Number($('#monthSelectE').val())-1){
				if($('#daySelectS').val()<$('#daySelectE').val()){
					inputCheck=1;
					alert("正しい期間を入力してください!");
				}
			}
		}
		if(inputCheck==0){
			$.ajax({
				url : "RequestRental",
				type : "POST",
				data : request,
				success : function(data) {
					location.reload();
				}
			});
		}
	}
}

function permit(id,period) {
	var answer = confirm('開始してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			period : period
	};
	$.ajax({
		url : "PermitRental",
		type : "POST",
		data : request,
		success : function(data) {
			location.reload();
		}
	});
	}
}

function cancel(id,type) {
	if(type==1){
		var answer = confirm('取り消してもよろしいですか？');
	}else{
		var answer = confirm('返却してもよろしいですか？');
	}
	if(answer){
	var request = {
			id : id,
			type : type
	};
	$.ajax({
		url : "CancelRental",
		type : "POST",
		data : request,
		success : function(data) {
			location.reload();
		}
	});
	}
}