//html = '<div id="demand" class="clearfix mt-3 demandD"><div class="d-none"><input id="demandId" type="text" class="form-control" name="demandId"></div><div class="col-md-1 float-left text-center dateD"><h5 id="targetMonth" class="d-inline-block"></h5><select name="targetDay" class="mt-3" id="targetDay"><option value=1>1</option><option value=2>2</option><option value=3>3</option><option value=4>4</option><option value=5>5</option><option value=6>6</option><option value=7>7</option><option value=8>8</option><option value=9>9</option><option value=10>10</option><option value=11>11</option><option value=12>12</option><option value=13>13</option><option value=14>14</option><option value=15>15</option><option value=16>16</option><option value=17>17</option><option value=18>18</option><option value=19>19</option><option value=20>20</option><option value=21>21</option><option value=22>22</option><option value=23>23</option><option value=24>24</option><option value=25>25</option><option value=26>26</option><option value=27>27</option><option value=28>28</option><option value=29>29</option><option value=30>30</option><option value=31>31</option></select></div><div class="col-md-1 float-left text-center divisionD"><select name="division" class="mt-3" id="division"><option value=1>旅費交通費</option><option value=2>消耗品費</option><option value=3>会議費</option><option value=4>通信費</option><option value=5>支払手数料</option><option value=6>交際費</option><option value=7>その他</option></select></div><div class="col-md-1 float-left text-center pt-2 pb-2 money"><input id="money" type="text" class="form-control" name="money"></div><div class="col-md-1 float-left text-center pt-2 pb-2 multi"><input id="multi" type="text" class="form-control" name="multi"></div><div class="col-md-1 float-left text-center pt-2 pb-2 sumMoney"><h5 id="sumMoney"></h5></div><div class="col-md-1 float-left text-center pt-2 pb-2 to"><input id="to" type="text" class="form-control" name="to"></div><div class="col-md-2 float-left text-center pt-2 pb-2 description"><input id="description" type="text" class="form-control" name="description"></div><div class="col-md-2 float-left text-center pt-2 pb-2 remarks"><input id="remarks" type="text" class="form-control" name="remarks"></div><div class="col-md-1 float-left text-center recipt"><select name="recipt" class="mt-3" id="recipt"><option value=1>✖️</option><option value=2>⚫️</option></select></div><div class="col-md-1 float-left text-center flag"><select name="flag" class="mt-3" id="flag"><option value=0>未</option><option value=1>済</option></select></div></div>'
html = '<div id="demand" class="clearfix mt-3 demandD"><div class="d-none"><input id="demandId" type="text" class="form-control" name="demandId"></div><div class="mt-3 float-left text-center dateD"><h5>対象日</h5></div><div class="mt-3 float-left text-center dateD"><h5>区分</h5></div><div class="col-md-1 float-left text-center dateD"><h5 id="targetMonth" class="d-inline-block"></h5><select name="targetDay" class="mt-3" id="targetDay"><option value=1>1</option><option value=2>2</option><option value=3>3</option><option value=4>4</option><option value=5>5</option><option value=6>6</option><option value=7>7</option><option value=8>8</option><option value=9>9</option><option value=10>10</option><option value=11>11</option><option value=12>12</option><option value=13>13</option><option value=14>14</option><option value=15>15</option><option value=16>16</option><option value=17>17</option><option value=18>18</option><option value=19>19</option><option value=20>20</option><option value=21>21</option><option value=22>22</option><option value=23>23</option><option value=24>24</option><option value=25>25</option><option value=26>26</option><option value=27>27</option><option value=28>28</option><option value=29>29</option><option value=30>30</option><option value=31>31</option></select></div><div class="col-md-1 float-left text-center divisionD"><select name="division" class="mt-3" id="division"><option value=1>旅費交通費</option><option value=2>消耗品費</option><option value=3>会議費</option><option value=4>通信費</option><option value=5>支払手数料</option><option value=6>交際費</option><option value=7>その他</option></select></div><div class="mt-3 float-left text-center money"><h5>金額</h5></div><div class="mt-3 float-left text-center multi"><h5>口数</h5></div><div class="mt-3 float-left text-center sumMoney"><h5>合計</h5></div><div class="col-md-1 float-left text-center pt-2 pb-2 money"><input id="money" type="text" class="form-control" name="money" placeholder="150"></div><div class="col-md-1 float-left text-center pt-2 pb-2 multi"><input id="multi" type="text" class="form-control" name="multi" placeholder="20"></div><div class="col-md-1 float-left text-center pt-2 pb-2 sumMoney"><h5 id="sumMoney"></h5></div><div class="mt-3 float-left text-center to"><h5>支払先</h5></div><div class="mt-3 float-left text-center descriptions"><h5>摘要</h5></div><div class="col-md-1 float-left text-center pt-2 pb-2 to"><input id="to" type="text" class="form-control" name="to" placeholder="JR"></div><div class="col-md-2 float-left text-center pt-2 pb-2 descriptions"><input id="description" type="text" class="form-control" name="description" placeholder="通勤"></div><div class="mt-3 float-left text-center remarks"><h5>詳細</h5></div><div class="col-md-2 float-left text-center pt-2 pb-2 remarks"><input id="remarks" type="text" class="form-control" name="remarks" placeholder="錦糸町⇄秋葉原"></div><div class="mt-3 float-left text-center recipt"><h5>領収書</h5></div><div class="mt-3 float-left text-center flag"><h5>処理</h5></div><div class="col-md-1 float-left text-center recipt"><select name="recipt" class="mt-3" id="recipt"><option value=1>✖️</option><option value=2>⚫️</option></select></div><div class="col-md-1 float-left text-center flag"><select name="flag" class="mt-3" id="flag"><option value=0>未</option><option value=1>済</option></select></div></div>'
html1 = '<div id="demand" class="clearfix mt-3 demandD"><div class="d-none"><input id="demandId" type="text" class="form-control" name="demandId"></div><div class="mt-3 float-left text-center dateDM"><h5>対象日</h5></div><div class="mt-3 float-left text-center dateDM"><h5>区分</h5></div><div class="mt-3 float-left text-center dateDM"><h5>　　</h5></div><div class="col-md-1 float-left text-center dateDM"><h5 id="targetMonth" class="d-inline-block"></h5><select name="targetDay" class="mt-3" id="targetDay"><option value=1>1</option><option value=2>2</option><option value=3>3</option><option value=4>4</option><option value=5>5</option><option value=6>6</option><option value=7>7</option><option value=8>8</option><option value=9>9</option><option value=10>10</option><option value=11>11</option><option value=12>12</option><option value=13>13</option><option value=14>14</option><option value=15>15</option><option value=16>16</option><option value=17>17</option><option value=18>18</option><option value=19>19</option><option value=20>20</option><option value=21>21</option><option value=22>22</option><option value=23>23</option><option value=24>24</option><option value=25>25</option><option value=26>26</option><option value=27>27</option><option value=28>28</option><option value=29>29</option><option value=30>30</option><option value=31>31</option></select></div><div class="col-md-1 float-left text-center divisionDM"><select name="division" class="mt-3" id="division"><option value=1>旅費交通費</option><option value=2>消耗品費</option><option value=3>会議費</option><option value=4>通信費</option><option value=5>支払手数料</option><option value=6>交際費</option><option value=7>その他</option></select></div><div class="col-md-1 float-left text-center divisionDM"><button type="submit" class="mt-3 btn btn-primary" id="deleteM">削除</button></div><div class="mt-3 float-left text-center money"><h5>金額</h5></div><div class="mt-3 float-left text-center multi"><h5>口数</h5></div><div class="mt-3 float-left text-center sumMoney"><h5>合計</h5></div><div class="col-md-1 float-left text-center pt-2 pb-2 money"><input id="money" type="text" class="form-control" name="money" placeholder="150"></div><div class="col-md-1 float-left text-center pt-2 pb-2 multi"><input id="multi" type="text" class="form-control" name="multi" placeholder="20"></div><div class="col-md-1 float-left text-center pt-2 pb-2 sumMoney"><h5 id="sumMoney"></h5></div><div class="mt-3 float-left text-center to"><h5>支払先</h5></div><div class="mt-3 float-left text-center descriptions"><h5>摘要</h5></div><div class="col-md-1 float-left text-center pt-2 pb-2 to"><input id="to" type="text" class="form-control" name="to" placeholder="JR"></div><div class="col-md-2 float-left text-center pt-2 pb-2 descriptions"><input id="description" type="text" class="form-control" name="description" placeholder="通勤"></div><div class="mt-3 float-left text-center remarks"><h5>詳細</h5></div><div class="col-md-2 float-left text-center pt-2 pb-2 remarks"><input id="remarks" type="text" class="form-control" name="remarks" placeholder="錦糸町⇄秋葉原"></div><div class="mt-3 float-left text-center recipt"><h5>領収書</h5></div><div class="mt-3 float-left text-center flag"><h5>処理</h5></div><div class="col-md-1 float-left text-center recipt"><select name="recipt" class="mt-3" id="recipt"><option value=1>✖️</option><option value=2>⚫️</option></select></div><div class="col-md-1 float-left text-center flag"><select name="flag" class="mt-3" id="flag"><option value=0>未</option><option value=1>済</option></select></div></div>'
var numbersAplication = 0;
var deleteLimit=1;
var authority = 1;
var sum1 = 0;
var sum2 = 0;
var sum3 = 0;
var sum4 = 0;
var sum5 = 0;
var sum6 = 0;
var sum7 = 0;
var month;
var inputCheck=0;
$(document).ready(function() {
	changeMonth(13);
});
$('#month').change(function() {
	changeMonth($('#month').val());
});
$('#append').on('click', function() {
	appendForm();
});
$('#delete').on('click', function() {
	if(numbersAplication>deleteLimit){
		$("#demand"+numbersAplication).remove();
		numbersAplication--;
		changeMoney(1);
	}
});
$('#update').on('click', function() {
	var answer = confirm('入力に間違いはないですか？');
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

function changeMoney(i){
	$('#sumMoney'+i).text(Number($('#money'+i).val())*Number($('#multi'+i).val()));
	sum1 =0;
	sum2 =0;
	sum3 =0;
	sum4 =0;
	sum5 =0;
	sum6 =0;
	sum7 =0;
	for(let i=1;i<=numbersAplication;i++){
		switch($('#division'+i).val()){
		case "1":
			sum1 = sum1+Number($('#sumMoney'+i).text());
			break;
		case "2":
			sum2 = sum2+Number($('#sumMoney'+i).text());
			break;
		case "3":
			sum3 = sum3+Number($('#sumMoney'+i).text());
			break;
		case "4":
			sum4 = sum4+Number($('#sumMoney'+i).text());
			break;
		case "5":
			sum5 = sum5+Number($('#sumMoney'+i).text());
			break;
		case "6":
			sum6 = sum6+Number($('#sumMoney'+i).text());
			break;
		case "7":
			sum7 = sum7+Number($('#sumMoney'+i).text());
			break;
		}
	}
		$('#sum8').text((sum1+sum2+sum3+sum4+sum5+sum6+sum7).toLocaleString());

		$('#sum1').text(sum1.toLocaleString()+"円");

		$('#sum2').text(sum2.toLocaleString()+"円");

		$('#sum3').text(sum3.toLocaleString()+"円");

		$('#sum4').text(sum4.toLocaleString()+"円");

		$('#sum5').text(sum5.toLocaleString()+"円");

		$('#sum6').text(sum6.toLocaleString()+"円");

		$('#sum7').text(sum7.toLocaleString()+"円");

}

function appendForm(){
	numbersAplication++;
	var num = numbersAplication
	$('#form2').append(html);
	$('#demand').attr('id', 'demand'+numbersAplication);
	$('#demandId').val(0);
	$('#demandId').attr('id', 'demandId'+numbersAplication);
	$('#recipt').attr('class', "p-1 mt-3");
	$('#recipt').attr('id', 'recipt'+numbersAplication);
	$('#targetMonth').attr('id', 'targetMonth'+numbersAplication);
	$('#targetDay').attr('class', "p-1 mt-3");
	$('#targetDay').attr('id', 'targetDay'+numbersAplication);
	$('#multi').attr('id', 'multi'+numbersAplication);
	$('#multi'+numbersAplication).val(1);
	$('#money').attr('id', 'money'+numbersAplication);
	$('#to').attr('id', 'to'+numbersAplication);
	$('#division').attr('class', "p-1 mt-3");
	$('#division').attr('id', 'division'+numbersAplication);
	$('#sumMoney').attr('id', 'sumMoney'+numbersAplication);
	$('#description').attr('id', 'description'+numbersAplication);
	$('#remarks').attr('id', 'remarks'+numbersAplication);
	$('#flag').attr('class', "p-1 mt-3");
	$('#flag').attr('id', 'flag'+numbersAplication);
	$('#money'+numbersAplication).change(function() {
		changeMoney(num);
	});
	$('#multi'+numbersAplication).change(function() {
		changeMoney(num);
	});
	$('#division'+numbersAplication).change(function() {
		changeMoney(num);
	});
	if(month!=new Date().getMonth()+1&&authority==1){
		if(monthText==new Date().getMonth()&&new Date().getDate()<3){
			$("#demand" + numbersAplication).css("background", "white");
			$("#recipt"+ numbersAplication).attr("disabled",false);
			$("#targetDay"+ numbersAplication).attr("disabled",false);
			$("#multi"+ numbersAplication).attr("disabled",false);
			$("#money"+ numbersAplication).attr("disabled",false);
			$("#to"+ numbersAplication).attr("disabled",false);
			$("#division"+ numbersAplication).attr("disabled",false);
			$("#description"+ numbersAplication).attr("disabled",false);
			$("#remarks"+ numbersAplication).attr("disabled",false);
			$("#flag"+ numbersAplication).attr("disabled",false);
		}else{
			$("#demand" + numbersAplication).css("background", "slategray");
			$("#recipt"+ numbersAplication).attr("disabled",true);
			$("#targetDay"+ numbersAplication).attr("disabled",true);
			$("#multi"+ numbersAplication).attr("disabled",true);
			$("#money"+ numbersAplication).attr("disabled",true);
			$("#to"+ numbersAplication).attr("disabled",true);
			$("#division"+ numbersAplication).attr("disabled",true);
			$("#description"+ numbersAplication).attr("disabled",true);
			$("#remarks"+ numbersAplication).attr("disabled",true);
			$("#flag"+ numbersAplication).attr("disabled",true);
		}
	}
	if(authority==1){
		$('#flag' + numbersAplication).attr('disabled', true);
	}
	if (month == "22") {
		$('#targetMonth' + numbersAplication).text("2 /　");
		$('select#targetDay'+numbersAplication+' option[value=30]').remove();
		$('select#targetDay'+numbersAplication+' option[value=31]').remove();
	} else {
		$('#targetMonth' + numbersAplication).text($('#month').val()+" /　");
		switch (month) {
		case "2":
			$('select#targetDay'+numbersAplication+' option[value=29]').remove();
			$('select#targetDay'+numbersAplication+' option[value=30]').remove();
			$('select#targetDay'+numbersAplication+' option[value=31]').remove();
			break;
		case "1":
		case "3":
		case "5":
		case "7":
		case "8":
		case "10":
		case "12":
			break;
		case "4":
		case "6":
		case "9":
		case "11":
			$('select#targetDay'+numbersAplication+' option[value=31]').remove();
			break;
		}
	}

}
function changeMonth(num) {
	var request = {
			month : num
	};
	$.ajax({
		url : "SearchDemand",
		type : "POST",
		data : request,
		success : function(data) {
			$('#targetMonth0').text(num+" /　");
			$('#memo').val(data["memo"]);
			for (let i = 1; i <= numbersAplication; i++) {
				$('#demand' + i).remove();
			}
			numbersAplication = 0;
			$('#editUser').html(data["editUser"]);
			month = data["month"];
			if (month == 22) {
				$('#month').val(2);
			}else{
				$('#month').val(month);
			}
			numbers = Number(data["numbersAplication"]);
			deleteLimit = Number(data["numbersAplication"]);
			if(deleteLimit==0){
				deleteLimit++;
			}
			authority = Number(data["authority"]);
			if(numbers==0){
				if(month==new Date().getMonth()+1){
					appendForm();
				}
			}
			if(authority!=2){
				$('#multiCheck').css('display', 'none');
				$('#output').css('display', 'none');
				$("#memo").attr("disabled",true);
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
			for (let i = 1; i <= numbers; i++) {
				numbersAplication++;
				if(authority==1){
					$('#form2').append(html);
				}else{
					$('#form2').append(html1);
					$('#deleteM').attr('id', 'deleteM'+numbersAplication);
				}
				$('#demand').attr('id', 'demand'+i);
				$('#demandId').attr('id', 'demandId'+i);
				$('#demandId' + i).val(data["demandId" + i]);
				$('#recipt').attr('class', "p-1 mt-3");
				$('#recipt').attr('id', 'recipt'+i);
				$('#recipt' + i).val(data["recipt" + i]);
				$('#targetMonth').attr('id', 'targetMonth'+i);
				$('#targetDay').attr('class', "p-1 mt-3");
				$('#targetDay').attr('id', 'targetDay'+i);
				$('#targetDay' + i).val(data["targetDay" + i]);
				if(authority==2){
					$('#deleteM').attr('id', 'deleteM'+i);
					$('#deleteM' + i).val(data["demandId" + i]);
					$('#deleteM'+ i).on('click', function() {
						deleteM($('#deleteM'+ i).val());
					});
				}
				$('#multi').attr('id', 'multi'+i);
				$('#multi' + i).val(data["multi" + i]);
				$('#money').attr('id', 'money'+i);
				$('#money' + i).val(data["money" + i]);
				$('#to').attr('id', 'to'+i);
				$('#to' + i).val(data["to" + i]);
				$('#sumMoney').attr('id', 'sumMoney'+i);
				$('#sumMoney' + i).text(Number(data["money" + i])*Number(data["multi" + i]));
				$('#division').attr('class', "p-1 mt-3");
				$('#division').attr('id', 'division'+i);
				$('#division' + i).val(data["division" + i]);
				$('#description').attr('id', 'description'+i);
				$('#description' + i).val(data["description" + i]);
				$('#remarks').attr('id', 'remarks'+i);
				$('#remarks' + i).val(data["remarks" + i]);
				$('#flag').attr('class', "p-1 mt-3");
				$('#flag').attr('id', 'flag'+i);
				$('#flag' + i).val(data["flag" + i]);
				$('#money'+i).change(function() {
					changeMoney(i);
				});
				$('#multi'+i).change(function() {
					changeMoney(i);
				});
				if(data["flag" + i]==1){
					$("#demand" + i).css("background", "slategray");
					$('#recipt' + i).attr('disabled', true);
					$('#multi' + i).attr('disabled', true);
					$('#targetDay' + i).attr('disabled', true);
					$('#money' + i).attr('readonly', true);
					$('#to' + i).attr('readonly', true);
					$('#division' + i).attr('disabled', true);
					$('#description' + i).attr('readonly', true);
					$('#remarks' + i).attr('readonly', true);
					$('#flag' + i).attr('disabled', true);
				}else{
					$("#demand" + i).css("background", "white");
					$('#recipt' + i).attr('disabled', false);
					$('#multi' + i).attr('disabled', false);
					$('#targetDay' + i).attr('disabled', false);
					$('#money' + i).attr('readonly', false);
					$('#to' + i).attr('readonly', false);
					$('#division' + i).attr('disabled', false);
					$('#description' + i).attr('readonly', false);
					$('#remarks' + i).attr('readonly', false);
					$('#flag' + i).attr('disabled', false);
				}
				if(authority==1){
					$('#flag' + i).attr('disabled', true);
				}else{
					$('#flag' + i).attr('disabled', false);
				}
				if (month == "22") {
					$('#targetMonth' + i).text("2 /　");
					$('select#targetDay'+i+' option[value=30]').remove();
					$('select#targetDay'+i+' option[value=31]').remove();
				} else {
					$('#targetMonth' + i).text(month+" /　");
					switch (month) {
					case "2":
						$('select#targetDay'+i+' option[value=29]').remove();
						$('select#targetDay'+i+' option[value=30]').remove();
						$('select#targetDay'+i+' option[value=31]').remove();
						break;
					case "1":
					case "3":
					case "5":
					case "7":
					case "8":
					case "10":
					case "12":
						break;
					case "4":
					case "6":
					case "9":
					case "11":
						$('select#targetDay'+i+' option[value=31]').remove();
						break;
					}
				}
			}
			$('#sum1').text(Number(data["sum1"]).toLocaleString()+"円");
			$('#sum2').text(Number(data["sum2"]).toLocaleString()+"円");
			$('#sum3').text(Number(data["sum3"]).toLocaleString()+"円");
			$('#sum4').text(Number(data["sum4"]).toLocaleString()+"円");
			$('#sum5').text(Number(data["sum5"]).toLocaleString()+"円");
			$('#sum6').text(Number(data["sum6"]).toLocaleString()+"円");
			$('#sum7').text(Number(data["sum7"]).toLocaleString()+"円");
			$('#sum8').text(Number(data["sum8"]).toLocaleString()+"円");
			$('#submitFlag').text(data["submitFlag"])

			$('#lastUpdate').text(data["update"]);
			if(month==22){
				monthText=2;
			}else{
				monthText=month;
			}
			if(monthText==new Date().getMonth()+1&&new Date().getDate()<3){
				$('#submitFlag').text("未提出")
			}
			if(monthText!=new Date().getMonth()+1&&authority==1){
				if(monthText==new Date().getMonth()&&new Date().getDate()<3){
					$('#update').show();
					$('#append').show();
					$('#delete').show();
					$('#submit').show();
				}else{
					for (let i = 1; i <= numbersAplication; i++) {
					$("#demand" + i).css("background", "slategray");
					$('#recipt' + i).attr('disabled', true);
					$('#multi' + i).attr('disabled', true);
					$('#targetDay' + i).attr('disabled', true);
					$('#money' + i).attr('readonly', true);
					$('#to' + i).attr('readonly', true);
					$('#division' + i).attr('disabled', true);
					$('#description' + i).attr('readonly', true);
					$('#remarks' + i).attr('readonly', true);
					$('#flag' + i).attr('disabled', true);
					}
					$('#update').hide();
					$('#append').hide();
					$('#delete').hide();
					$('#submit').hide();
				}
			}else{
				$('#update').show();
				$('#append').show();
				$('#submit').show();
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
	for(let i=1;i<=numbersAplication;i++){
		if(isNaN($('#money'+i).val())){
			alert("金額は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#money'+i).val().startsWith('0')&&$('#money'+i).val().length!=1){
			alert("金額は整数値で入力してください！")
			inputCheck=1;
		}else if($('#money'+i).val().includes(".")){
			alert("金額は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#money'+i).val()==""){
			alert("金額の項目を入力してください！")
			inputCheck=1;
		}
		if(isNaN($('#multi'+i).val())){
			alert("口数は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#multi'+i).val().startsWith('0')&&$('#multi'+i).val().length!=1){
			alert("口数は整数値で入力してください！")
			inputCheck=1;
		}else if($('#multi'+i).val().includes(".")){
			alert("口数は整数値で入力してください！")
			inputCheck=1;
		}
		if($('#multi'+i).val()==""){
			alert("口数の項目を入力してください！")
			inputCheck=1;
		}
		if($('#to'+i).val()==""){
			alert("支払先の項目を入力してください！")
			inputCheck=1;
		}
		if($('#to'+i).val().length>100){
			alert("支払先は45文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#description'+i).val().length>100){
			alert("摘要は100文字以内で入力してください！")
			inputCheck=1;
		}
		if($('#remarks'+i).val().length>100){
			alert("詳細は100文字以内で入力してください！")
			inputCheck=1;
		}
	}
	var numbers = 0;
	var request = {
			submit:num
	};
	for(let i=1;i<=numbersAplication;i++){
		request["targetDay"+i] = $('#targetDay'+i).val();
		request["aplicationMonth"+i] = new Date().getMonth()+1;
		request["aplicationDay"+i] = new Date().getDay();
		request["aplicationAmount"+i] = $('#money'+i).val();
		request["description"+i] = $('#description'+i).val();
		request["to"+i] = $('#to'+i).val();
		request["division"+i] = $('#division'+i).val();
		request["remarks"+i] = $('#remarks'+i).val();
		request["flag"+i] = $('#flag'+i).val();
		request["recipt"+i] = $('#recipt'+i).val();
		request["multi"+i] = $('#multi'+i).val();
		request["demandId"+i] = $('#demandId'+i).val();
	}
	request.memo=$('#memo').val();
	request.multiCheck=$('#multiCheck').val();
	request.month=$('#month').val();
	request.numbersAplication = numbersAplication;
	if(inputCheck==0){
		$.ajax({
			url : "UpdateDemand",
			type : "POST",
			data : request,
			success : function(data) {
				$('#lastUpdate').text(data["update"]);
				if(num==0){
					for (let i = 1; i <= numbersAplication; i++) {
						$('#demandId' + i).val(data["demandId" + i]);
					}
					alert("更新しました！");
				}else{
					$('#submitFlag').text(data["submitFlag"])
					for (let i = 1; i <= numbersAplication; i++) {
						$('#demandId' + i).val(data["demandId" + i]);
					}
					alert("提出しました！");
				}
			}
		});
	}
}

function deleteM(num){
	var request = {
		id:num
	};
	var answer = confirm('削除してもいいですか？');
	if(answer){
		$.ajax({
			url : "UpdateDemand",
			type : "POST",
			data : request,
			success : function(data) {
				alert("削除しました！");
			}
		});
	}
}
