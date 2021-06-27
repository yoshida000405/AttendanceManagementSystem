var html ='<div class="modal fade" id="messageModal2" tabindex="-1" role="dialog" aria-labelledby="messageModal2Label" style="display: none;" aria-hidden="true"><div class="modal-dialog modal-dialog-centered" role="document"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="messageModal2Label">予約情報</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><div class="overflow-auto" style="width:100%;height:500px;"><div class="col-12 mt-5 text-center"><img width="80%" height="80%" src="" id="imgR" alt="準備中" class="equipmentIMG" border="0" /></div><div id="type1" class="text-center mt-3 clearfix"><div class="col-md-5 mt-4 text-center float-left"><select name=monthSelectS id="monthSelectS" class="p-1"><option value=1>1月</option><option value=2>2月</option><option value=3>3月</option><option value=4>4月</option><option value=5>5月</option><option value=6>6月</option><option value=7>7月</option><option value=8>8月</option><option value=9>9月</option><option value=10>10月</option><option value=11>11月</option><option value=12>12月</option></select><h5 class="d-inline-block">/</h5><select name="daySelectS" id="daySelectS" class="p-1"><option value=1>1日</option><option value=2>2日</option><option value=3>3日</option><option value=4>4日</option><option value=5>5日</option><option value=6>6日</option><option value=7>7日</option><option value=8>8日</option><option value=9>9日</option><option value=10>10日</option><option value=11>11日</option><option value=12>12日</option><option value=13>13日</option><option value=14>14日</option><option value=15>15日</option><option value=16>16日</option><option value=17>17日</option><option value=18>18日</option><option value=19>19日</option><option value=20>20日</option><option value=21>21日</option><option value=22>22日</option><option value=23>23日</option><option value=24>24日</option><option value=25>25日</option><option value=26>26日</option><option value=27>27日</option><option value=28>28日</option><option value=29>29日</option><option value=30>30日</option><option value=31>31日</option></select></div><div class="col-md-2 mt-4 text-center float-left">〜</div><div class="col-md-5 mt-4 text-center float-left"><select name=monthSelectE id="monthSelectE" class="p-1"><option value=1>1月</option><option value=2>2月</option><option value=3>3月</option><option value=4>4月</option><option value=5>5月</option><option value=6>6月</option><option value=7>7月</option><option value=8>8月</option><option value=9>9月</option><option value=10>10月</option><option value=11>11月</option><option value=12>12月</option></select><h5 class="d-inline-block">/</h5><select name="daySelectE" id="daySelectE" class="p-1"><option value=1>1日</option><option value=2>2日</option><option value=3>3日</option><option value=4>4日</option><option value=5>5日</option><option value=6>6日</option><option value=7>7日</option><option value=8>8日</option><option value=9>9日</option><option value=10>10日</option><option value=11>11日</option><option value=12>12日</option><option value=13>13日</option><option value=14>14日</option><option value=15>15日</option><option value=16>16日</option><option value=17>17日</option><option value=18>18日</option><option value=19>19日</option><option value=20>20日</option><option value=21>21日</option><option value=22>22日</option><option value=23>23日</option><option value=24>24日</option><option value=25>25日</option><option value=26>26日</option><option value=27>27日</option><option value=28>28日</option><option value=29>29日</option><option value=30>30日</option><option value=31>31日</option></select></div></div><div class="col-12 mt-5 text-right"><button type="submit" class="btn btn-primary ml-3" id="changes">変更</button><button type="submit" class="btn btn-primary ml-3" id="permit">開始</button><button type="submit" class="btn btn-primary ml-3" id="cancel">取消</button><button type="submit" class="btn btn-primary ml-3" id="continuation">継続</button><button type="submit" class="btn btn-primary ml-3" id="return">返却</button></div></div></div></div></div></div></div>';
var numbersEquipment = 0;
var numbersRental = 0;
var authority = 1;
var numbers=0;
var inputCheck=0;

$(document).ready(function() {
	todayMonth = (new Date().getMonth() + 1);
	$.blockUI({
      message: 'Now Loading',
      css: {
        border: 'none',
        padding: '10px',
        backgroundColor: '#333',
        opacity: .5,
        color: '#fff'
      },
      overlayCSS: {
        backgroundColor: '#000',
        opacity: 0.6
      }
	});
	setTimeout($.unblockUI, 1000);
	setTimeout(changePage, 1000, todayMonth);
});

$('#request').on('click', function() {
	request();
});

$('#equipment').on('click', function() {
	location.href = "equipment.jsp";
});

function changeDaySelect(day,month) {
	if(day==31){
		if(month==2||month==4||month==6||month==9||month==11){
			return 0;
		}else{
			return 1;
		}
	}else if(day==30){
		if(month==2){
			return 0;
		}else{
			return 1;
		}
	}else if(day==29){
		if(new Date().getMonth()+1>=month){
			if(new Date().getYear()%4==0){
				return 1;
			}else{
				if(month==2){
					return 0;
				}else{
					return 1;
				}
			}
		}else{
			if((new Date().getYear()+1)%4==0){
				return 1;
			}else{
				if(month==2){
					return 0;
				}else{
					return 1;
				}
			}
		}
	}else{
		return 1;
	}
}

function changeMonthSelect(num) {
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
			numbersEquipment = Number(data["numbersEquipment"]);
			for (let i = 1; i <= numbersEquipment; i++) {
				$('#equipmentSelect').append($('<option>').html(data["equipment"+i]).val(data["equipmentId"+i]));
			}
			numbersRental = Number(data["numbersRental"]);
			for (let i = 1; i <= numbersRental; i++) {
				$('#append').append(html);
				$('#messageModal2').attr('id', 'messageModal2_'+data["rentalId"+i]);
				$('#imgR').attr('id', 'imgR_'+i);
				$('#imgR_'+i).attr('src', "resources/image/equipment/"+data["rentalEquipmentId"+i]+".jpeg");
				$('#monthSelectS').attr('id', 'monthSelectS'+i);
				$('#monthSelectS'+i).val(""+Number(data["start"+i].substring(5,7)));
				$('#daySelectS').attr('id', 'daySelectS'+i);
				$('#daySelectS'+i).val(Number(data["start"+i].substring(8,10)));
				$('#monthSelectE').attr('id', 'monthSelectE'+i);
				$('#monthSelectE'+i).val(Number(data["finish"+i].substring(5,7)));
				$('#daySelectE').attr('id', 'daySelectE'+i);
				$('#daySelectE'+i).val(Number(data["finish"+i].substring(8,10)));
				var date1 = new Date(Number(data["start"+i].substring(0,4)), Number(data["start"+i].substring(5,7))-1, Number(data["start"+i].substring(8,10)), 0, 0, 0);
				var date2 = new Date(Number(data["finish"+i].substring(0,4)), Number(data["finish"+i].substring(5,7))-1, Number(data["finish"+i].substring(8,10)), 0, 0, 0);
				var date3 = new Date();
				$('#changes').attr('id', 'changes'+i);
				$('#permit').attr('id', 'permit'+i);
				$('#return').attr('id', 'return'+i);
				$('#cancel').attr('id', 'cancel'+i);
				$('#continuation').attr('id', 'continuation'+i);
				$("#changes"+i).val(data["rentalId"+i]);
				$("#permit"+i).val(data["rentalId"+i]);
				$("#return"+i).val(data["rentalId"+i]);
				$("#cancel"+i).val(data["rentalId"+i]);
				$("#continuation"+i).val(data["rentalId"+i]);
				//$('#changes'+i).on('click', function() {
				//	changes(data["rentalId"+i]);
				//});
				$('#permit'+i).on('click', function() {
					permit(data["rentalId"+i]);
				});
				$('#return'+i).on('click', function() {
					returns(data["rentalId"+i]);
				});
				$('#cancel'+i).on('click', function() {
					cancel(data["rentalId"+i]);
				});
				$('#continuation'+i).on('click', function() {
					continuation(data["rentalId"+i],i);
				});
				$('#changes'+i).hide();
				$('#permit'+i).hide();
				$('#return'+i).hide();
				$('#cancel'+i).hide();
				$('#continuation'+i).hide();
				if(data["rentalFlag"+i]==0){
					if(data["staffId"+i]==data["userId"]||authority==2){
						$("#cancel"+i).show();
					}
				}
				if(data["rentalFlag"+i]==0 && date3>date1 && date3<date2){
					if(data["staffId"+i]==data["userId"]||authority==2){
						$("#permit"+i).show();
					}
				}
				if(data["rentalFlag"+i]==1 && date3>date1 && date3<date2){
					if(data["staffId"+i]==data["userId"]||authority==2){
						$("#return"+i).show();
					}
				}
				if(data["rentalFlag"+i]==1 && date3>=date2){
					if(data["staffId"+i]==data["userId"]||authority==2){
						$("#continuation"+i).show();
						$("#return"+i).show();
					}
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
				year : $('#year').val(),
				startMonth : $('#monthSelectS').val(),
				startDay : $('#daySelectS').val(),
				finishMonth : $('#monthSelectE').val(),
				finishDay : $('#daySelectE').val(),
				equipmentId : $('#equipmentSelect').val(),
				type : 1,
		};
		var date = new Date();
		if((Number($('#year').val())-1900)==date.getYear()){
			if(Number($('#monthSelectS').val())==date.getMonth()+1){
				if(Number($('#daySelectS').val())<date.getDate()){
					inputCheck=1;
					alert("予約対象期間外です!");
				}
			}else if(Number($('#monthSelectS').val())<date.getMonth()+1){
				inputCheck=1;
				alert("予約対象期間外です!");
			}
		}else if((Number($('#year').val())-1900)<date.getYear()){
			inputCheck=1;
			alert("予約対象期間外です!");
		}
		if($('#monthSelectS').val()==$('#monthSelectE').val()){
			if(Number($('#daySelectS').val())>=Number($('#daySelectE').val())){
				inputCheck=1;
				alert("正しい期間を入力してください!");
			}
		}else if(Number($('#monthSelectS').val())==Number($('#monthSelectE').val())-1){
			if(Number($('#daySelectS').val())<=Number($('#daySelectE').val())){
				inputCheck=1;
				alert("正しい期間を入力してください!");
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

function changes(id) {
	var answer = confirm('変更してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			type : 2,
			monthSelectS : $('#monthSelectS').val(),
			daySelectS : $('#daySelectS').val(),
			monthSelectE : $('#monthSelectE').val(),
			daySelectE : $('#daySelectE').val()
	};
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

function permit(id) {
	var answer = confirm('開始してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			type : 1
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

function continuation(id, i) {
	var answer = confirm('継続してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			type : 2,
			finishMonth : $('#monthSelectE'+i).val(),
			finishDay : $('#daySelectE'+i).val()
	};
	$.ajax({
		url : "PermitRental",
		type : "POST",
		data : request,
		success : function(data) {
			if(data["next"]==0){
				alert("次の予約が１週間以内に入っているので継続できません！");
			}else{
				location.reload();
			}
		}
	});
	}
}

function returns(id) {
	var answer = confirm('返却してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			type : 2

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

function cancel(id) {
	var answer = confirm('取り消してもよろしいですか？');
	if(answer){
	var request = {
			id : id,
			type : 1
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

function changeDaySelect(day,month) {
	if(day==31){
		if(month==2||month==4||month==6||month==9||month==11){
			return 0;
		}else{
			return 1;
		}
	}else if(day==30){
		if(month==2){
			return 0;
		}else{
			return 1;
		}
	}else if(day==29){
		if(new Date().getMonth()+1>=month){
			if(new Date().getYear()%4==0){
				return 1;
			}else{
				if(month==2){
					return 0;
				}else{
					return 1;
				}
			}
		}else{
			if((new Date().getYear()+1)%4==0){
				return 1;
			}else{
				if(month==2){
					return 0;
				}else{
					return 1;
				}
			}
		}
	}else{
		return 1;
	}
}

function changeMonthSelect(num) {
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
		$('select#daySelectE option[value=29]').attr('disabled', false);
		$('select#daySelectE option[value=30]').attr('disabled', true);
		$('select#daySelect option[value=31]').attr('disabled', true);
	} else {
		switch (month) {
		case 2:
			$('select#daySelectE option[value=29]').attr('disabled', true);
			$('select#daySelectE option[value=30]').attr('disabled', true);
			$('select#daySelectE option[value=31]').attr('disabled', true);
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			$('select#daySelectE option[value=29]').attr('disabled', false);
			$('select#daySelectE option[value=30]').attr('disabled', false);
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			$('select#daySelectE option[value=29]').attr('disabled', false);
			$('select#daySelectE option[value=31]').attr('disabled', true);
			break;
		}
	}
}