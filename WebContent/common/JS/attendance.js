html = '<div id="day" class="clearfix mt-3"><div class="col-md-2 float-left text-center mt-3 date"><h4 id="date"></h4></div><div class="col-md-2 float-left text-center division"><select name="division" class="mt-3" id="division" class="p-1"><option value=1>出勤</option><option value=2>欠勤</option><option value=3>代休</option><option value=4>有給</option><option value=5>午前休</option><option value=6>午後休</option><option value=7>休日</option><option value=8>明休</option><option value=9>時間外</option></select></div><div class="mt-3 float-left text-center titleA1 d-none"><h5>始業時間</h5></div><div class="mt-3 float-left text-center titleA2 d-none"><h5>終業時間</h5></div><div class="mt-3 float-left text-center titleA3 d-none"><h5>休憩時間</h5></div><div class="col-md-2 float-left text-center start"><select name="start" class="mt-3" id="start"><option value=00:00>00:00</option><option value=00:15>00:15</option><option value=00:30>00:30</option><option value=00:45>00:45</option><option value=01:00>01:00</option><option value=01:15>01:15</option><option value=01:30>01:30</option><option value=01:45>01:45</option><option value=02:00>02:00</option><option value=02:15>02:15</option><option value=02:30>02:30</option><option value=02:45>02:45</option><option value=03:00>03:00</option><option value=03:15>03:15</option><option value=03:30>03:30</option><option value=03:45>03:45</option><option value=04:00>04:00</option><option value=04:15>04:15</option><option value=04:30>04:30</option><option value=04:45>04:45</option><option value=05:00>05:00</option><option value=05:15>05:15</option><option value=05:30>05:30</option><option value=05:45>05:45</option><option value=06:00>06:00</option><option value=06:15>06:15</option><option value=06:30>06:30</option><option value=06:45>06:45</option><option value=07:00>07:00</option><option value=07:15>07:15</option><option value=07:30>07:30</option><option value=07:45>07:45</option><option value=08:00>08:00</option><option value=08:15>08:15</option><option value=08:30>08:30</option><option value=08:45>08:45</option><option value=09:00>09:00</option><option value=09:15>09:15</option><option value=09:30>09:30</option><option value=09:45>09:45</option><option value=10:00>10:00</option><option value=10:15>10:15</option><option value=10:30>10:30</option><option value=10:45>10:45</option><option value=11:00>11:00</option><option value=11:15>11:15</option><option value=11:30>11:30</option><option value=11:45>11:45</option><option value=12:00>12:00</option><option value=12:15>12:15</option><option value=12:30>12:30</option><option value=12:45>12:45</option><option value=13:00>13:00</option><option value=13:15>13:15</option><option value=13:30>13:30</option><option value=13:45>13:45</option><option value=14:00>14:00</option><option value=14:15>14:15</option><option value=14:30>14:30</option><option value=14:45>14:45</option><option value=15:00>15:00</option><option value=15:15>15:15</option><option value=15:30>15:30</option><option value=15:45>15:45</option><option value=16:00>16:00</option><option value=16:15>16:15</option><option value=16:30>16:30</option><option value=16:45>16:45</option><option value=17:00>17:00</option><option value=17:15>17:15</option><option value=17:30>17:30</option><option value=17:45>17:45</option><option value=18:00>18:00</option><option value=18:15>18:15</option><option value=18:30>18:30</option><option value=18:45>18:45</option><option value=19:00>19:00</option><option value=19:15>19:15</option><option value=19:30>19:30</option><option value=19:45>19:45</option><option value=20:00>20:00</option><option value=20:15>20:15</option><option value=20:30>20:30</option><option value=20:45>20:45</option><option value=21:00>21:00</option><option value=21:15>21:15</option><option value=21:30>21:30</option><option value=21:45>21:45</option><option value=22:00>22:00</option><option value=22:15>22:15</option><option value=22:30>22:30</option><option value=22:45>22:45</option><option value=23:00>23:00</option><option value=23:15>23:15</option><option value=23:30>23:30</option><option value=23:45>23:45</option></select></div><div class="col-md-2 float-left text-center finish"><select name="finish" class="mt-3" id="finish"><option value=00:00>00:00</option><option value=00:15>00:15</option><option value=00:30>00:30</option><option value=00:45>00:45</option><option value=01:00>01:00</option><option value=01:15>01:15</option><option value=01:30>01:30</option><option value=01:45>01:45</option><option value=02:00>02:00</option><option value=02:15>02:15</option><option value=02:30>02:30</option><option value=02:45>02:45</option><option value=03:00>03:00</option><option value=03:15>03:15</option><option value=03:30>03:30</option><option value=03:45>03:45</option><option value=04:00>04:00</option><option value=04:15>04:15</option><option value=04:30>04:30</option><option value=04:45>04:45</option><option value=05:00>05:00</option><option value=05:15>05:15</option><option value=05:30>05:30</option><option value=05:45>05:45</option><option value=06:00>06:00</option><option value=06:15>06:15</option><option value=06:30>06:30</option><option value=06:45>06:45</option><option value=07:00>07:00</option><option value=07:15>07:15</option><option value=07:30>07:30</option><option value=07:45>07:45</option><option value=08:00>08:00</option><option value=08:15>08:15</option><option value=08:30>08:30</option><option value=08:45>08:45</option><option value=09:00>09:00</option><option value=09:15>09:15</option><option value=09:30>09:30</option><option value=09:45>09:45</option><option value=10:00>10:00</option><option value=10:15>10:15</option><option value=10:30>10:30</option><option value=10:45>10:45</option><option value=11:00>11:00</option><option value=11:15>11:15</option><option value=11:30>11:30</option><option value=11:45>11:45</option><option value=12:00>12:00</option><option value=12:15>12:15</option><option value=12:30>12:30</option><option value=12:45>12:45</option><option value=13:00>13:00</option><option value=13:15>13:15</option><option value=13:30>13:30</option><option value=13:45>13:45</option><option value=14:00>14:00</option><option value=14:15>14:15</option><option value=14:30>14:30</option><option value=14:45>14:45</option><option value=15:00>15:00</option><option value=15:15>15:15</option><option value=15:30>15:30</option><option value=15:45>15:45</option><option value=16:00>16:00</option><option value=16:15>16:15</option><option value=16:30>16:30</option><option value=16:45>16:45</option><option value=17:00>17:00</option><option value=17:15>17:15</option><option value=17:30>17:30</option><option value=17:45>17:45</option><option value=18:00>18:00</option><option value=18:15>18:15</option><option value=18:30>18:30</option><option value=18:45>18:45</option><option value=19:00>19:00</option><option value=19:15>19:15</option><option value=19:30>19:30</option><option value=19:45>19:45</option><option value=20:00>20:00</option><option value=20:15>20:15</option><option value=20:30>20:30</option><option value=20:45>20:45</option><option value=21:00>21:00</option><option value=21:15>21:15</option><option value=21:30>21:30</option><option value=21:45>21:45</option><option value=22:00>22:00</option><option value=22:15>22:15</option><option value=22:30>22:30</option><option value=22:45>22:45</option><option value=23:00>23:00</option><option value=23:15>23:15</option><option value=23:30>23:30</option><option value=23:45>23:45</option></select></div><div class="col-md-2 float-left text-center break"><select name="break" class="mt-3" id="break"><option value=00:00>00:00</option><option value=00:15>00:15</option><option value=00:30>00:30</option><option value=00:45>00:45</option><option value=01:00 selected>01:00</option><option value=01:15>01:15</option><option value=01:30>01:30</option><option value=01:45>01:45</option><option value=02:00>02:00</option><option value=02:15>02:15</option><option value=02:30>02:30</option><option value=02:45>02:45</option><option value=03:00>03:00</option></select></div><div class="col-md-2 float-left text-center pt-2 pb-2"><input id="remarks" type="text" class="form-control" name="remarks"></div></div>'
html1 ='<div id="inDays" class="mt-3 text-center"><h5 id="inDay" class="d-inline-block"></h5><h5 id="inStart" class="d-inline-block ml-5"></h5><h5 id="inFinish" class="d-inline-block ml-5"></h5><h5 id="inRemarks" class="d-inline-block ml-5"></h5></div>';
html2 ='<div id="inDays" class="mt-3 text-center"><select id="inDayInput" class="d-inline-block"><option value=1>1日</option><option value=2>2日</option><option value=3>3日</option><option value=4>4日</option><option value=5>5日</option><option value=6>6日</option><option value=7>7日</option><option value=8>8日</option><option value=9>9日</option><option value=10>10日</option><option value=11>11日</option><option value=12>12日</option><option value=13>13日</option><option value=14>14日</option><option value=15>15日</option><option value=16>16日</option><option value=17>17日</option><option value=18>18日</option><option value=19>19日</option><option value=20>20日</option><option value=21>21日</option><option value=22>22日</option><option value=23>23日</option><option value=24>24日</option><option value=25>25日</option><option value=26>26日</option><option value=27>27日</option><option value=28>28日</option><option value=29>29日</option><option value=30>30日</option><option value=31>31日</option></select><select id="inStartInput" class="d-inline-block ml-5"><option value=13:00>13:00</option><option value=13:30>13:30</option><option value=14:00>14:00</option><option value=14:30>14:30</option><option value=15:00>15:00</option><option value=15:30>15:30</option><option value=16:00>16:00</option><option value=16:30>16:30</option><option value=17:00>17:00</option><option value=16:30>17:30</option><option value=18:00>18:00</option><option value=18:30>18:30</option><option value=19:00>19:00</option><option value=19:30>19:30</option><option value=20:00>20:00</option><option value=20:30>20:30</option><option value=21:00>21:00</option><option value=21:30>21:30</option><option value=22:00>22:00</option></select><select id="inFinishInput" class="d-inline-block ml-5"><option value=17:00>17:00</option><option value=17:30>17:30</option><option value=18:00>18:00</option><option value=18:30>18:30</option><option value=19:00>19:00</option><option value=19:30>19:30</option><option value=20:00>20:00</option><option value=20:30>20:30</option><option value=21:00>21:00</option><option value=21:30>21:30</option><option value=22:00>22:00</option><option value=22:30>22:30</option><option value=23:00>23:00</option></select><input class="d-inline-block ml-5" id="inRemarksInput"><button type="submit" class="btn btn-primary ml-5" id="delete">削除</button></div>';
html3 ='<div id="changeDiv" class="text-right mt-5"><button type="submit" class="btn btn-primary mr-5" id="change">変更</button></div>';
var normalDay=0;
var minus=0;
var authority = 1;
var mode = 0;
var inputCheck=0;
var type = 0;
var inNumber=0;
var array="0";

$(document).ready(function() {
	changeMonth(13);
});

$('#month').change(function() {
	changeMonth($('#month').val());
});

$(document).on('change', 'input[type="file"]', function() {
	$("#upload").attr("disabled",false);
});

$('#defaultSet').on('click', function() {
	var answer = confirm('入力に間違いはないですか？');
	if(answer){
		defaultSet();
	}
});

$('#update').on('click', function() {
	var answer = confirm('更新してもいいですか？');
	if(answer){
		update(0);
	}
});

$('#submit').on('click', function() {
	var answer = confirm('提出してもいいですか？');
	if(answer){
		update(1);
	}
});

$('#entry').on('click', function() {
	var answer = confirm('登録してもいいですか？');
	if(answer){
		entry();
	}
});

$('#edit').on('click', function() {
	edit();
});

$('#data').on('click', function() {
	data();
});

// 1.出勤 9.時間外　意外の場合は選択不可にする
function changeDivision(i){
	if($("#division"+i).val()==1 || $("#division"+i).val()==9){
		$("#start"+i).attr('disabled',false);
		$("#finish"+i).attr('disabled',false);
		$("#break"+i).attr('disabled',false);
		$('#break'+i).val("01:00");
	}else{
		$('#start'+i).val("00:00");
		$('#finish'+i).val("00:00");
		$('#break'+i).val("00:00");
		$("#start"+i).attr('disabled',true);
		$("#finish"+i).attr('disabled',true);
		$("#break"+i).attr('disabled',true);
	}
}

function changePTO(i, division){
	var answer = confirm('有給を取り消してもいいですか？');
	if(answer){
		array=array+","+i;
	}else{
		$('#division'+i).val(division);
	}
}
function changeMonth(num) {
	var request = {
		month : num
	};
	$.ajax({
		url : "SearchAttendance",
		type : "POST",
		data : request,
		success : function(data) {
			// 入力チェックフラグリセット
			inputCheck=0;
			// 自社稼働モーダル内の変更ボタンを削除
			$('#changeDiv').remove();
			// 自社稼働のリストを削除
			for(let i=1; i<=inNumber; i++){
				$('#inDays' + i).remove();
			}
			inNumber=0;
			// 入力状態の情報をセット
			type = data["type"];
			// 直接入力状態を解除
			mode = 0;
			// 直接入力用のフォームを非表示に
			$('#operatingTimeInput').hide();
			$('#sumTimeInput').hide();
			$('#inTimeInput').hide();
			$('#operatingDaysInput').hide();
			$('#absenceDaysInput').hide();
			$('#substituteDaysInput').hide();
			$('#paidDaysInput').hide();
			$('#monthText').hide();
			// 平日数をリセット
			normalDay=data["normalDay"];
			// 休日数を取得
			numbersHoliday = Number(data["numbersHoliday"]);
			minus=0;
			// 通常勤務時間をセット
			$('#defaultStart').val(data["defaultStart"]);
			$('#defaultFinish').val(data["defaultFinish"]);
			// 月情報を取得
			month = data["month"];
			if (month == 22) {
				$('#month').val(2);
				$('#monthText').val(2);
				monthText = 2;
			}else{
				$('#month').val(month);
				$('#monthText').val(month);
				monthText = month;
			}
			// 編集対象者情報をセット
			$('#editUser').html(data["editUser"]);
			// 管理者メモをセット
			memo = data["memo"];
			// 権限情報を取得
			authority = Number(data["authority"]);
			// 稼働日を取得
			operatingDays = data["operatingDays"];
			// 稼働時間を取得
			operatingTime = data["operatingTime"];
			// 現場稼働を取得
			sumTime = data["sumTime"];
			// 自社稼働を取得
			inTime = data["inTime"];
			// 自社稼働日数を取得
			inNumber=data["inDays"];
			// 各日の情報を取得
			for (let i = 1; i <= 31; i++) {
				$('#day' + i).remove();
				$('#form2').append(html);
				$('#day').attr('id', 'day'+i);
				$('#date').attr('id', 'date'+i);
				$('#division').attr('class', "p-1 mt-3");
				$('#division').attr('id', 'division'+i);
				$('#start').attr('class', "p-1 mt-3");
				$('#start').attr('id', 'start'+i);
				$('#finish').attr('class', "p-1 mt-3");
				$('#finish').attr('id', 'finish'+i);
				$('#break').attr('class', "p-1 mt-3");
				$('#break').attr('id', 'break'+i);
				$('#remarks').attr('id', 'remarks'+i);
				$('#division'+i).change(function() {
					changeDivision(i);
				});
				$('#start'+i).val(data["start"+i]);
				$('#finish'+i).val(data["finish"+i]);
				$('#remarks'+i).val(data["remarks"+i]);
				$('#division'+i).val(data["division"+i]);
				$('#break'+i).val(data["break"+i]);
			}
			// 直接入力状態で通常ユーザーの場合、入力を制御
			if(type==1&&authority==1){
				for(let i=1;i<=31;i++){
					$("#division"+i).attr("disabled",true);
					$("#start"+i).attr("disabled",true);
					$("#finish"+i).attr("disabled",true);
					$("#break"+i).attr("disabled",true);
					$("#remarks"+i).attr("disabled",true);
					$("#day" + i).css("background", "gray");
				}
			}
			// 各月の日付の表示を制御
			// 閏年の場合
			if (month == "22") {
				$("#day30").css("display", "none");
				$("#day31").css("display", "none");
			} else {
				switch (month) {
				case "2":
					$("#day29").css("display", "none");
					$("#day30").css("display", "none");
					$("#day31").css("display", "none");
					break;
				case "1":
				case "3":
				case "5":
				case "7":
				case "8":
				case "10":
				case "12":
					$("#day29").css("display", "block");
					$("#day30").css("display", "block");
					$("#day31").css("display", "block");
					break;
				case "4":
				case "6":
				case "9":
				case "11":
					$("#day29").css("display", "block");
					$("#day30").css("display", "block");
					$("#day31").css("display", "none");
					break;
				}
			}
			var date = new Date();
			// 1月に12月を選択した場合は前年度のdateを取得
			if(date.getMonth()==0&&monthText==12){
				date = new Date((date.getFullYear()-1), monthText-1, 1);
			}else{
				date = new Date(date.getFullYear(), monthText-1, 1);
			}
			// 0.日曜〜6.土曜日
			var dayOfWeek = date.getDay();

			for (let i = 1; i <= 31; i++) {
				switch((i + dayOfWeek) % 7){
					case 1:
						$('#date' + i).text(monthText + "/" + i +" 日");
						break;
					case 2:
						$('#date' + i).text(monthText + "/" + i +" 月");
						break;
					case 3:
						$('#date' + i).text(monthText + "/" + i +" 火");
						break;
					case 4:
						$('#date' + i).text(monthText + "/" + i +" 水");
						break;
					case 5:
						$('#date' + i).text(monthText + "/" + i +" 木");
						break;
					case 6:
						$('#date' + i).text(monthText + "/" + i +" 金");
						break;
					case 0:
						$('#date' + i).text(monthText + "/" + i +" 土");
						break;
				}
				// 区分が出勤の場合
				if($("#division"+i).val()==1){
					$("#day" + i).css("background", "white");
				}
				// 区分が代休の場合
				if(data["division"+i]==3){
					minus++;
				}
				// 区分が有給(全日)の場合
				if($("#division"+i).val()==4){
					$("#day" + i).css("background", "springgreen");
				}
				// 区分が有給の場合
				if(Number($("#division"+i).val())==4||Number($("#division"+i).val())==5||Number($("#division"+i).val())==6){
					if(authority==1){
						$("#division"+i).prop("disabled",true);
					}
					minus++;
					$('#division'+ i).change(function() {
						changePTO(i, data["division"+i]);
					});
				}
				if($("#division"+i).val()==1 || $("#division"+i).val()==5 || $("#division"+i).val()==6 || $("#division"+i).val()==8){
					$("#start"+i).prop('disabled',false);
					$("#finish"+i).prop('disabled',false);
					$("#break"+i).prop('disabled',false);
				}else{
					$("#start"+i).prop('disabled',true);
					$("#finish"+i).prop('disabled',true);
					$("#break"+i).prop('disabled',true);
				}
				// 休日の場合
				if(data["division"+i]==7){
					$("#day" + i).css("background", "aqua");
					$("#division"+i).val(7);
					$("#start"+i).val("00:00");
					$("#finish"+i).val("00:00");
					$("#break"+i).val("00:00");
				}
				if(month==1){
					if(i==2||i==3){
						$("#day" + i).css("background", "aqua");
					}
				}else if(month==12){
					if(i==29||i==30||i==31){
						$("#day" + i).css("background", "aqua");
					}
				}

				if(authority!=2){
					$('select#division'+i+' option[value=8]').prop('disabled', true);
					if(month!=new Date().getMonth()+1){
						if(month==new Date().getMonth()){
							if(new Date().getDate()>=3){
								$("#division"+i).attr("disabled",true);
								$("#start"+i).attr("disabled",true);
								$("#finish"+i).attr("disabled",true);
								$("#break"+i).attr("disabled",true);
								$("#remarks"+i).attr("disabled",true);
							}
						}else{
							$("#division"+i).attr("disabled",true);
							$("#start"+i).attr("disabled",true);
							$("#finish"+i).attr("disabled",true);
							$("#break"+i).attr("disabled",true);
							$("#remarks"+i).attr("disabled",true);
						}
					}
				}
				$('select#division'+i+' option[value=4]').prop('disabled', true);
				$('select#division'+i+' option[value=5]').prop('disabled', true);
				$('select#division'+i+' option[value=6]').prop('disabled', true);
			}

			$('#sumTime').text((Number(operatingTime)+Number(inTime))+" 時間");
			$('#operatingTime').text(operatingTime+" 時間");
			$('#inTime').append('<a data-toggle="modal" data-target="#inModal" id="inTimeText"></a>');
			$('#inTimeText').text(inTime+" 時間");
			$('#operatingTimeInput').val(data["operatingTime"]);
			$('#operatingDaysInput').val(data["operatingDays"]);
			$('#absenceDaysInput').val(data["absenceDays"])
			$('#substituteDaysInput').val(data["substituteDays"])
			$('#paidDaysInput').val(data["paidDays"])
			$('#sumTimeInput').val((Number(operatingTime)+Number(inTime)));
			$('#inTimeInput').val(data["inTime"]);
			$('#operatingDays').text(operatingDays+" / "+(normalDay - minus));
			$('#remainingTime').text((Number(operatingTime)+Number(inTime))-((Number(operatingDays)+Number(data["absenceDays"])+Number(data["overNightDays"]))*8)+"時間");
			$('#absenceDays').text(data["absenceDays"])
			$('#substituteDays').text(data["substituteDays"])
			$('#paidDays').text(data["paidDays"])
			$('#memo').val(data["memo"])
			$('#update').show();
			$('#submit').show();
			if(authority==1){
				for(let i=1; i<=data["inDays"]; i++){
					$('#appendIn').append(html1);
					$('#inDays').attr('id', 'inDays'+i);
					$('#inDay').attr('id', 'inDay'+i);
					$('#inStart').attr('id', 'inStart'+i);
					$('#inFinish').attr('id', 'inFinish'+i);
					$('#inRemarks').attr('id', 'inRemarks'+i);
					$('#inDay'+i).text(data["inDay"+i]+"日");
					$('#inStart'+i).text(data["inStart"+i]);
					$('#inFinish'+i).text(data["inFinish"+i]);
					$('#inRemarks'+i).text(data["inRemarks"+i]);
				}
				if(data["file"]=="exist"){
					$('#submitFlag').text(data["submitFlag"]+"&データあり")
					$('#data').show();
				}else{
					$('#submitFlag').text(data["submitFlag"]+"&データなし")
					$('#data').hide();
				}
				if($("#month").val()==new Date().getMonth()+1&&new Date().getDate()<3){
					$('#submitFlag').text("未提出")
				}
				if(month!=new Date().getMonth()+1){
					if(month!=new Date().getMonth()|| new Date().getDate()>=3){
						$('#update').hide();
						$('#submit').hide();
					}
				}
				$('#file').hide();
				$('#uploadDiv').remove();
				$('#upload').hide();
				$("#memo").attr("disabled",true);
				$('#edit').remove();
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
			}else{
				for(let i=1; i<=data["inDays"]; i++){
					$('#appendIn').append(html2);
					$('#inDays').attr('id', 'inDays'+i);
					$('#inDayInput').attr('id', 'inDayInput'+i);
					$('#inStartInput').attr('id', 'inStartInput'+i);
					$('#inFinishInput').attr('id', 'inFinishInput'+i);
					$('#inRemarksInput').attr('id', 'inRemarksInput'+i);
					$('#inDayInput'+i).val(data["inDay"+i]);
					$('#inStartInput'+i).val(data["inStart"+i]);
					$('#inFinishInput'+i).val(data["inFinish"+i]);
					$('#inRemarksInput'+i).val(data["inRemarks"+i]);
					$('#delete').attr('id', 'delete'+i);
					$('#delete'+ i).val(data["inDay"+i]);
					$('#delete'+ i).on('click', function() {
						deleteM($('#delete'+ i).val());
					});
				}
				if(data["file"]=="exist"){
					$('#submitFlag').text(data["submitFlag"]+"&データあり")
					$('#pdf').attr('src',"http://52.69.198.103:8080/AttendanceManagementSystem/resources/confidential/"+data["userId"]+"/workschedule"+data["year"]+month+".pdf");
				}else{
					$('#submitFlag').text(data["submitFlag"]+"&データなし")
				}
				if($("#month").val()==new Date().getMonth()+1&&new Date().getDate()<3){
					if(data["file"]=="exist"){
						$('#submitFlag').text("未提出&データあり")
					}else{
						$('#submitFlag').text("未提出&データなし")
					}
				}
				$('#appendIn').append(html3);
				$('#change').on('click', function() {
					var answer = confirm('変更してもいいですか？');
					if(answer){
						change();
					}
				});
				if(inNumber==0){
					$("#change").hide();
				}
			}

			if($('#pdf').attr('src')==""){
				$('#pdf').hide();
			}else{
				$('#pdf').show();
			}

			$('#modalPDF').attr('class','modal-content modal-pdf')

			if($('#file').val()==null){
				$("#upload").attr("disabled",true);
			}
		}
	});
}

function update(num){
	inputCheck=0;
	if($('#memo').val().length>200){
		alert("管理者メモは200文字以内で入力してください！")
		inputCheck=1;
	}
	for(let i=1; i<=31; i++){
		if($('#remarks'+i).val().length>100){
			alert("備考は200文字以内で入力してください！")
			inputCheck=1;
		}
	}
	if(mode==0){
		var key1;
		var key2;
		var key3;
		var key4;
		var key5;
		var request = {
				submit : num,
				array : array,
				flag : type,
				type : 2,
				month : $('#month').val(),
				memo : $('#memo').val()
		};
		for(let i=1; i<=31; i++){
			$("#division"+i+" option").attr("disabled",false);
			key1 = "division"+i;
			key2 = "start"+i;
			key3 = "finish"+i;
			key4 = "break"+i;
			key5 = "remarks"+i;
			request[key1] = $('#division'+i).val();
			request[key2] = $('#start'+i).val();
			request[key3] = $('#finish'+i).val();
			request[key4] = $('#break'+i).val();
			request[key5] = $('#remarks'+i).val();
		}
	}else{
		if(isNaN($('#operatingTimeInput').val())){
			alert("稼働時間は数値で入力してください！")
			inputCheck=1;
		}
		if($('#operatingTimeInput').val().startsWith('0')&&$('#operatingTimeInput').val().length!=1){
			if($('#operatingTimeInput').val().charAt(1)!="."){
				alert("稼働時間は数値で入力してください！")
				inputCheck=1;
			}
		}
		if($('#operatingTimeInput').val().endsWith(".")){
			alert("稼働時間は数値で入力してください！")
			inputCheck=1;
		}
		if($('#operatingTimeInput').val()==""){
			alert("稼働時間の項目を入力してください！")
			inputCheck=1;
		}
		if(isNaN($('#operatingDaysInput').val())){
			alert("稼働日数は数値で入力してください！")
			inputCheck=1;
		}
		if($('#operatingDaysInput').val().startsWith('0')&&$('#operatingDaysInput').val().length!=1){
			if($('#operatingDaysInput').val().charAt(1)!="."){
				alert("稼働日数は数値で入力してください！")
				inputCheck=1;
			}
		}
		if($('#operatingDaysInput').val().endsWith(".")){
			alert("稼働日数は数値で入力してください！")
			inputCheck=1;
		}
		if($('#operatingDaysInput').val()==""){
			alert("稼働日数の項目を入力してください！")
			inputCheck=1;
		}
		if(isNaN($('#absenceDaysInput').val())){
			alert("欠勤日数は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#absenceDaysInput').val().startsWith('0')&&$('#absenceDaysInput').val().length!=1){
			alert("欠勤日数は整数値で入力してください！")
			inputCheck=1;
		}else if($('#absenceDaysInput').val().includes(".")){
			alert("欠勤日数は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#absenceDaysInput').val()==""){
			alert("欠勤日数の項目を入力してください！")
			inputCheck=1;
		}
		if(isNaN($('#substituteDaysInput').val())){
			alert("代休日数は整値で入力してください！")
			inputCheck=1;
		}
		if($('#substituteDaysInput').val().startsWith('0')&&$('#substituteDaysInput').val().length!=1){
			alert("代休日数は整数値で入力してください！")
			inputCheck=1;
		}else if($('#substituteDaysInput').val().includes(".")){
			alert("代休日数は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#substituteDaysInput').val()==""){
			alert("代休日数の項目を入力してください！")
			inputCheck=1;
		}
		if(isNaN($('#paidDaysInput').val())){
			alert("有給日数は数値で入力してください！")
			inputCheck=1;
		}
		if($('#paidDaysInput').val().startsWith('0')&&$('#paidDaysInput').val().length!=1){
			if($('#paidDaysInput').val().charAt(1)!="."){
				alert("有給日数は数値で入力してください！")
				inputCheck=1;
			}
		}
		if($('#paidDaysInput').val().endsWith(".")){
			alert("有給日数は数値で入力してください！")
			inputCheck=1;
		}
		if($('#paidDaysInput').val()==""){
			alert("有給日数の項目を入力してください！")
			inputCheck=1;
		}
		var request = {
				submit : num,
				type : 3,
				month : $('#month').val(),
				memo : $('#memo').val(),
				operatingTime : $('#operatingTimeInput').val(),
				operatingDays : $('#operatingDaysInput').val(),
				absenceDays : $('#absenceDaysInput').val(),
				substituteDays : $('#substituteDaysInput').val(),
				paidDays : $('#paidDaysInput').val(),
		}
	}

	if(inputCheck==0){
	$.ajax({
		url : "UpdateAttendance",
		type : "POST",
		data : request,
		success : function(data) {
			$.blockUI({
		      message: '更新しました',
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
			setTimeout(function() { location.reload(); }, 1000);
		}
	});
	}
}

function defaultSet(){
		var request = {
			submit : 0,
			type : 1,
			month : $('#month').val(),
			defaultStart : $('#defaultStart').val(),
			defaultFinish : $('#defaultFinish').val()
		};
		$.ajax({
			url : "UpdateAttendance",
			type : "POST",
			data : request,
			success : function(data) {
				$.blockUI({
			      message: '登録しました',
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
				setTimeout(function() { location.reload(); }, 1000);
			}
		});
}

function entry(){
	var request = {
		submit : 0,
		type : 4,
		month : $('#month').val(),
		inDay : $('#inDaySelect').val(),
		inStart : $('#inStartSelect').val(),
		inFinish : $('#inFinishSelect').val(),
		inRemarks : $('#inRemarksSelect').val()
	};
	$.ajax({
		url : "UpdateAttendance",
		type : "POST",
		data : request,
		success : function(data) {
			$.blockUI({
		      message: '登録しました',
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
			setTimeout(function() { location.reload(); }, 1000);
		}
	});
}

function change(){
	var request = {
		submit : 0,
		month : $('#month').val(),
		type : 5
	};
	for(let i=1; i<=inNumber; i++){
		request["inDay"+i] = $('#inDayInput'+i).val();
		request["inStart"+i] = $('#inStartInput'+i).val();
		request["inFinish"+i] = $('#inFinishInput'+i).val();
		request["inRemarks"+i] = $('#inRemarksInput'+i).val();
	}
	$.ajax({
		url : "UpdateAttendance",
		type : "POST",
		data : request,
		success : function(data) {
			$.blockUI({
		      message: '変更しました',
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
			setTimeout(function() { location.reload(); }, 1000);
		}
	});
}

function deleteM(day){
	var answer = confirm('削除しても良いですか？');
		if(answer){
		var request = {
			submit : 0,
			month : $('#month').val(),
			type : 6,
			inDay : day
		};
		$.ajax({
			url : "UpdateAttendance",
			type : "POST",
			data : request,
			success : function(data) {
				$.blockUI({
			      message: '削除しました',
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
				setTimeout(function() { location.reload(); }, 1000);
			}
		});
	}
}

function edit() {
	$('#operatingTime').remove();
	$('#sumTime').remove();
	$('#operatingDays').remove();
	$('#absenceDays').remove();
	$('#substituteDays').remove();
	$('#paidDays').remove();
	$('#operatingTimeInput').show();
	$('#outTimeInput').show();
	$('#operatingDaysInput').show();
	$('#absenceDaysInput').show();
	$('#substituteDaysInput').show();
	$('#paidDaysInput').show();
	mode=1;
}