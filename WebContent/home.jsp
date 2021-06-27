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
	href="../assets/img/apple-icon.png">
<link rel="icon" type="image/png" href="../assets/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>ホーム</title>
<meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no'
	name='viewport' />
<!--     Fonts and icons     -->
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
<!-- CSS Files -->
<link href="common/css/material-dashboard.css?v=2.1.2" rel="stylesheet" />
<link href="common/css/styles.css" rel="stylesheet" />
</head>

<body class="">
	<div class="wrapper ">
		<div class="sidebar" data-color="purple" data-background-color="white"
			data-image="common/img/sidebar-1.jpg">
			<div class="logo">
				<a href="home.jsp" class="simple-text logo-normal"> Rigare Japan
				</a>
			</div>
			<div class="sidebar-wrapper">
				<ul class="nav">
					<li class="nav-item dropdown" id="notificationsNav"><a
						class="nav-link" href="http://example.com"
						id="navbarDropdownMenuLink" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> <i
							class="material-icons">notifications</i> <span
							class="notification" id="notificationNumSub"></span>
					</a>
						<div class="dropdown-menu dropdown-menu-right"
							aria-labelledby="navbarDropdownMenuLink" id="notificationSub">
						</div></li>
					<li class="nav-item active  " id="homeNav"><a class="nav-link"
						href="home.jsp"> <i class="material-icons">home</i>
							<p>ホーム</p>
					</a></li>
					<li class="nav-item " id="attendanceNav"><a class="nav-link"
						href="attendance.jsp"> <i class="material-icons">schedule</i>
							<p>勤怠入力</p>
					</a></li>
					<li class="nav-item " id="demandNav"><a class="nav-link"
						href="demand.jsp"> <i class="material-icons">content_paste</i>
							<p>請求書入力</p>
					</a></li>
					<li class="nav-item " id="skillNav"><a class="nav-link"
						href="skill.jsp"> <i class="material-icons">library_books</i>
							<p>スキルシート入力</p>
					</a></li>
					<li class="nav-item " id="ptoNav"><a class="nav-link"
						href="pto.jsp"> <i class="material-icons">card_travel</i>
							<p>有給申請</p>
					</a></li>
					<li class="nav-item " id="boardNav"><a class="nav-link"
						href="board.jsp"> <i class="material-icons">book_online</i>
							<p>掲示板</p>
					</a></li>
					<li class="nav-item " id="rentalNav"><a class="nav-link"
						href="rental.jsp"> <i class="material-icons">important_devices</i>
							<p>レンタル</p>
					</a></li>
					<li class="nav-item " id="editInfoNav"><a class="nav-link"
						href="editInfo.jsp"> <i class="material-icons">person</i>
							<p>登録情報変更</p>
					</a></li>
					<li class="nav-item " id="registerNav"><a class="nav-link"
						href="register.jsp"> <i class="material-icons">group_add</i>
							<p>新規登録</p>
					</a></li>
					<li class="nav-item " id="mailNav"><a class="nav-link"
						href="mail.jsp"> <i class="material-icons">mail</i>
							<p>メール</p>
					</a></li>
					<li class="nav-item " id="managedNav"><a class="nav-link"
						href="managed.jsp"> <i class="material-icons">dashboard</i>
							<p>管理機能</p>
					</a></li>
					<li class="nav-item " id="settingNav"><a class="nav-link"
						href="setting.jsp"> <i class="material-icons">miscellaneous_services</i>
							<p>設定</p>
					</a></li>
					<li class="nav-item " id="logoutNav">
						<a class="nav-link" href="#" onClick="document.form.submit();"><i
							class="material-icons">exit_to_app</i><p>ログアウト</p></a>
					</li>
				</ul>
			</div>
		</div>
		<form name="form" method="post" action="LogoutChk">
		</form>
		<div class="main-panel">
			<!-- Navbar -->
			<nav
				class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
				<div class="container-fluid">
					<div class="navbar-wrapper"></div>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						aria-controls="navigation-index" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="sr-only">Toggle navigation</span> <span
							class="navbar-toggler-icon icon-bar"></span> <span
							class="navbar-toggler-icon icon-bar"></span> <span
							class="navbar-toggler-icon icon-bar"></span>
					</button>
					<div class="collapse navbar-collapse justify-content-end">
						<form class="navbar-form">
							<div class="input-group no-border">
								<input type="text" value="" class="form-control"
									placeholder="Search...">
								<button type="submit"
									class="btn btn-white btn-round btn-just-icon">
									<i class="material-icons">search</i>
									<div class="ripple-container"></div>
								</button>
							</div>
						</form>
						<ul class="navbar-nav">
							<li class="nav-item"><a class="nav-link" href="javascript:;">
									<i class="material-icons">dashboard</i>
									<p class="d-lg-none d-md-block">Stats</p>
							</a></li>
							<li class="nav-item dropdown"><a class="nav-link"
								href="http://example.com" id="navbarDropdownMenuLink"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false"> <i class="material-icons">notifications</i>
									<span class="notification" id="notificationNum"></span>
									<p class="d-lg-none d-md-block">Some Actions</p>
							</a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdownMenuLink" id="notification">
								</div></li>
							<li class="nav-item dropdown"><a class="nav-link"
								href="javascript:;" id="navbarDropdownProfile"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false"> <i class="material-icons">person</i>
									<p class="d-lg-none d-md-block">Account</p>
							</a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdownProfile">
									<a class="dropdown-item" href="#">プロフィール</a> <a
										class="dropdown-item" href="#">設定</a>
									<div class="dropdown-divider"></div>
									<form name="form1" method="post" action="LogoutChk">
										<a class="dropdown-item" href="#"
											onClick="document.form1.submit();"><p>ログアウト</p></a>
									</form>
								</div></li>
						</ul>
					</div>
				</div>
			</nav>
			<!-- End Navbar -->
			<div class="content">
				<div class="mt-3 text-center">
					<h4 id="welcome"></h4>
				</div>
				<div class="container-fluid mt-5">
					<div class="row">
						<div class="col-md-4">
							<div class="card card-chart">
								<div class="card-header card-header-success">
									<div class="ct-chart">
										<a href="attendance.jsp"><h5 id="operatingTime"></h5></a>
									</div>
								</div>
								<div class="card-body">
									<h4 class="card-title">今月の稼働時間</h4>
									<p class="card-category"></p>
								</div>
								<div class="card-footer">
									<div class="stats">
										<i class="material-icons">access_time</i>
										<h5>updated</h5><h5 id="attendanceUPD" class="d-inline-block"></h5><h5 id="attendanceMsg" class="d-inline-block"></h5><h5>ago</h5>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="card card-chart">
								<div class="card-header card-header-warning">
									<div class="ct-chart" id="boardCard"></div>
								</div>
								<div class="card-body">
									<h4 class="card-title">新着トピック</h4>
									<p class="card-category"></p>
								</div>
								<div class="card-footer">
									<div class="stats">
										<i class="material-icons">access_time</i>
										<h5>updated</h5><h5 id="boardUPD" class="d-inline-block"></h5><h5 id="boardMsg" class="d-inline-block"></h5><h5>ago</h5>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="card card-chart">
								<div class="card-header card-header-danger">
									<div class="ct-chart" id="PTO">
									</div>
								</div>
								<div class="card-body">
									<h4 class="card-title">有給情報</h4>
									<p class="card-category"></p>
								</div>
								<div class="card-footer">
									<div class="stats">
										<i class="material-icons">access_time</i>
										<h5>updated</h5><h5 id="ptoUPD" class="d-inline-block"></h5><h5 id="ptoMsg" class="d-inline-block"></h5><h5>ago</h5>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="mt-3">
						<div class="col-md-2 float-left text-right">
							<button type="button" class="btn btn-light ml-3"><a href="http://52.69.198.103:8080/AttendanceManagementSystem/resources/pc-manual.pdf" target="_blank" rel="noopener noreferrer">マニュアル</a></button>
						</div>
						<div class="col-md-2 float-left text-right">
							<button type="button" class="btn btn-light ml-3"><a href="http://52.69.198.103:8080/AttendanceManagementSystem/resources/calendar.pdf" target="_blank" rel="noopener noreferrer">カレンダー</a></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!--   Core JS Files   -->
<script src="common/JS/core/jquery.min.js"></script>
<script src="common/JS/core/popper.min.js"></script>
<script src="common/JS/core/bootstrap-material-design.min.js"></script>
<script src="common/JS/plugins/perfect-scrollbar.jquery.min.js"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="common/JS/material-dashboard.js?v=2.1.2"
	type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript" src="common/JS/home.js"></script>
</html>
<%
	}
%>