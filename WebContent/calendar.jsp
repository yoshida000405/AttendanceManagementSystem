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
<title>カレンダー管理</title>
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
								<li class="nav-item col-md-15 text-center" id="homeNav"><a
									class="nav-link" href="home.jsp"> <i class="material-icons">home</i>
										<p>ホーム</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="attendanceNav"><a
									class="nav-link" href="attendance.jsp"> <i
										class="material-icons">schedule</i>
										<p>勤怠入力</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="demandNav"><a
									class="nav-link" href="demand.jsp"> <i
										class="material-icons">content_paste</i>
										<p>請求書入力</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="skillNav"><a
									class="nav-link" href="skill.jsp"> <i
										class="material-icons">library_books</i>
										<p>スキルシート入力</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="editInfoNav"><a
									class="nav-link" href="editInfo.jsp"> <i
										class="material-icons">person</i>
										<p>登録情報変更</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="registerNav"><a
									class="nav-link" href="register.jsp"> <i
										class="material-icons">group_add</i>
										<p>新規登録</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="mailNav"><a
									class="nav-link" href="mail.jsp"> <i class="material-icons">mail</i>
										<p>メール</p>
								</a></li>
								<li class="nav-item col-md-15 text-center" id="managedNav"><a
									class="nav-link" href="managed.jsp"> <i
										class="material-icons">dashboard</i>
										<p>管理機能</p>
								</a></li>
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
								<div class="col-md-6 float-left">勤怠入力画面</div>
								<div id="editUser" class="col-md-6 float-left text-right"></div>
							</div>

							<div class="card-body"></div>
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
						, made with <i class="material-icons">favorite</i> by <a
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
<script type="text/javascript" src="common/JS/calendar.js"></script>
</html>
<%
	}
%>