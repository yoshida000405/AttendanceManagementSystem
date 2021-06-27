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
<title>掲示板</title>
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
					<div class="col-md-8">
						<div class="card">
							<div class="card-header border">
							<div class="form-group row text-center">
								<div class="col-md-12 text-center">
									<h5 id="subject1B">現在作成中</h5>
								</div>
							</div>
							</div>

							<div class="card-body">
								<div class="form-group row text-center" id="append">
									<div class="m-auto" id="content1"></div>
									<div class="col-md-12 text-center">
										<textarea id="content" cols="70" rows="20">設定画面は現在作成中です。</textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix mt-5 mb-5">
							<div class="col-4 text-left float-left"><h5 id="forward">前へ</h5></div>
							<div class="col-4 text-center float-left"><h5 id="all">一覧</h5></div>
							<div class="col-4 text-right float-left"><h5 id="next">次へ</h5></div>
						</div>
						<div class="card mt-5">
							<div class="card-header border text-left">
								最近の投稿
							</div>
							<div class="card-body">
								<div class="form-group row">
									<h5 id="subject2B">直近1</h5>
								</div>

								<div class="form-group row">
									<h5 id="subject3B">直近2</h5>
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
<script src="common/JS/material-dashboard.js?v=2.1.2"
	type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</html>
<%
	}
%>