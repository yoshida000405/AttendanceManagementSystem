var authority = 1;
var nowId = 0;
var updDate;
var subId2 = 0;
var subId3 = 0;
var inputCheck=0;
$(document).ready(function() {
	changeAction(0,0);
});
$('#forward').on('click', function() {
	forward();
});
$('#next').on('click', function() {
	next();
});
$('#all').on('click', function() {
	all();
});
$('#edit').on('click', function() {
	edit();
});
$('#update').on('click', function() {
	var answer = confirm('更新してもいいですか？');
	if(answer){
		update();
	}
});
$('#subject2B').on('click', function() {
	changeAction(subId2,4);
});
$('#subject3B').on('click', function() {
	changeAction(subId3,4);
});
function changeAction(id,num){
	var request = {
		nowId : id,
		type : num,
		updDate : updDate
	};
	$.ajax({
		url : "SearchBoard",
		type : "POST",
		data : request,
		success : function(data) {
			$('#subject1B').text(data["subject1"]);
			$('#subject2B').text(data["subject2"]);
			$('#subject3B').text(data["subject3"]);
			$('#content1').html(data["content1"].replaceAll('\n', '<br><br>'));
			$('#content').text(data["content1"]);
			$('#subjectB').val(data["subject1"]);
			nowId = data["id1"];
			subId2 = data["id2"];
			subId3 = data["id3"];
			updDate = data["updDate"];
			authority = data["authority"];
			if(authority==1){
				$('#edit').hide();
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
			$('#content').hide();
			$('#update').hide();
			$('#subjectB').hide();
		}
	});
}
function next() {
	var request = {
		nowId : nowId,
		type : 1,
		updDate : updDate
	};
	$.ajax({
		url : "SearchBoard",
		type : "POST",
		data : request,
		success : function(data) {
			$('#subject1B').text(data["subject1"]);
			$('#subject2B').text(data["subject2"]);
			$('#subject3B').text(data["subject3"]);
			$('#content1').html(data["content1"].replaceAll('\n', '<br><br>'));
			$('#subjectB').val(data["subject1"]);
			$('#content').text(data["content1"]);
			nowId = data["id1"];
			subId2 = data["id2"];
			subId3 = data["id3"];
			updDate = data["updDate"];
			authority = data["authority"];
			if(authority==1){
				$('#editDiv').hide();
			}
			$('#content').hide();
			$('#update').hide();
			$('#subjectB').hide();
		}
	});
}

function forward() {
	var request = {
		nowId : nowId,
		type : 2,
		updDate : updDate
	};
	$.ajax({
		url : "SearchBoard",
		type : "POST",
		data : request,
		success : function(data) {
			$('#subject1B').text(data["subject1"]);
			$('#subject2B').text(data["subject2"]);
			$('#subject3B').text(data["subject3"]);
			$('#content1').html(data["content1"].replaceAll('\n', '<br><br>'));
			$('#subjectB').val(data["subject1"]);
			$('#content').text(data["content1"]);
			nowId = data["id1"];
			subId2 = data["id2"];
			subId3 = data["id3"];
			updDate = data["updDate"];
			authority = data["authority"];
			if(authority==1){
				$('#edit').hide();
			}
			$('#content').hide();
			$('#update').hide();
			$('#subjectB').hide();
		}
	});
}

function update() {
	inputCheck=0;
	if($('#subjectB').val()==""||$('#content').val()==""){
		alert("未入力の項目があります！");
		inputCheck=1;
	}
	if($('#subjectB').val().length>30){
		alert("件名は30文字以内で入力してください！")
		inputCheck=1;
	}
	if($('#content').val().length>300){
		alert("本文は300文字以内で入力してください！")
		inputCheck=1;
	}
	var request = {
		nowId : nowId,
		subject : $('#subjectB').val(),
		content : $('#content').val()
	};
	if(inputCheck==0){
		$.ajax({
			url : "UpdateBoard",
			type : "POST",
			data : request,
			success : function(data) {
				alert("更新しました");
			}
		});
	}
}

function all() {
	window.location.href = "boardAll.jsp";
}

function edit() {
	$('#subject1B').remove();
	$('#content1').remove();
	$('#subjectB').show();
	$('#content').show();
	$('#update').show();
}