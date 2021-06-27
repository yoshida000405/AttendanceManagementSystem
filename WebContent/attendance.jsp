<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="common/img/apple-icon.png">
<link rel="icon" type="image/png" href="common/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>勤怠管理</title>
<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no'
	name='viewport' />
<!--     Fonts and icons     -->
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
<!-- CSS Files -->
<link href="common/css/material-dashboard.css?v=2.1.2" rel="stylesheet" />
<link rel="stylesheet" href="common/css/app.css">
<link rel="stylesheet" href="common/css/styles.css">
</head>

<body class="">
	<div class="wrapper ">
		<div>
			<!-- Navbar -->
			<div class="collapse" id="navbarToggleExternalContent">
				<div class="bg-dark p-4">
					<div data-color="purple" data-background-color="white"
						data-image="common/img/sidebar-1.jpg">
						<div>
							<ul class="nav">
								<li class="nav-item w-6 text-center" id="homeNav"><a
									 href="home.jsp"> <i class="material-icons">home</i>
										<p>ホーム</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="attendanceNav"><a
									 href="attendance.jsp"> <i
										class="material-icons">schedule</i>
										<p>勤怠入力</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="demandNav"><a
									 href="demand.jsp"> <i
										class="material-icons">content_paste</i>
										<p>請求書入力</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="skillNav"><a
									 href="skill.jsp"> <i
										class="material-icons">library_books</i>
										<p>スキルシート入力</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="ptoNav"><a
									href="pto.jsp"> <i class="material-icons">card_travel</i>
										<p>有給申請</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="boardNav"><a
									href="board.jsp"> <i class="material-icons">book_online</i>
										<p>掲示板</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="rentalNav"><a
									href="rental.jsp"> <i class="material-icons">important_devices</i>
										<p>レンタル</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="editInfoNav"><a
									href="editInfo.jsp"> <i
										class="material-icons">person</i>
										<p>登録情報変更</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="registerNav"><a
									href="register.jsp"> <i
										class="material-icons">group_add</i>
										<p>新規登録</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="mailNav"><a
									href="mail.jsp"> <i class="material-icons">mail</i>
										<p>メール</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="managedNav"><a
									href="managed.jsp"> <i
										class="material-icons">dashboard</i>
										<p>管理機能</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="settingNav"><a
									href="setting.jsp"> <i class="material-icons">miscellaneous_services</i>
										<p>設定</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="logoutNav">
									<form name="form" method="post" action="LogoutChk">
										<a  href="#" onClick="document.form.submit();"><i class="material-icons">exit_to_app</i><p>ログアウト</p></a>
									</form>
								</li>
							</ul>
						</div>
					</div>

				</div>
			</div>
			<nav class="navbar navbar-dark bg-dark">
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarToggleExternalContent"
					aria-controls="navbarToggleExternalContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
			</nav>
			<!-- End Navbar -->
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-md-10">
						<div class="card">
							<div class="card-header border">
								<div class="col-4 float-left">勤怠入力</div>
								<div id="submitFlag" class="col-4 float-left text-center"></div>
								<div id="editUser" class="col-4 float-left text-right"></div>
							</div>

							<div class="card-body">
								<div>
									<form id="form1" method="post">
										<div class="form-group row">
											<div class="col-md-4 month">
												<label for="month"
													class="col-md-4 col-form-label text-md-left monthLabel">対象月</label> <select
													name=”month” id="month" class="p-1">
													<option value=1>1月</option>
													<option value=2>2月</option>
													<option value=3>3月</option>
													<option value=4>4月</option>
													<option value=5>5月</option>
													<option value=6>6月</option>
													<option value=7>7月</option>
													<option value=8>8月</option>
													<option value=9>9月</option>
													<option value=10>10月</option>
													<option value=11>11月</option>
													<option value=12>12月</option>
												</select>
											</div>
											<div class="col-md-4 operatingTime text-center">
												<label for="sumTime"
													class="col-md-4 col-form-label text-md-left">稼働時間</label>
												<h4 id="sumTime" class="d-inline-block"></h4>
												<input id="sumTimeInput" type="text" size="6" >
											</div>
											<div class="col-md-4 remainingTime text-center">
												<label for="remainingTime"
													class="col-md-4 col-form-label text-md-left">残業時間</label>
												<h4 id="remainingTime" class="d-inline-block"></h4>
											</div>
										</div>
									</form>
								</div>
								<div class="mt-3">
									<div class="col-md-4 float-left text-center">
									</div>
									<div class="form-group row col-md-8 subject2">
										<div class="col-md-6 operatingTime text-center">
											<label for="operatingTime"
												class="col-md-4 col-form-label text-md-left">現場稼働</label>
											<h4 id="operatingTime" class="d-inline-block"></h4>
											<input id="operatingTimeInput" type="text" size="6" >
										</div>
										<div class="col-md-6 operatingTime text-center">
											<label for="inTime"
												class="col-md-4 col-form-label text-md-left">自社稼働</label>
											<h4 id="inTime" class="d-inline-block"></h4>
											<input id="inTimeInput" type="text" size="6" >
										</div>
									</div>
								</div>
								<div class="mt-3">
									<div class="form-group row col-md-12 subject2">
										<div class="col-md-3 operatingDays text-center">
											<label for="operatingDays"
												class="col-md-4 col-form-label text-md-left">日数</label>
											<h4 id="operatingDays" class="d-inline-block"></h4>
											<input id="operatingDaysInput" type="text" size="6" >
										</div>
										<div class="col-md-3 absenceDays text-center">
											<label for="absenceDays"
												class="col-md-4 col-form-label text-md-left">欠勤</label>
											<h4 id="absenceDays" class="d-inline-block"></h4>
											<input id="absenceDaysInput" type="text" size="6" >
										</div>
										<div class="col-md-3 substituteDays text-center">
											<label for="substituteDays"
												class="col-md-4 col-form-label text-md-left">代休</label>
											<h4 id="substituteDays" class="d-inline-block"></h4>
											<input id="substituteDaysInput" type="text" size="6" >
										</div>
										<div class="col-md-3 paidDays text-center">
											<label for="paidDays"
												class="col-md-4 col-form-label text-md-left">有給</label>
											<h4 id="paidDays" class="d-inline-block"></h4>
											<input id="paidDaysInput" type="text" size="6" >
										</div>
									</div>
								</div>

								<div class="clearfix mt-3">
									<div class="col-md-2 float-left text-center defaultTime">
										<h4>基本 時間</h4>
									</div>
									<div class="col-md-2 float-left text-center defaultStart">
										<select name=defaultStart id="defaultStart" class="p-1 ml-3">
											<option value=08:00>08:00</option>
											<option value=08:30>08:30</option>
											<option value=09:00 selected>09:00</option>
											<option value=09:30>09:30</option>
											<option value=10:00>10:00</option>
											<option value=10:30>10:30</option>
											<option value=11:00>11:00</option>
										</select>
									</div>
									<div class="col-md-2 float-left text-center defaultFinish">
										<select name=defaultFinish id="defaultFinish" class="p-1 ml-3">
											<option value=17:00>17:00</option>
											<option value=17:30>17:30</option>
											<option value=18:00 selected>18:00</option>
											<option value=18:30>18:30</option>
											<option value=19:00>19:00</option>
											<option value=19:30>19:30</option>
											<option value=20:00>20:00</option>
										</select>
									</div>
									<div class="col-md-2 float-left text-center set">
										<button type="submit" class="btn btn-light ml-3"
												id="defaultSet">セット</button>
									</div>
									<div class="col-md-2 float-left text-center edit">
										<button type="submit" class="btn btn-light ml-3"
												id="edit">編集</button>
									</div>
									<div class="col-md-2 float-left text-center data">
										<button type="button" class="btn btn-light ml-3" data-toggle="modal" data-target="#messageModal" id="data">データ</button>
									</div>
									<!-- Modal -->
									<div class="modal fade" id="messageModal" tabindex="-1"
										role="dialog" aria-labelledby="messageModalLabel"
										aria-hidden="true">
										<div class="modal-dialog modal-dialog-centered"
											role="document">
											<div class="modal-content" id="modalPDF">
												<div class="modal-header">
													<h5 class="modal-title" id="messageModalLabel">データ</h5>
													<button type="button" class="close" data-dismiss="modal" aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
												</div>
												<div class="modal-body">
													<embed id="pdf" src="" type="application/pdf" width="100%" height="85%">
													<form action="Upload" method="post" enctype="multipart/form-data">
														<input type="file" name="file" id="file">
														<input type="text" name="monthText" id="monthText">
														<button type="submit" class="btn btn-primary ml-3" id="upload">アップロード</button>
													</form>
												</div>
											</div>
										</div>
									</div>
									<!-- Modal -->
									<div class="modal fade" id="inModal" tabindex="-1"
										role="dialog" aria-labelledby="inModalLabel"
										aria-hidden="true">
										<div class="modal-dialog modal-dialog-centered"
											role="document">
											<div class="modal-content" id="modalPDF">
												<div class="modal-header">
													<h5 class="modal-title" id="inModalLabel">自社勤務状況</h5>
													<button type="button" class="close" data-dismiss="modal" aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
												</div>
												<div class="modal-body">
													<div id="uploadDiv">
														<div class="mt-3 text-center"><h5>対象日時</h5></div>
														<div class="mt-5 text-center">
															<select name=inDay id="inDaySelect" class="p-1">
																<option value=1>1日</option>
																<option value=2>2日</option>
																<option value=3>3日</option>
																<option value=4>4日</option>
																<option value=5>5日</option>
																<option value=6>6日</option>
																<option value=7>7日</option>
																<option value=8>8日</option>
																<option value=9>9日</option>
																<option value=10>10日</option>
																<option value=11>11日</option>
																<option value=12>12日</option>
																<option value=13>13日</option>
																<option value=14>14日</option>
																<option value=15>15日</option>
																<option value=16>16日</option>
																<option value=17>17日</option>
																<option value=18>18日</option>
																<option value=19>19日</option>
																<option value=20>20日</option>
																<option value=21>21日</option>
																<option value=22>22日</option>
																<option value=23>23日</option>
																<option value=24>24日</option>
																<option value=25>25日</option>
																<option value=26>26日</option>
																<option value=27>27日</option>
																<option value=28>28日</option>
																<option value=29>29日</option>
																<option value=30>30日</option>
																<option value=31>31日</option>
															</select>
															<select name="inStart" id="inStartSelect" class="p-1 ml-5">
																<option value=13:00>13:00</option>
																<option value=13:30>13:30</option>
																<option value=14:00>14:00</option>
																<option value=14:30>14:30</option>
																<option value=15:00 selected>15:00</option>
																<option value=15:30>15:30</option>
																<option value=16:00>16:00</option>
																<option value=16:30>16:30</option>
																<option value=17:00>17:00</option>
																<option value=16:30>17:30</option>
																<option value=18:00>18:00</option>
																<option value=18:30>18:30</option>
																<option value=19:00>19:00</option>
																<option value=19:30>19:30</option>
																<option value=20:00>20:00</option>
																<option value=20:30>20:30</option>
																<option value=21:00>21:00</option>
																<option value=21:30>21:30</option>
																<option value=22:00>22:00</option>
															</select>
															<select name="inFinish" id="inFinishSelect" class="p-1 ml-5">
																<option value=17:00>17:00</option>
																<option value=17:30>17:30</option>
																<option value=18:00 selected>18:00</option>
																<option value=18:30>18:30</option>
																<option value=19:00>19:00</option>
																<option value=19:30>19:30</option>
																<option value=20:00>20:00</option>
																<option value=20:30>20:30</option>
																<option value=21:00>21:00</option>
																<option value=21:30>21:30</option>
																<option value=22:00>22:00</option>
																<option value=22:30>22:30</option>
																<option value=23:00>23:00</option>
															</select>
															<input class="ml-5" id="inRemarksSelect">
														</div>
														<div class="text-right mt-5">
															<button type="submit" class="btn btn-primary mr-5" id="entry">登録</button>
														</div>
													</div>
													<div class="mt-4" id="appendIn">
													</div>
												</div>
											</div>
										</div>
									</div>

								</div>

								<div class="mt-4">
									<form method="post" id="form2">
										<div class="clearfix" id="subject">
											<div class="col-md-2 float-left text-center">
												<h4>日付</h4>
											</div>
											<div class="col-md-2 float-left text-center">
												<h4>区分</h4>
											</div>
											<div class="col-md-2 float-left text-center">
												<h4>開始時間</h4>
											</div>
											<div class="col-md-2 float-left text-center">
												<h4>終了時間</h4>
											</div>
											<div class="col-md-2 float-left text-center">
												<h4>休憩時間</h4>
											</div>
											<div class="col-md-2 float-left text-center">
												<h4>備考</h4>
											</div>
										</div>
									</form>
									<div class="form-group row mt-5 mb-0 text-right">
										<div class="col-md-12">
											<button type="submit" class="btn btn-light ml-3"
												id="update">更新</button>
											<button type="submit" class="btn btn-primary ml-3"
												id="submit">提出</button>
										</div>
									</div>
								</div>
								<div class="mt-4 col-md-11 mr-auto ml-auto">
									<h4>管理者メモ</h4>
									<textarea id="memo" class="form-control" name="memo"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<footer class="footer">
				<div class="container-fluid">
					<nav class="float-left">
						<ul>
							<li><a href="https://www.creative-tim.com"> Creative Tim
							</a></li>
							<li><a href="https://creative-tim.com/presentation">
									About Us </a></li>
							<li><a href="http://blog.creative-tim.com"> Blog </a></li>
							<li><a href="https://www.creative-tim.com/license">
									Licenses </a></li>
						</ul>
					</nav>
					<div class="copyright float-right">
						&copy;
						<script>
							document.write(new Date().getFullYear())
						</script>
						, made with by <a
							href="https://www.creative-tim.com" target="_blank">Creative
							Tim</a> for a better web.
					</div>
				</div>
			</footer>
		</div>
	</div>
</body>
<!--   Core JS Files   -->
<script src="common/JS/core/jquery.min.js"></script>
<script src="common/JS/core/popper.min.js"></script>
<script src="common/JS/core/bootstrap-material-design.min.js"></script>
<script src="common/JS/plugins/perfect-scrollbar.jquery.min.js"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="common/JS/material-dashboard.js?v=2.1.2" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript" src="common/JS/jquery.blockUI.js"></script>
<script type="text/javascript" src="common/JS/attendance.js"></script>
</html>
<%
	}
%>