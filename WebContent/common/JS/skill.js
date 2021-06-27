var authority = 1;
var reset = 0;
var inputCheck=0;
$(document).ready(function() {
	todayMonth = (new Date().getMonth() + 1);
	changeMonth(todayMonth);
	$('#month').val(todayMonth)
});
$('#month').change(function() {
	changeMonth($('#month').val());
});
$('#update').on('click', function() {
	var answer = confirm('更新してもいいですか？');
	if(answer){
		update();
	}
});

$('#reset').on('click', function() {
	resetAction();
});

function changeMonth(num) {
	var request = {
			month : num
	};
	$.ajax({
		url : "SearchSkill",
		type : "POST",
		data : request,
		success : function(data) {
			reset = 0;
			month =$('#month').val();
			$("#projectName").val(data["projectName"]);
			$("#bussinessOverview").val(data["bussinessOverview"]);
			if(data["role"]==0){
				$("#role").val("1");
			}else{
				$("#role").val(data["role"]);
			}
			$("#scale").val(data["scale"]);
			$("#serverOS").val(data["serverOS"]);
			$("#DB").val(data["db"]);
			$("#tool").val(data["tool"]);
			$("#useLanguage").val(data["useLanguage"]);
			$("#other").val(data["other"]);
			$('#bussinessOverviewEdit').val("");
			$('#serverOSEdit').val("");
			$('#DBEdit').val("");
			$('#toolEdit').val("");
			$('#useLanguageEdit').val("");
			$('#otherEdit').val("");
			authority = Number(data["authority"]);
			$('#editUser').html(data["editUser"]);

			if(month!=new Date().getMonth()+1&&authority==1){
				if(month==new Date().getMonth()&&new Date().getDate()<3){
					$("#bussinessOverviewEdit").attr("disabled",false);
					$("#role").attr("disabled",false);
					$("#scale").attr("disabled",false);
					$("#serverOSEdit").attr("disabled",false);
					$("#DBEdit").attr("disabled",false);
					$("#toolEdit").attr("disabled",false);
					$("#useLanguageEdit").attr("disabled",false);
					$("#otherEdit").attr("disabled",false);
					$('#reset').show();
					$('#update').show();
				}else{
					$("#bussinessOverviewEdit").attr("disabled",true);
					$("#role").attr("disabled",true);
					$("#scale").attr("disabled",true);
					$("#serverOSEdit").attr("disabled",true);
					$("#DBEdit").attr("disabled",true);
					$("#toolEdit").attr("disabled",true);
					$("#useLanguageEdit").attr("disabled",true);
					$("#otherEdit").attr("disabled",true);
					$('#reset').hide();
					$('#update').hide();
				}
			}else{
				$("#bussinessOverviewEdit").attr("disabled",false);
				$("#role").attr("disabled",false);
				$("#scale").attr("disabled",false);
				$("#serverOSEdit").attr("disabled",false);
				$("#DBEdit").attr("disabled",false);
				$("#toolEdit").attr("disabled",false);
				$("#useLanguageEdit").attr("disabled",false);
				$("#otherEdit").attr("disabled",false);
				$('#reset').show();
				$('#update').show();
			}

			if(data["authority"]==1){
				$('#output').remove();
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
			$('#submitFlag').text(data["submitFlag"])
			if($("#month").val()==new Date().getMonth()+1&&new Date().getDate()<3){
				$('#submitFlag').text("未提出")
			}
		}
	});
}
function update(){
	inputCheck=0;
	if(reset==1){
		if($('#projectName').val().length>45){
			alert("プロジェクト名が文字制限に達しています！")
			inputCheck=1;
		}
		if($('#projectName').val()==""){
			alert("プロジェクト名を入力して下さい！")
			inputCheck=1;
		}
	}

	if(reset==1){
		if($('#bussinessOverview').val().length>298){
			alert("業務概要が文字制限に達しています！")
			inputCheck=1;
		}
		if($('#bussinessOverview').val()==""){
			alert("業務概要を入力して下さい！")
			inputCheck=1;
		}
	}else{
		if($('#bussinessOverview').val().length + $('#bussinessOverviewEdit').val().length>298){
			alert("業務概要が文字制限に達しています！")
			inputCheck=1;
		}
	}
	if(isNaN($('#scale').val())){
		alert("規模数は数値で入力してください！")
		inputCheck=1;
	}
	if($('#scale').val().startsWith('0')&&$('#scale').val().length!=1){
		alert("規模数は整数値で入力してください！")
		inputCheck=1;
	}else if($('#scale').val().includes(".")){
		alert("規模数は整数値で入力してください！")
		inputCheck=1;
	}
	if($('#scale').val()==""){
		alert("規模数の項目を入力してください！")
		inputCheck=1;
	}
	if(reset==1){
		if($('#serverOS').val().length>28){
			alert("サーバー/OSが文字制限に達しています！")
			inputCheck=1;
		}
	}else{
		if($('#serverOS').val().length+$('#serverOSEdit').val().length>28){
			alert("サーバー/OSが文字制限に達しています！")
			inputCheck=1;
		}
	}

	if(reset==1){
		if($('#DB').val().length>28){
			alert("DBが文字制限に達しています！")
			inputCheck=1;
		}
	}else{
		if($('#DB').val().length+$('#DBEdit').val().length>28){
			alert("DBが文字制限に達しています！")
			inputCheck=1;
		}
	}

	if(reset==1){
		if($('#tool').val().length>28){
			alert("ツール類が文字制限に達しています！")
			inputCheck=1;
		}
	}else{
		if($('#tool').val().length+$('#toolEdit').val().length>58){
			alert("ツール類が文字制限に達しています！")
			inputCheck=1;
		}
	}

	if(reset==1){
		if($('#useLanguage').val().length>28){
			alert("使用言語が文字制限に達しています！")
			inputCheck=1;
		}
	}else{
		if($('#useLanguage').val().length+$('#useLanguageEdit').val().length>28){
			alert("使用言語が文字制限に達しています！")
			inputCheck=1;
		}
	}

	if(reset==1){
		if($('#other').val().length>98){
			alert("その他が文字制限に達しています！")
			inputCheck=1;
		}
	}else{
		if($('#other').val().length+$('#otherEdit').val().length>98){
			alert("その他が文字制限に達しています！")
			inputCheck=1;
		}
	}

	var request = {};
	request.month=$('#month').val();
	request.projectName=$('#projectName').val();
	request.bussinessOverview=$('#bussinessOverview').val();
	request.role=Number($('#role').val());
	request.scale=Number($('#scale').val());
	request.serverOS=$('#serverOS').val();
	request.db=$('#DB').val();
	request.tool=$('#tool').val();
	request.useLanguage=$('#useLanguage').val();
	request.other=$('#other').val();
	if(reset==0){
		request.bussinessOverviewEdit=$('#bussinessOverviewEdit').val();
		request.serverOSEdit=$('#serverOSEdit').val();
		request.dbEdit=$('#DBEdit').val();
		request.toolEdit=$('#toolEdit').val();
		request.useLanguageEdit=$('#useLanguageEdit').val();
		request.otherEdit=$('#otherEdit').val();
	}
	request.reset=reset;
	if(inputCheck==0){
		$.ajax({
			url : "UpdateSkill",
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
}
function resetAction(){
	var answer = confirm('リセットしてもいいですか？');
	if(answer){
		reset = 1;
		$("#projectName").val("");
		$('#projectName').attr('disabled', false);
		$("#bussinessOverview").val("");
		$('#bussinessOverview').attr('disabled', false);
		$("#role").val();
		$("#scale").val("");
		$("#serverOS").val("");
		$('#serverOS').attr('disabled', false);
		$("#DB").val("");
		$('#DB').attr('disabled', false);
		$("#tool").val("");
		$('#tool').attr('disabled', false);
		$("#useLanguage").val("");
		$('#useLanguage').attr('disabled', false);
		$("#other").val("");
		$('#other').attr('disabled', false);
		$('#bussinessOverview' + 'Edit').remove();
		$('#serverOS' + 'Edit').remove();
		$('#DB' + 'Edit').remove();
		$('#tool' + 'Edit').remove();
		$('#useLanguage' + 'Edit').remove();
		$('#other' + 'Edit').remove();
		if(month!=new Date().getMonth()+1&&authority==1){
			$("#projectName").attr("disabled",true);
			$("#bussinessOverview").attr("disabled",true);
			$("#role").attr("disabled",true);
			$("#scale").attr("disabled",true);
			$("#serverOS").attr("disabled",true);
			$("#DB").attr("disabled",true);
			$("#tool").attr("disabled",true);
			$("#useLanguage").attr("disabled",true);
			$("#other").attr("disabled",true);
		}
		$('#month').attr('disabled', true);
	}
}