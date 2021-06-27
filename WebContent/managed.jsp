<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") == null || !session.getAttribute("loginUserAuthority").toString().contentEquals("2")) {
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
<title>全体管理</title>
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
					<div class="col-md-11">
						<div class="card">
							<div class="card-header border">
								<div class="col-md-6 float-left">全体管理</div>
								<div id="editUser" class="col-md-6 float-left text-right"></div>
							</div>

							<div class="card-body">
								<div>
									<form id="form1" method="post">
										<div class="form-group row">
											<div class="col-md-6 float-left type">
												<h5 class="d-inline-block ml-3 mr-3">種類</h5>
												<select name="type" id="type" class="p-1">
													<option value=1>勤務表</option>
													<option value=2>請求書</option>
													<option value=3>スキルシート</option>
													<option value=4>社員情報</option>
												</select>
											</div>
											<div class="col-md-6 float-left text-left monthM">
												<h5 class="d-inline-block mr-3">対象月</h5>
												<select name=”month” id="month" class="p-1">
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
													<option value=13>年間</option>
												</select>
											</div>
										</div>
									</form>
								</div>
								<div class="clearfix mt-5">
									<div class="float-left col-md-12">
										<div class="col-md-4 float-left text-left member">
											<h5 class="d-inline-block mr-3">対象メンバー</h5>
											<select name=member id="member" class="p-1">
											</select>
										</div>
										<div class="col-md-4 float-left text-left search">
											<h5 class="d-inline-block mr-3">検索</h5>
											<input class="d-inline-block" id="search" type="text"
												class="form-control" name="search">
										</div>
										<div class="col-md-4 float-left text-right outputAll">
											<h5 class="d-inline-block" id="check"></h5>
											<button type="submit" class="btn btn-light ml-4"" id="checkAll">一括処理</button>
											<button type="submit" class="btn btn-light ml-4" id="outputAll">一括出力</button>
										</div>
									</div>
								</div>
								<div class="mt-5">
									<form id="form2" method="post">
										<div class="clearfix mb-3 " id="subject">
											<div class="col-md-2 float-left text-center">
												<h5 id="column1"></h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5 id="column2"></h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5 id="column3"></h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5 id="column4"></h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5 id="column5"></h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5 id="column6"></h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5 id="column7"></h5>
											</div>
											<div id="column8sub" class="col-md-1 float-left text-center">
												<h5 id="column8"></h5>
											</div>
											<div id="column9sub" class="col-md-1 float-left text-center">
												<h5 id="column9"></h5>
											</div>
											<div  id="column10sub" class="col-md-1 float-left text-center">
												<h5 id="column10"></h5>
											</div>
											<div id="column11sub" class="col-md-1 float-left text-center">
												<h5 id="column11"></h5>
											</div>
										</div>
									</form>
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
<script src="common/JS/material-dashboard.js?v=2.1.2"
	type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript" src="common/JS/managed.js"></script>
</html>
<%
	}
%>