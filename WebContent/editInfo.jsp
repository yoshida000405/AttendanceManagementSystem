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
<title>登録者情報編集</title>
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
								<div class="col-6 float-left">登録者情報編集</div>
								<div class="col-6 float-left text-right">
									<select id="member" class="p-1"></select>
								</div>
							</div>

							<div class="card-body">
								<div class="mt-4">
									<form id="form2" method="post">
										<div class="clearfix mt-5">
											<label for="lastName"
												class="col-md-4 col-form-label float-left text-md-center">姓</label>
											<input id="lastName" type="text"
												class="col-md-7 form-control float-left" name="lastName"
												required>
										</div>
										<div class="clearfix mt-5">
											<label for="firstName"
												class="col-md-4 col-form-label float-left text-md-center">名</label>
											<input id="firstName" type="text"
												class="col-md-7 form-control float-left" name="firstName"
												required>
										</div>
										<div class="clearfix mt-5">
											<label for="lastNameKana"
												class="col-md-4 col-form-label float-left text-md-center">姓（ふりがな）</label>
											<input id="lastNameKana" type="text"
												class="col-md-7 form-control float-left" name="lastNameKana"
												required>
										</div>
										<div class="clearfix mt-5">
											<label for="firstNameKana"
												class="col-md-4 col-form-label float-left text-md-center">名（ふりがな）</label>
											<input id="firstNameKana" type="text"
												class="col-md-7 form-control float-left"
												name="firstNameKana" required>
										</div>
										<div class="clearfix mt-5">
											<label for="lastNameEnglish"
												class="col-md-4 col-form-label float-left text-md-center">姓（アルファベット）</label>
											<input id="lastNameEnglish" type="text"
												class="col-md-7 form-control float-left" name="lastNameEnglish"
												required>
										</div>
										<div class="clearfix mt-5">
											<label for="firstNameEnglish"
												class="col-md-4 col-form-label float-left text-md-center">名（アルファベット）</label>
											<input id="firstNameEnglish" type="text"
												class="col-md-7 form-control float-left"
												name="firstNameEnglish" required>
										</div>
										<div class="clearfix mt-5">
											<label for="gender"
												class="col-md-4 col-form-label float-left text-md-center">性別</label>
											<select id="gender"
												class="col-md-7 form-control float-left p-1" name="gender"
												required>
												<option value=1>男性</option>
												<option value=2>女性</option>
												<option value=3>その他</option>
											</select>
										</div>
										<div class="clearfix mt-5">
											<label for="hireDate"
												class="col-md-4 col-form-label float-left text-md-center">入社日</label>
											<input id="hireDate" type="text"
												class="col-md-7 form-control float-left" name="hireDate"
												pattern="[0-9]{8}" required>
										</div>
										<div class="clearfix mt-5">
											<label for="mailAddress"
												class="col-md-4 col-form-label float-left text-md-center">メールアドレス</label>
											<input id="mailAddress" type="text"
												class="col-md-7 form-control float-left" name="mailAddress"
												required>
										</div>
										<div class="clearfix mt-5">
											<label for="group"
												class="col-md-4 col-form-label float-left text-md-center">グループ</label>
											<input type="checkbox" id="group1" class="mt-1 mr-2"
												value="html">
											<h5 class="d-inline-block mr-3">現場</h5>
											<input type="checkbox" id="group2" class="mt-1 mr-2"
												value="ruby">
											<h5 class="d-inline-block mr-3">自社</h5>
											<input type="checkbox" id="group3" class="mt-1 mr-2"
												value="php">
											<h5 class="d-inline-block mr-3">リーダー</h5>
										</div>
										<div class="clearfix mt-5">
											<label for="authority"
												class="col-md-4 col-form-label float-left text-md-center">権限</label>
											<select id="authority"
												class="col-md-7 form-control float-left p-1"
												name="authority" required>
												<option value=1>通常ユーザー</option>
												<option value=2>管理者ユーザー</option>
											</select>
										</div>
										<div class="clearfix mt-5">
											<label for="station"
												class="col-md-4 col-form-label float-left text-md-center">最寄駅</label>
											<input id="station" type="text"
												class="col-md-7 form-control float-left" name="station"
												required>
										</div>
									</form>
									<div class="form-group row mt-5 mb-0 text-right">
										<div class="col-md-11">
											<button type="submit" class="btn btn-primary" id="delete">削除</button>
										</div>
										<div class="col-md-1">
											<button type="submit" class="btn btn-primary" id="update">更新</button>
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
<script type="text/javascript" src="common/JS/jquery.blockUI.js"></script>
<script type="text/javascript" src="common/JS/editInfo.js"></script>
</html>
<%
	}
%>