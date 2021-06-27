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