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
<title>スキルシート管理</title>
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
								<div class="col-4 float-left">スキルシート</div>
								<div id="submitFlag" class="col-4 float-left text-center"></div>
								<div id="editUser" class="col-4 float-left text-right"></div>
							</div>

							<div class="card-body">
								<div>
									<form id="form1" method="post">
										<div class="form-group row">
											<div class="col-sm-6 month">
												<label for="month"
													class="col-md-4 col-form-label text-md-right monthLabel">対象月</label> <select
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
											<div class="col-sm-6 text-center">
												<button type="button" class="btn btn-primary" id="reset">リセット</button>
											</div>
										</div>
									</form>
								</div>
								<div class="mt-4">
									<form id="form2" method="post">
										<div class="clearfix mt-5">
											<label for="projectName"
												class="col-md-4 col-form-label float-left text-md-center">プロジェクト名</label>
											<input id="projectName" type="text"
												class="col-md-7 form-control float-left" name="projectName"
												disabled>
										</div>
										<div class="clearfix mt-5">
											<label for="bussinessOverview"
												class="col-md-4 col-form-label float-left text-md-center">業務概要</label>
											<textarea id="bussinessOverview"
												class="col-md-7 form-control float-left"
												name="bussinessOverview" rows="7" disabled></textarea>
										</div>
										<div class="clearfix mt-5">
											<label for="bussinessOverviewEdit"
												class="col-md-4 col-form-label float-left text-md-center"></label>
											<textarea id="bussinessOverviewEdit"
												class="col-md-7 form-control float-left"
												name="bussinessOverviewEdit" rows="4" required></textarea>
										</div>
										<div class="clearfix">
											<div class="col-md-6 float-left text-center mt-5">
												<label for="role"
													class="col-md-4 col-form-label text-md-left">役割</label> <select
													name=role id="role" class="p-1">
													<option value=1>役割１</option>
													<option value=2>役割２</option>
													<option value=3>役割３</option>
												</select>
											</div>
											<div class="col-md-6 float-left text-center mt-5">
												<label for="scale"
													class="col-md-4 col-form-label float-left text-md-center">規模</label>
												<input id="scale" type="text"
													class="col-md-7 form-control float-left" name="scale"
													required>
											</div>
										</div>
										<div class="clearfix mt-5">
											<label for="serverOS"
												class="col-md-4 col-form-label float-left text-md-center">サーバ/OS</label>
											<input id="serverOS" type="text"
												class="col-md-7 form-control float-left" name="serverOS"
												disabled>
										</div>
										<div class="clearfix mt-5">
											<label for="serverOSEdit"
												class="col-md-4 col-form-label float-left text-md-center"></label>
											<input id="serverOSEdit" type="text"
												class="col-md-7 form-control float-left" name="serverOSEdit">
										</div>
										<div class="clearfix mt-5">
											<label for="DB"
												class="col-md-4 col-form-label float-left text-md-center">DB</label>
											<input id="DB" type="text"
												class="col-md-7 form-control float-left" name="DB" disabled>
										</div>
										<div class="clearfix mt-5">
											<label for="DBEdit"
												class="col-md-4 col-form-label float-left text-md-center"></label>
											<input id="DBEdit" type="text"
												class="col-md-7 form-control float-left" name="DBEdit">
										</div>
										<div class="clearfix mt-5">
											<label for="tool"
												class="col-md-4 col-form-label float-left text-md-center">ツール類</label>
											<input id="tool" type="text"
												class="col-md-7 form-control float-left" name="tool"
												disabled>
										</div>
										<div class="clearfix mt-5">
											<label for="toolEdit"
												class="col-md-4 col-form-label float-left text-md-center"></label>
											<input id="toolEdit" type="text"
												class="col-md-7 form-control float-left" name="toolEdit">
										</div>
										<div class="clearfix mt-5">
											<label for="useLanguage"
												class="col-md-4 col-form-label float-left text-md-center">使用言語</label>
											<input id="useLanguage" type="text"
												class="col-md-7 form-control float-left" name="useLanguage"
												disabled>
										</div>
										<div class="clearfix mt-5">
											<label for="useLanguageEdit"
												class="col-md-4 col-form-label float-left text-md-center"></label>
											<input id="useLanguageEdit" type="text"
												class="col-md-7 form-control float-left"
												name="useLanguageEdit">
										</div>
										<div class="clearfix mt-5">
											<label for="other"
												class="col-md-4 col-form-label float-left text-md-center">その他</label>
											<input id="other" type="text"
												class="col-md-7 form-control float-left" name="other"
												disabled>
										</div>
										<div class="clearfix mt-5">
											<label for="otherEdit"
												class="col-md-4 col-form-label float-left text-md-center"></label>
											<input id="otherEdit" type="text"
												class="col-md-7 form-control float-left" name="otherEdit">
										</div>
									</form>
									<div class="form-group row mt-5 mb-0 text-right">
										<div class="col-md-12">
											<button type="submit" class="btn btn-primary ml-3" id="update">提出</button>
										</div>
									</div>
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
<script type="text/javascript" src="common/JS/skill.js"></script>
</html>
<%
	}
%>