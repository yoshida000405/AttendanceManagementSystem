var inputCheck=0;
$('#update').on('click', function() {
	update();
});
$('#delete').on('click', function() {
	deleted();
});
$(document).ready(function() {
	changeAction(0);
});
$('#member').change(function() {
	changeAction(1);
});
$('#group1').change(function() {
	if($("#group1").prop("checked") == true){
		if($("#group2").prop("checked") == true){
			$("#group2").attr("checked",false);
		}
	}
});
$('#group2').change(function() {
	if($("#group2").prop("checked") == true){
		if($("#group1").prop("checked") == true){
			$("#group1").attr("checked",false);
		}
	}
});

function changeAction(num) {
	var request = {
			dummy : "dummy"
	};
	if($("#member").val()==null){
		request.id = 1;
		request.type = num;
	}else{
		request.type = num;
		request.id = Number($("#member").val());
	}
	$.ajax({
		url : "SearchUserInfo",
		type : "POST",
		data : request,
		success : function(data) {
			if(num==0){
				for(let i=1; i<=data["numbersMembers"]; i++){
					$('#member').append($('<option>').html(data["member"+i]).val(data["memberId"+i]));
				}
			}
			$("#member").val(data["id"]);
			$("#lastName").val(data["lastName"]);
			$("#firstName").val(data["firstName"]);
			$("#lastNameKana").val(data["lastNameKana"]);
			$("#firstNameKana").val(data["firstNameKana"]);
			$("#lastNameEnglish").val(data["lastNameEnglish"]);
			$("#firstNameEnglish").val(data["firstNameEnglish"]);
			$("#gender").val(data["gender"]);
			$("#hireDate").val(data["hireDate"].replaceAll('-',''));
			$("#mailAddress").val(data["mailAddress"]);
			$("#authority").val(data["authority"]);
			$("#station").val(data["station"]);
			$('#member').val(data["editUser"]);
			if(data["group"].includes("4")){
				$("#group1").prop("checked",true);
			}else{
				$("#group1").prop("checked",false);
			}
			if(data["group"].includes("5")){
				$("#group2").prop("checked",true);
			}else{
				$("#group2").prop("checked",false);
			}
			if(data["group"].includes("6")){
				$("#group3").prop("checked",true);
			}else{
				$("#group3").prop("checked",false);
			}
		}
	});
}

function update(){
	var answer = confirm('入力に間違いはないですか？');
	if(answer){
		inputCheck=0;
		if($('#lastName').val()==""||$('#firstName').val()==""||$('#lastNameKana').val()==""||$('#firstNameKana').val()==""||$('#lastNameEnglish').val()==""||$('#firstNameEnglish').val()==""||$('#hireDate').val()==""||$('#mailAddress').val()==""||$('#station').val()==""){
			alert("全てのフォームを入力してください！")
			inputCheck=1;
		}
		if($('#lastName').val().length>10){
			alert("名は10文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#firstName').val().length>10){
			alert("姓は10文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#lastNameKana').val().length>15){
			alert("名（ふりがな）は15文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#firstNameKana').val().length>15){
			alert("姓（ふりがな）は15文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#lastNameEnglish').val().length>45){
			alert("名（アルファベット）は45文字以内で入力してください！")
			inputCheck=1;
		}
		var isJapanese = false;
		for(var i=0; i < $('#lastNameEnglish').val().length; i++){
			if($('#lastNameEnglish').val().charCodeAt(i) < 65||$('#lastNameEnglish').val().charCodeAt(i)>=123) {
		      isJapanese = true;
		      break;
		    }else if($('#lastNameEnglish').val().charCodeAt(i) < 97&&$('#lastNameEnglish').val().charCodeAt(i)>=91){
		    	isJapanese = true;
			      break;
		    }
		}
		if(isJapanese){
			alert("名（アルファベット）は英字で入力してください！！")
			inputCheck=1;
		}
		if($('#firstNameEnglish').val().length>45){
			alert("姓（アルファベット）は45文字以内で入力してください！")
			inputCheck=1;
		}
		isJapanese = false;
		for(var i=0; i < $('#firstNameEnglish').val().length; i++){
			if($('#firstNameEnglish').val().charCodeAt(i) < 65||$('#firstNameEnglish').val().charCodeAt(i)>=123) {
			      isJapanese = true;
			      break;
		    }else if($('#firstNameEnglish').val().charCodeAt(i) < 97&&$('#firstNameEnglish').val().charCodeAt(i)>=91){
		    	isJapanese = true;
			      break;
		    }
		}
		if(isJapanese){
			alert("姓（アルファベット）は英字で入力してください！！")
			inputCheck=1;
		}
		if($('#hireDate').val().length!=8){
			alert("入社日は「20200101」の形式で入力してください！")
			inputCheck=1;
		}
		if(isNaN($('#hireDate').val())){
			alert("入社日は数値で入力してください！")
			inputCheck=1;
		}
		if($('#hireDate').val().includes(".")){
			alert("入社日は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#mailAddress').val().length>50){
			alert("メールアドレスは50文字以内で入力してください！")
			inputCheck=1;
		}
		if($("#group1").prop("checked") == false&&$("#group2").prop("checked") == false){
			alert("現場か自社かどちらかを選択してください！")
			inputCheck=1;
		}
		if($('#station').val().length>45){
			alert("最寄駅名は45文字以内で入力してください！")
			inputCheck=1;
		}
		if(inputCheck==0){
			var request = {};
			var group =",1,";
			request.id=$('#member').val();
			request.lastName=$('#lastName').val();
			request.firstName=$('#firstName').val();
			request.lastNameKana=$('#lastNameKana').val();
			request.firstNameKana=$('#firstNameKana').val();
			request.lastNameEnglish=$('#lastNameEnglish').val();
			request.firstNameEnglish=$('#firstNameEnglish').val();
			request.gender=$('#gender').val();
			request.hireDate=$('#hireDate').val();
			request.mailAddress=$('#mailAddress').val();
			request.authority=$('#authority').val();
			request.station=$('#station').val();
			if($("#gender").val()==1){
				group = group+"2,"
			}else{
				group = group+"3,"
			}
			if($("#group1").prop("checked") == true){
				group = group+"4,"
			}
			if($("#group2").prop("checked") == true){
				group = group+"5,"
			}
			if($("#group3").prop("checked") == true){
				group = group+"6,"
			}
			request.group=group;
			$.ajax({
				url : "UpdateUserInfo",
				type : "POST",
				data : request,
				success : function(data) {
					alert("更新しました！");
				}
			});
		}
	}
}

function deleted(){
	var answer = confirm('削除してもよろしいですか？');
	if(answer){
		password = window.prompt("パスワードを入力してください", "");
		var request = {};
		request.id=$('#member').val();
		request.password=password;
		request.type="delete"
		$.ajax({
				url : "UpdateUserInfo",
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