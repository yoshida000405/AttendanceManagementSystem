var authority = 1;
$(document).ready(function() {
	changeAction();
});
$('#submit').on('click', function() {
	submit();
});
$('#board').on('click', function() {
	board();
});
function changeAction(){
	var request = {
			dummy : "dummy"
	};
	$.ajax({
		url : "SearchMember",
		type : "POST",
		data : request,
		success : function(data) {
			$('#member').append($('<option>').html("全員").val(1));
			$('#member').append($('<option>').html("男性").val(2));
			$('#member').append($('<option>').html("女性").val(3));
			$('#member').append($('<option>').html("現場").val(4));
			$('#member').append($('<option>').html("自社").val(5));
			$('#member').append($('<option>').html("リーダー").val(6));
			for(let i=1; i<=data["numbersMembers"]; i++){
				$('#member').append($('<option>').html(data["member"+i]).val(Number(data["memberId"+i])+6));
			}
		}
	});
}
function submit() {
	var answer = confirm('送信してもいいですか？');
	if(answer){
		inputCheck=0;
		if($('#subjectM').val()==""||$('#contentM').val()==""){
			alert("未入力の項目があります！");
			inputCheck=1;
		}
		if($('#subjectM').val().length>30){
			alert("件名は200文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#contentM').val().length>300){
			alert("本文は300文字以内で入力してください！")
			inputCheck=1;
		}
		var request = {
				member : $('#member').val(),
				subject : $('#subjectM').val(),
				content : $('#contentM').val()
		};
		if(inputCheck==0){
			$.ajax({
				url : "SendMail",
				type : "POST",
				data : request,
				success : function(data) {
					alert("送信しました！");
				}
			});
		}
	}
}

function board() {
	var answer = confirm('掲示板に投稿してもいいですか？');
	if(answer){
		if($('#subjectM').val()==""||$('#contentM').val()==""){
			alert("未入力の項目があります！");
			inputCheck=1;
		}
		if($('#subjectM').val().length>30){
			alert("件名は200文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#contentM').val().length>300){
			alert("本文は300文字以内で入力してください！")
			inputCheck=1;
		}
		var request = {
				subject : $('#subjectM').val(),
				content : $('#contentM').val()
		};
		if(inputCheck==0){
			$.ajax({
				url : "SendBoard",
				type : "POST",
				data : request,
				success : function(data) {
					alert("投稿しました！");
				}
			});
		}
	}
}