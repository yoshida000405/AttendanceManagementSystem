var inputCheck=0;
$('#update').on('click', function() {
	update();
});
$('#request').on('click', function() {
	request();
});
function update(){
	var answer = confirm('入力に間違いはないですか？');
	if(answer){
		inputCheck=0;
		if($('#password').val()==""||$('#passwordConfirm').val()==""||$('#userId').val()==""){
			alert("未入力の項目があります！");
			inputCheck=1;
		}
		if($('#password').val()!=$('#passwordConfirm').val()){
			alert("パスワードが一致しません！");
			inputCheck=1;
		}
		if(!isValidPassword($('#password').val())){
			alert("大文字、小文字、数字の３種類を全て含んでください！");
			inputCheck=1;
		}
		if($('#password').val().length<8){
			alert("パスワードは8文字以上で設定してください！");
			inputCheck=1;
		}
		if(inputCheck==0){
			var request = {};
			request.userId=$('#userId').val();
			request.password=$('#password').val();
			$.ajax({
				url : "PasswordChg",
				type : "POST",
				data : request,
				success : function(data) {
					var answer = confirm(data["msg"]);
					if(answer){
						window.location.href = "http://52.69.198.103:8080/AttendanceRigare/login.jsp";
					}else{
						window.location.href = "http://52.69.198.103:8080/AttendanceRigare/login.jsp";
					}
				}
			});
		}
	}
}

function request(){
	var answer = confirm('入力に間違いはないですか？');
	if(answer){
		inputCheck=0;
		if($('#userId').val()==""){
			alert("未入力の項目があります！");
			inputCheck=1;
		}
		if(inputCheck==0){
			var request = {};
			request.userId=$('#userId').val();
			$.ajax({
				url : "RequestPasswordChg",
				type : "POST",
				data : request,
				success : function(data) {
					alert(data["msg"]);
				}
			});
		}
	}
}

function isValidPassword(str) {
	  return /^(?:[^a-zA-Z0-9]*(?:[a-z](?:[^A-Z0-9]*(?:[A-Z](?:[^0-9]*[0-9])|[0-9](?:[^A-Z]*[A-Z])))|[A-Z](?:[^a-z0-9]*(?:[a-z](?:[^0-9]*[0-9])|[0-9](?:[^a-z]*[a-z])))|[0-9](?:[^a-zA-Z]*(?:[a-z](?:[^A-Z]*[A-Z])|[A-Z](?:[^a-z]*[a-z])))))/.test(str)
	}