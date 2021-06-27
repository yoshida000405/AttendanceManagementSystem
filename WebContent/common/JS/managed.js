html ='<div class="mt-4 line" id="line"><div class="clearfix mb-3"><div class="col-md-2 float-left text-center mt-2 div-column1"><h5 class="column1Label" id="column1Label"></h5><h5 class="column1" id="column1_"></h5></div><div class="col-md-1 float-left text-center mt-2 div-column2"><h5 class="column2Label" id="column2Label"></h5><h5 class="column2" id="column2_"></h5></div><div class="col-md-1 float-left text-center mt-2 div-column3"><h5 class="column3Label" id="column3Label"></h5><h5 class="column3" id="column3_"></h5></div><div class="col-md-1 float-left text-center mt-2 div-column4"><h5 class="column4Label" id="column4Label"></h5><h5 class="column4" id="column4_"></h5></div><div class="col-md-1 float-left text-center mt-2 div-column5"><h5 class="column5Label" id="column5Label"></h5><h5 class="column5" id="column5_"></h5></div><div class="col-md-1 float-left text-center mt-2 div-column6"><h5 class="column6Label" id="column6Label"></h5><h5 class="column6" id="column6_"></h5></div><div class="col-md-1 float-left text-center mt-2 div-column7"><h5 class="column7Label" id="column7Label"></h5><h5 class="column7" id="column7_"></h5></div><div id="div-column8" class="col-md-1 float-left text-center mt-2 div-column8"><h5 class="column8Label" id="column8Label"></h5><h5 class="column8" id="column8_"></h5></div><div id="div-column9" class="col-md-1 float-left text-center mt-2 div-column9"><h5 class="column9Label" id="column9Label"></h5><h5 class="column9" id="column9_"></h5></div><div id="div-column10" class="col-md-1 float-left text-center div-column10"><h5 class="column10Label" id="column10Label"></h5><button type="button" class="btn btn-primary column10" id="column10_" value="値">出力</button></div><div id="div-column11" class="col-md-1 float-left text-center div-column11"><h5 class="column11Label" id="column11Label"></h5><button type="button" class="btn btn-primary column11" id="column11_" value="値">編集</button></div></div></div>';
var numbersAplication = 0;
var authority = 1;
var month = 0;
var sum2=0;
var sum3=0;
var sum4=0;
var sum5=0;
var sum6=0;
var sum7=0;
var sum8=0;
var sum9=0;
$(document).ready(function() {
	changeAction(14,0);
	setTimeout(function() {
		if(authority==2){
			if(new Date().getDate()<4){
				$("#month").val(new Date().getMonth());
			}else{
				$("#month").val(new Date().getMonth()+1);
			}
		}else{
			if(new Date().getDate<3){
				$("#month").val(new Date().getMonth());
			}else{
				$("#month").val(new Date().getMonth()+1);
			}
		}
	}, 1000);
});
$('#month').change(function() {
	changeAction($('#month').val(),1);
});
$('#type').change(function() {
	changeAction($('#month').val(),1);
});
$('#member').change(function() {
	changeAction($('#month').val(),1);
});
$('#checkAll').on('click', function() {
	var answer = confirm('一括処理してもいいですか？');
	if(answer){
		checkAll();
	}
});
$('#outputAll').on('click', function() {
	var answer = confirm('一括出力してもいいですか？');
	if(answer){
		outputAll();
	}
});
$('#search').keyup(function() {
    var value = $('#search').val();
    var check = 0;
    for (let i = 1; i <= numbersAplication; i++) {
    	check =0;
    	for(let j = 1; j <= 10; j++){
    		if($('#column'+j+'_' + i).text().includes(value)){
        		check =1;
        	}
    	}
    	if(check!=1){
    		$('#line' + i).hide();
    	}else{
    		$('#line' + i).show();
    	}
	}
  });
function appendForm(){
	numbersAplication++;
	$('#form2').append(html);
	$('#line').attr('id', 'line'+numbersAplication);
	$('#column1_').attr('id', 'column1_'+numbersAplication);
	$('#column2_').attr('id', 'column2_'+numbersAplication);
	$('#column3_').attr('id', 'column3_'+numbersAplication);
	$('#column4_').attr('id', 'column4_'+numbersAplication);
	$('#column5_').attr('id', 'column5_'+numbersAplication);
	$('#column6_').attr('id', 'column6_'+numbersAplication);
	$('#column7_').attr('id', 'column7_'+numbersAplication);
	$('#column8_').attr('id', 'column8_'+numbersAplication);
	$('#column9_').attr('id', 'column9_'+numbersAplication);
	$('#column10_').attr('id', 'column10_'+numbersAplication);
	$('#column11_').attr('id', 'column11_'+numbersAplication);
	$('#column1Label').attr('id', 'column1Label'+numbersAplication);
	$('#column2Label').attr('id', 'column2Label'+numbersAplication);
	$('#column3Label').attr('id', 'column3Label'+numbersAplication);
	$('#column4Label').attr('id', 'column4Label'+numbersAplication);
	$('#column5Label').attr('id', 'column5Label'+numbersAplication);
	$('#column6Label').attr('id', 'column6Label'+numbersAplication);
	$('#column7Label').attr('id', 'column7Label'+numbersAplication);
	$('#column8Label').attr('id', 'column8Label'+numbersAplication);
	$('#column9Label').attr('id', 'column9Label'+numbersAplication);
	$('#column10Label').attr('id', 'column10Label'+numbersAplication);
	$('#column11Label').attr('id', 'column11Label'+numbersAplication);
}
function changeAction(num,check) {
	var request = {
			month : num,
			type : $('#type').val()
	};
	if($('#member').val()!=null){
		request.member = $('#member').val();
	}else{
		request.member = 1;
	}
	$.ajax({
		url : "SearchManaged",
		type : "POST",
		data : request,
		success : function(data) {
			sum2=0;
			sum3=0;
			sum4=0;
			sum5=0;
			sum6=0;
			sum7=0;
			sum8=0;
			sum9=0;
			if(check==0){
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
			for (let i = 0; i <= numbersAplication; i++) {
				$('#line' + i).remove();
			}
			numbersAplication = 0;
			month = data["month"];
			numbers = Number(data["numbersAplication"]);
			authority = Number(data["authority"]);
			element = document.getElementById( "month" );
			elements = element.options;
			if(Number($('#member').val())<=6||Number($('#member').val())==4){
				$('select#month option[value=13]').remove();
			}else{
				if(elements.length==12){
					$('#month').append($('<option>').html("年間").val(13));
				}
			}
			if(check==1){
				if(Number($('#month').val())==13){
					for(let i=1; i<=6; i++){
						$('select#member option[value='+i+']').attr('disabled', true);
					}
					$('select#type option[value=4]').attr('disabled', true);
				}else{
					for(let i=1; i<=6; i++){
						$('select#member option[value='+i+']').attr('disabled', false);
					}
					$('select#type option[value=4]').attr('disabled', false);
				}
			}else{
				$('#month').val(data["month"]);
			}
			if($('#type').val()==4){
				$('#month').attr('disabled', true);
			}else{
				$('#month').attr('disabled', false);
			}
			if($('#type').val()==1){
				$('#column1').text("対象者");
				$('#column2').text("稼働時間");
				$('#column3').text("出勤");
				$('#column4').text("欠勤");
				$('#column5').text("残業");
				$('#column6').text("有給");
				$('#column7').text("区分");
				$('#column8').text("データ");
				$('#column9').text("合計額");
				$('#column10').text("出力");
				$('#column11').text("リンク");
				$('#checkAll').hide();
			}else if($('#type').val()==2){
				$('#column1').text("対象者");
				$('#column2').text("合計");
				$('#column3').text("旅費交通費");
				$('#column4').text("消耗品費");
				$('#column5').text("会議費");
				$('#column6').text("通信費");
				$('#column7').text("支払手数料");
				$('#column8').text("交際費");
				$('#column9').text("その他");
				$('#column10').text("出力");
				$('#column11').text("リンク");
				$('#checkAll').show();
				numbersAplication = -1;
				appendForm();
				$('#div-column11').attr('id','div-column11_'+ 0);
				$('#div-column11_'+ 0).attr('class','col-md-1 float-left text-center div-column11');
				$('#column11sub').attr('class','col-md-1 float-left text-center');
				$('#div-column8').attr('id','div-column8_'+ 0);
				$('#div-column9').attr('id','div-column9_'+ 0);
				$('#div-column10').attr('id','div-column10_'+ 0);
				$('#div-column8_'+ 0).attr('class','col-md-1 float-left text-center mt-2 div-column8');
				$('#div-column9_'+ 0).attr('class','col-md-1 float-left text-center mt-2 div-column9');
				$('#div-column10_'+ 0).attr('class','col-md-1 float-left text-center div-column10');
				$('#column8sub').attr('class','col-md-1 float-left text-center');
				$('#column9sub').attr('class','col-md-1 float-left text-center');
				$('#column10sub').attr('class','col-md-1 float-left text-center');
				$('#column10_'+0).remove();
				$('#column11_'+0).remove();
			}else{
				$('#column1').text("対象者");
				$('#column2').text("");
				$('#column3').text("");
				$('#column4').text("");
				$('#column5').text("");
				$('#column6').text("");
				$('#column7').text("");
				$('#column8').text("");
				$('#column9').text("区分");
				$('#column10').text("出力");
				$('#column11').text("リンク");
				$('#checkAll').hide();
			}
			for (let i = 1; i <= numbers; i++) {
				appendForm();
				if(Number($('#month').val())==13){
					$('#column10_' + i).val(i);
					$('#column11_' + i).val(i);
				}else{
					$('#column10_' + i).val(data["column10_" + i]);
					$('#column11_' + i).val(data["column10_" + i]);
				}
				$('#column1_' + i).text(data["column1_" + i]);
				if($('#type').val()==1){
					$('#div-column8').attr('id','div-column8_'+ i);
					$('#div-column9').attr('id','div-column9_'+ i);
					$('#div-column10').attr('id','div-column10_'+ i);
					$('#div-column8_'+ i).attr('class','col-md-1 float-left text-center mt-2 div-column8');
					$('#div-column9_'+ i).attr('class','col-md-1 float-left text-center mt-2 div-column9');
					$('#div-column10_'+ i).attr('class','col-md-1 float-left text-center div-column10');
					$('#column8sub').attr('class','col-md-1 float-left text-center');
					$('#column9sub').attr('class','col-md-1 float-left text-center');
					$('#column10sub').attr('class','col-md-1 float-left text-center');
					$('#div-column11').attr('id','div-column11_'+ i);
					$('#div-column11_'+ i).attr('class','col-md-1 float-left text-center div-column11');
					$('#column11sub').attr('class','col-md-1 float-left text-center');
					$('#column2_' + i).text(data["column2_" + i]);
					$('#column3_' + i).text(data["column3_" + i]);
					$('#column4_' + i).text(data["column4_" + i]);
					$('#column5_' + i).text(data["column5_" + i]);
					$('#column6_' + i).text(data["column6_" + i]);
					if(data["column7_" + i]==0){
						$('#column7_' + i).text("自社");
					}else{
						$('#column7_' + i).text("現場");
					}
					if(data["column8_" + i]=="exist"){
						$('#column8_' + i).text("あり");
					}else{
						$('#column8_' + i).text("なし");
					}
					$('#column9_' + i).text(data["column9_" + i]);
					$('#column10_'+i).on('click', function() {
						outputAttendance($('#column10_' + i).val());
					});
					$('#column11_'+i).on('click', function() {
						editAttendance($('#column11_' + i).val());
					});

					$("#column1Label"+ i).text("対象者");
					$('#column2Label'+ i).text("稼働時間");
					$('#column3Label'+ i).text("出勤");
					$('#column4Label'+ i).text("欠勤");
					$('#column5Label'+ i).text("残業");
					$('#column6Label'+ i).text("有給");
					$('#column7Label'+ i).text("区分");
					$('#column8Label'+ i).text("データ");
					$('#column9Label'+ i).text("合計額");
					$('#column10Label'+ i).text("出力");
					$('#column11Label'+ i).text("リンク");
				}
				if($('#type').val()==2){
					$('#div-column11').attr('id','div-column11_'+ i);
					$('#div-column11_'+ i).attr('class','col-md-1 float-left text-center div-column11');
					$('#column11sub').attr('class','col-md-1 float-left text-center');
					$('#div-column8').attr('id','div-column8_'+ i);
					$('#div-column9').attr('id','div-column9_'+ i);
					$('#div-column10').attr('id','div-column10_'+ i);
					$('#div-column8_'+ i).attr('class','col-md-1 float-left text-center mt-2 div-column8');
					$('#div-column9_'+ i).attr('class','col-md-1 float-left text-center mt-2 div-column9');
					$('#div-column10_'+ i).attr('class','col-md-1 float-left text-center div-column10');
					$('#column8sub').attr('class','col-md-1 float-left text-center');
					$('#column9sub').attr('class','col-md-1 float-left text-center');
					$('#column10sub').attr('class','col-md-1 float-left text-center');
					$('#column2_' + i).text(data["column2_" + i]);
					$('#column3_' + i).text(data["column3_" + i]);
					$('#column4_' + i).text(data["column4_" + i]);
					$('#column5_' + i).text(data["column5_" + i]);
					$('#column6_' + i).text(data["column6_" + i]);
					$('#column7_' + i).text(data["column7_" + i]);
					$('#column8_' + i).text(data["column8_" + i]);
					$('#column9_' + i).text(data["column9_" + i]);
					sum2 += Number(data["column2_" + i]);
					sum3 += Number(data["column3_" + i]);
					sum4 += Number(data["column4_" + i]);
					sum5 += Number(data["column5_" + i]);
					sum6 += Number(data["column6_" + i]);
					sum7 += Number(data["column7_" + i]);
					sum8 += Number(data["column8_" + i]);
					sum9 += Number(data["column9_" + i]);
					$('#column10_'+i).on('click', function() {
						outputDemand($('#column10_' + i).val());
					});
					$('#column11_'+i).on('click', function() {
						editDemand($('#column11_' + i).val());
					});

					$("#column1Label"+ i).text("対象者");
					$('#column2Label'+ i).text("合計");
					$('#column3Label'+ i).text("旅費交通費");
					$('#column4Label'+ i).text("消耗品費");
					$('#column5Label'+ i).text("会議費");
					$('#column6Label'+ i).text("通信費");
					$('#column7Label'+ i).text("支払手数料");
					$('#column8Label'+ i).text("交際費");
					$('#column9Label'+ i).text("その他");
					$('#column10Label'+ i).text("出力");
					$('#column11Label'+ i).text("リンク");
				}
				if($('#type').val()==3){
					$('#column11_'+i).on('click', function() {
						editSkill($('#column11_' + i).val());
					});

				}
				if($('#type').val()==4){
					$('#column11_'+i).on('click', function() {
						editInfo($('#column11_' + i).val());
					});
				}
				if(Number($('#type').val())>2){
					if(data["column9_" + i]==0){
						$('#column9_' + i).text("自社");
					}else{
						$('#column9_' + i).text("現場");
					}
					$("#column1Label"+ i).text("対象者");
					$('#column2Label'+ i).text("");
					$('#column3Label'+ i).text("");
					$('#column4Label'+ i).text("");
					$('#column5Label'+ i).text("");
					$('#column6Label'+ i).text("");
					$('#column7Label'+ i).text("");
					$('#column8Label'+ i).text("");
					$('#column9Label'+ i).text("区分");
					$('#column10Label'+ i).text("");
					$('#column11Label'+ i).text("リンク");
					$('#div-column8').attr('id','div-column8_'+ i);
					$('#div-column9').attr('id','div-column9_'+ i);
					$('#div-column10').attr('id','div-column10_'+ i);
					$('#div-column8_'+ i).attr('class','none');
					$('#div-column9_'+ i).attr('class','col-md-2 float-left text-center mt-2 div-column9');
					$('#div-column10_'+ i).attr('class','none');
					$('#column8sub').attr('class','none');
					$('#column9sub').attr('class','col-md-2 float-left text-center');
					$('#column10sub').attr('class','none');
					$('#div-column11').attr('id','div-column11_'+ i);
					$('#div-column11_'+ i).attr('class','col-md-2 float-left text-center div-column11');
					$('#column11sub').attr('class','col-md-2 float-left text-center');
				}
				if(Number($('#member').val())>6&&Number($('#type').val())!=4){
					$('#column1Label'+i).text("対象月");
				}
			}
			if(Number($('#member').val())>6&&Number($('#type').val())!=4){
				$('#column1').text("対象月");
			}
			if($('#type').val()==2){
				$('#column2_' + 0).text(sum2.toLocaleString());
				$('#column3_' + 0).text(sum3.toLocaleString());
				$('#column4_' + 0).text(sum4.toLocaleString());
				$('#column5_' + 0).text(sum5.toLocaleString());
				$('#column6_' + 0).text(sum6.toLocaleString());
				$('#column7_' + 0).text(sum7.toLocaleString());
				$('#column8_' + 0).text(sum8.toLocaleString());
				$('#column9_' + 0).text(sum9.toLocaleString());

				$("#column1Label"+ 0).text("");
				$('#column2Label'+ 0).text("総計");
				$('#column3Label'+ 0).text("旅費交通費");
				$('#column4Label'+ 0).text("消耗品費");
				$('#column5Label'+ 0).text("会議費");
				$('#column6Label'+ 0).text("通信費");
				$('#column7Label'+ 0).text("支払手数料");
				$('#column8Label'+ 0).text("交際費");
				$('#column9Label'+ 0).text("その他");
				$('#column10Label'+ 0).text("");
				$('#column11Label'+ 0).text("");
				if($('#month').val()==13){
					$('#check').text("");
				}else{
					if(data["check"]!=undefined){
						if(data["check"]==1){
							$('#check').text("処理済み");
						}else{
							$('#check').text("未処理");
						}
					}
				}
			}else{
				$('#check').text("");
			}
			if($('#month').val()==13){
				$("#outputAll").hide();
			}else{
				$("#outputAll").show();
			}
		}
	});
}
function editAttendance(val) {
	var request = {
	};
	if(Number($('#member').val())<=6){
		request.id = val;
		request.month = $('#month').val();
	}else{
		request.id = Number($('#member').val())-6;
		request.month = val;
	}
	$.ajax({
		url : "editAttendance",
		type : "POST",
		data : request,
		success : function(data) {
			location.href = "attendance.jsp";
		}
	});
}

function outputAttendance(id){
	var request = {
		type : 1,
		all : "no"
	};
	if(Number($("#member").val())>6){
		request["id"] = Number($("#member").val())-6;
	}else{
		request["id"] = id;
	}
	if($('#month').val()!=13){
		request["month"] = $("#month").val();
	}else{
		request["month"] = id;
	}
	$.ajax({
		url : "OutputExcel",
		type : "POST",
		data : request,
		success : function(data) {
			window.location.href = data["url"];
		}
	});
}

function editDemand(val) {
	var request = {
	};
	if(Number($('#member').val())<=6){
		request.id = val;
		request.month = $('#month').val();
	}else{
		request.id = Number($('#member').val())-6;
		request.month = val;
	}
	$.ajax({
		url : "editDemand",
		type : "POST",
		data : request,
		success : function(data) {
			location.href = "demand.jsp";
		}
	});
}
function outputDemand(id){
	var request = {
		type : 2,
		all : "no"
	};
	if(Number($("#member").val())>6){
		request["id"] = Number($("#member").val())-6;
	}else{
		request["id"] = id;
	}
	if($('#month').val()!=13){
		request["month"] = $("#month").val();
	}else{
		request["month"] = id;
	}
	$.ajax({
		url : "OutputExcel",
		type : "POST",
		data : request,
		success : function(data) {
			window.location.href = data["url"];
		}
	});
}

function outputAll(){
	var request = {
		type : $('#type').val(),
		month : $('#month').val(),
		all : "yes"
	};
	if($('#type').val()!=3){
		$.ajax({
			url : "OutputExcel",
			type : "POST",
			data : request,
			success : function(data) {
				window.location.href = data["url"];
			}
		});
	}else{
		$.ajax({
			url : "OutputCSV",
			type : "POST",
			data : request,
			success : function(data) {
				window.location.href = data["url"];
			}
		});
	}
}

function editSkill(val) {
	var request = {
	};
	if(Number($('#member').val())<=6){
		request.id = val;
		request.month = $('#month').val();
	}else{
		request.id = Number($('#member').val())-6;
		request.month = val;
	}
	$.ajax({
		url : "editSkill",
		type : "POST",
		data : request,
		success : function(data) {
			location.href = "skill.jsp";
		}
	});
}

function editInfo(val) {
	var request = {
	};
	if(Number($('#member').val())<=6){
		request.id = val;
	}else{
		request.id = Number($('#member').val())-6;
	}
	$.ajax({
		url : "editInfo",
		type : "POST",
		data : request,
		success : function(data) {
			location.href = "editInfo.jsp";
		}
	});
}
function checkAll(val) {
	var request = {
		all:"yes",
		month:$('#month').val()
	};
	$.ajax({
		url : "UpdateDemand",
		type : "POST",
		data : request,
		success : function(data) {
			alert("手続きが完了しました！");
		}
	});
}