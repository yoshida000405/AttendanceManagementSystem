var inputCheck=0;
$('#update').on('click', function() {
	update();
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
function update(){
	var answer = confirm('入力に間違いはないですか？');
	if(answer){
		inputCheck=0;
		if($('#password').val()!=$('#passwordConfirm').val()){
			alert("パスワードが一致しません！")
			inputCheck=1;
		}
		if($('#lastName').val()==""||$('#firstName').val()==""||$('#lastNameKana').val()==""||$('#firstNameKana').val()==""||$('#password').val()==""||$('#lastNameEnglish').val()==""||$('#firstNameEnglish').val()==""||$('#hireDate').val()==""||$('#mailAddress').val()==""||$('#station').val()==""||$('#pto').val()==""){
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
		for(let i=0; i<$('#lastNameEnglish').val().length; i++){
			if($('#lastNameEnglish').val().charCodeAt(i) < 65||$('#lastNameEnglish').val().charCodeAt(i)>122) {
		      isJapanese = true;
		      break;
		    }else if($('#lastNameEnglish').val().charCodeAt(i) < 97 && $('#lastNameEnglish').val().charCodeAt(i)>90){
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
		if($('#password').val().length>15){
			alert("パスワード）は15文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#passwordConfirm').val().length>15){
			alert("パスワード（確認用）は15文字以内で入力してください！")
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
		if(isNaN($('#pto').val())){
			alert("残有給数は数値で入力してください！")
			inputCheck=1;
		}
		if($('#pto').val().startsWith('0')&&$('#pto').val().length!=1){
			if($('#pto').val().charAt(1)!="."){
				alert("残有給数は数値で入力してください！")
				inputCheck=1;
			}
		}
		if($('#pto').val().endsWith(".")){
			alert("残有給数は数値で入力してください！")
			inputCheck=1;
		}

		if(inputCheck==0){
			var request = {};
			var group =",1,";
			request.lastName=$('#lastName').val();
			request.firstName=$('#firstName').val();
			request.lastNameKana=$('#lastNameKana').val();
			request.firstNameKana=$('#firstNameKana').val();
			request.lastNameEnglish=$('#lastNameEnglish').val();
			request.firstNameEnglish=$('#firstNameEnglish').val();
			request.password=$('#password').val();
			request.gender=$('#gender').val();
			request.hireDate=$('#hireDate').val();
			request.mailAddress=$('#mailAddress').val();
			request.station=$('#station').val();
			if($('#pto').val()==""){
				request.pto=0;
			}else{
				request.pto=$('#pto').val();
			}
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
				url : "Register",
				type : "POST",
				data : request,
				success : function(data) {
					alert("登録しました！");
				}
			});
		}
	}
}