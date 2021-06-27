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
<title>レンタル画面</title>
<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no'
	name='viewport' />
<!--     Fonts and icons     -->
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
<!-- CSS Files -->
<link href="common/css/material-dashboard.css" rel="stylesheet" />
<link rel="stylesheet" href="common/css/app.css">
<link rel="stylesheet" href="common/css/styles.css">
<link rel="stylesheet" href="common/css/packages/core/main.css">
<link rel="stylesheet" href="common/css/packages/daygrid/main.css">
<link rel="stylesheet" href="common/css/packages/timegrid/main.css">
<link rel="stylesheet" href="common/css/packages/list/main.css">
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
									href="attendance.jsp"> <i class="material-icons">schedule</i>
										<p>勤怠入力</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="demandNav"><a
									href="demand.jsp"> <i class="material-icons">content_paste</i>
										<p>請求書入力</p>
								</a></li>
								<li class="nav-item w-6 text-center" id="skillNav"><a
									href="skill.jsp"> <i class="material-icons">library_books</i>
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
									href="editInfo.jsp"> <i class="material-icons">person</i>
										<p>登録情報変更</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="registerNav"><a
									href="register.jsp"> <i class="material-icons">group_add</i>
										<p>新規登録</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="mailNav"><a
									href="mail.jsp"> <i class="material-icons">mail</i>
										<p>メール</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="managedNav"><a
									href="managed.jsp"> <i class="material-icons">dashboard</i>
										<p>管理機能</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="settingNav"><a
									href="setting.jsp"> <i class="material-icons">miscellaneous_services</i>
										<p>設定</p>
								</a></li>
								<li class="nav-item mt-4 w-7 text-center" id="logoutNav">
									<form name="form" method="post" action="LogoutChk">
										<a href="#" onClick="document.form.submit();"><i
											class="material-icons">exit_to_app</i>
											<p>ログアウト</p></a>
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
			<div class="container mb-5">
				<div class="row justify-content-center">
					<div class="col-md-12">
						<div class="text-left">
							<button type="submit" class="btn btn-light ml-3" id=equipment>機材一覧</button>
						</div>
					</div>
					<div class="col-md-12 mt-3">
						<div id="calendar"></div>
					</div>
					<div id="append"></div>
					<!-- Modal -->
					<div class="modal fade" id="messageModal1" tabindex="-1"
						role="dialog" aria-labelledby="messageModal1Label"
						style="display: none;" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="messageModal1Label">レンタル予約</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="overflow-auto" style="width: 100%; height: 500px;">
										<div class="d-none">
											<input id="year" type="text" class="form-control" name="year">
										</div>
										<div class="col-12 mt-5 text-center">
											<select id="equipmentSelect" class="p-1">
											</select>
										</div>
										<div class="col-12 mt-5 text-center">
											<img width="80%" height="80%" src="" id="imgR" alt="準備中"
												class="equipmentIMG" border="0" />
										</div>
										<div id="type1" class="text-center mt-3 clearfix">
											<div class="col-md-5 mt-4 text-center float-left">
												<select name=monthSelectS id="monthSelectS" class="p-1">
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
												<h5 class="d-inline-block">/</h5>
												<select name="daySelectS" id="daySelectS" class="p-1">
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
											</div>
											<div class="col-md-2 mt-4 text-center float-left">〜</div>
											<div class="col-md-5 mt-4 text-center float-left">
												<select name=monthSelectE id="monthSelectE" class="p-1">
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
												<h5 class="d-inline-block">/</h5>
												<select name="daySelectE" id="daySelectE" class="p-1">
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
											</div>
										</div>
										<div class="col-12 mt-5 text-right">
											<button type="submit" class="btn btn-primary ml-3"
												id="request">予約</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<footer class="footer mt-5">
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
						, made with by <a href="https://www.creative-tim.com"
							target="_blank">Creative Tim</a> for a better web.
					</div>
				</div>
			</footer>
		</div>
	</div>
</body>
<!--   Core JS Files   -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="common/JS/core/jquery.min.js"></script>
<script src="common/JS/core/popper.min.js"></script>
<script src="common/JS/core/bootstrap-material-design.min.js"></script>
<script src="common/JS/plugins/perfect-scrollbar.jquery.min.js"></script>
<script src="common/JS/bootstrap.js"></script>
<script src="common/JS/packages/core/main.js"></script>
<script src="common/JS/packages/interaction/main.js"></script>
<script src="common/JS/packages/daygrid/main.js"></script>
<script src="common/JS/packages/timegrid/main.js"></script>
<script src="common/JS/packages/list/main.js"></script>
<script src="common/JS/packages/core/locales-all.js"></script>
<script src="common/JS/fullcalendar.js"></script>
<script src="common/JS/event-control.js"></script>
<script src="common/JS/ajax-setup.js"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="common/JS/material-dashboard.js?v=2.1.2" type="text/javascript"></script>
<script type="text/javascript" src="common/JS/jquery.blockUI.js"></script>
<script type="text/javascript" src="common/JS/rental.js"></script>
</html>
<%
	}
%>