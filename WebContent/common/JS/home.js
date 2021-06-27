var authority = 1;
var htmlAttendance = '<a class="dropdown-item" href="attendance.jsp">勤務表の提出を完了させてください</a>';
var htmlDemand = '<a class="dropdown-item" href="demand.jsp"">請求書の提出を完了させてください</a>';
var htmlSkill = '<a class="dropdown-item" href="skill.jsp">スキルシートの更新を完了させてください</a>';
var htmlPTOAnnotation = '<a class="dropdown-item" href="pto.jsp">未承認の申請があります</a>';
var htmlBoard = '<a href="board.jsp"><h5 id="boardSubject"></h5></a>';
var htmlPTOLost = '<a href="pto.jsp"><h5 id="ptoLost"></h5></a>';
var htmlPTOConsume = '<a href="pto.jsp"><h5 id="ptoConsume"></h5></a>';
var num;
$(document).ready(function() {
	changeAction();
});

function changeAction() {
	var request = {
	};
	$.ajax({
		url : "Home",
		type : "POST",
		data : request,
		success : function(data) {
			num = 0;
			$("#welcome").text(data["name"]+"さん　お疲れ様です！　"+data["welcome"]);
			if(data["attendanceUPD"]!= undefined){
				$('#attendanceUPD').text("　"+data["attendanceUPD"]);
				$('#attendanceMsg').text(data["attendanceMsg"]+"　");
			}else{
				$('#attendanceUPD').text("　1");
				$('#attendanceMsg').text("days　");
			}
			if(data["boardUPD"]!= undefined){
				$('#boardUPD').text("　"+data["boardUPD"]);
				$('#boardMsg').text(data["boardMsg"]+"　");
			}else{
				$('#boardUPD').text("　1");
				$('#boardMsg').text("days　");
			}
			if(data["ptoUPD"]!= undefined){
				$('#ptoUPD').text("　"+data["ptoUPD"]);
				$('#ptoMsg').text(data["ptoMsg"]+"　");
			}else{
				$('#ptoUPD').text("　1");
				$('#ptoMsg').text("days　");
			}
			if(data["authority"]==1){
				$('#managedNav').remove();
				$('#editInfoNav').remove();
				$('#registerNav').remove();
				$('#mailNav').remove();
				$('#settingNav').remove();
			}
			if(data["flag"]==0){
				alert("初回パスワードをお使いなので変更をお願いします！");
				location.href = "password_change_request.jsp";
			}

			if(data["attendanceFlag"]==0){
				$('#notification').append(htmlAttendance);
				$('#notificationSub').append(htmlAttendance);
				num++;
			}
			if(data["demandFlag"]==0){
				$('#notification').append(htmlDemand);
				$('#notificationSub').append(htmlDemand);
				num++;
			}
			if(data["skillFlag"]==0){
				$('#notification').append(htmlSkill);
				$('#notificationSub').append(htmlSkill);
				num++;
			}
			if(data["authority"]!=1){
				if(data["ptoNumber"]>0){
					$('#notification').append(htmlPTOAnnotation);
					$('#notificationSub').append(htmlPTOAnnotation);
					num++;
				}
			}
			$('#notificationNum').html(num);
			$('#notificationNumSub').html("お知らせが"+num+"件あります");
			if(num==0){
				$('#notificationsNav').remove();
			}

			$('#homeNav p').css('display','block');
			$('#attendanceNav p').css('display','block');
			$('#demandNav p').css('display','block');
			$('#skillNav p').css('display','block');
			$('#ptoNav p').css('display','block');
			$('#boardNav p').css('display','block');
			$('#rentalNav p').css('display','block');
			$('#editInfoNav p').css('display','block');
			$('#registerNav p').css('display','block');
			$('#mailNav p').css('display','block');
			$('#managedNav p').css('display','block');
			$('#settingNav p').css('display','block');
			$('#logoutNav p').css('display','block');
			$('#operatingTime').text(data["operatingTime"]+"時間");
			$('#boardCard').append(htmlBoard);
			$('#boardSubject').text(data["boardSubject"]);
			if(data["consumePTO"]<5){
				$('#PTO').append(htmlPTOConsume);
			}
			$('#PTO').append(htmlPTOLost);
			$('#ptoLost').text("あと"+data["remainingTime"]+"ヶ月で"+data["lostPTO"]+"日分 消失");
			$('#ptoConsume').text("年間で"+data["consumePTO"]+"日分 使用");
		}
	});
}