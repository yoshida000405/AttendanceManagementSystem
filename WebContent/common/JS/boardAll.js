var authority = 1;
var firstUpdDate;
var lastUpdDate;
var numbers = 0;
var page = 0;
var html = '<div id="subjectB" class="mt-5 ml-3"></div><button id="id" class="d-none"></button>';
$(document).ready(function() {
	changeAction(0);
});
$('#forward').on('click', function() {
	if(page!=0){
		page--;
		changeAction(1);
	}
});
$('#next').on('click', function() {
	if(numbers==8){
		page++;
		changeAction(2);
	}
});

function changeAction(num){
	var request = {
		nowId : num,
		type : 3,
		firstUpdDate : firstUpdDate,
		lastUpdDate : lastUpdDate
	};
	$.ajax({
		url : "SearchBoard",
		type : "POST",
		data : request,
		success : function(data) {
			for (let i = 1; i <= numbers; i++) {
				$('#subjectB' + i).remove();
			}
			numbers=0;
			for(let i=1; i<=data["numbersRecord"]; i++){
				numbers++;
				$('#append').append(html);
				$('#subjectB').attr('id', 'subjectB'+numbers);
				$('#id').attr('id', 'id'+numbers);
				$('#subjectB'+numbers).text((data["date"+numbers]).substring(5,10)+"　　　　"+data["subject"+numbers]);
				$('#id'+numbers).val(data["id"+numbers]);
				$('#subjectB'+i).on('click', function() {
					changePage($('#id'+i).val());
				});
				if(i==data["numbersRecord"]){
					$('#subjectB'+i).attr('class',"mt-5 ml-3 mb-5");
				}
			}
			firstUpdDate = data["firstUpdDate"];
			lastUpdDate = data["lastUpdDate"];
			authority = data["authority"];
			if(authority==1){
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
		}
	});
}

function changePage(num){
	var request = {
		nowId : num,
		type : 5
	};
	$.ajax({
		url : "SearchBoard",
		type : "POST",
		data : request,
		success : function(data) {
			window.location.href = "board.jsp";
		}
	});
}