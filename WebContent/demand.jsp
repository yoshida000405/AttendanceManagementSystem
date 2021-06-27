<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>
<!DOCTYPE html>
<html lang="jp">

<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="../assets/img/apple-icon.png">
<link rel="icon" type="image/png" href="../assets/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>請求書管理</title>
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
					<div class="col-md-12">
						<div class="card">
							<div class="card-header border">
								<div class="col-4 float-left">請求書入力</div>
								<div id="submitFlag" class="col-4 float-left text-center"></div>
								<div id="editUser" class="col-4 float-left text-right"></div>
							</div>

							<div class="card-body">
								<div>
									<form id="form1" method="post">
										<div class="form-group row">
											<div class="col-md-4 ml-5 monthD">
												<h5 class="d-inline-block mr-3 monthLabelD">対象月</h5>
												<select name=”month” id="month" class="p-1 monthSelect">
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
											<div class="col-md-6 text-right lastUpdate">
												<h5 class="d-inline-block mr-3">最終更新日</h5>
												<h5 class="d-inline-block" id="lastUpdate"></h5>
											</div>
											<div class="col-md-1 text-center example">
												<button type="button" class="btn btn-primary"
													data-toggle="modal" data-target="#messageModal">
												区分説明</button>
											</div>
											<!-- Modal -->
											<div class="modal fade" id="messageModal" tabindex="-1"
												role="dialog" aria-labelledby="messageModalLabel"
												aria-hidden="true">
												<div class="modal-dialog modal-dialog-centered"
													role="document">
													<div class="modal-content">
														<div class="modal-header">
															<h5 class="modal-title" id="messageModalLabel">区分説明</h5>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<div class="overflow-auto" style="width:100%;height:500px;">
															<h5>
																<br>・旅費交通費<br>電車・バス・新幹線・タクシー・飛行機 等の利用<br>
																<br> ・消耗品費<br>文房具の購入やコピー料金 等 ・事務用品費
																事務所にて使用するもの(無線ルーター等)<br>
																<br> ・支払手数料<br>銀行の振込手数料や企業ゴミ処理券の購入、証明書発行手数料 等<br>
																<br> ・交際費<br>交際費、接待費、機密費、その他の費用で法人がその得意先、<br>仕入先その他事業に関係ある者等に対する接待、供応、贈答<br>その他これらに対する行為のために支出する費用<br>
																<br> ・会議費<br>ミーティングや技術者面談 等(人数・参加者名を必ず記載)<br>
																<br> ・通信費<br>はがき・切手・宅配便等の配送・レターパック 等<br>
																<br> ・車両費<br>ガソリン・駐車場・高速利用料 等<br>
																<br> ・外注費<br>売上高の原価になる業務委託料<br>
															</h5>
															</div>
														</div>
													</div>
												</div>
											</div>
									</form>
								</div>
								<div class="clearfix mt-4">
									<div class="float-left col-md-12">
										<div class="col-md-2 float-left text-center column1D border-top border-left m-0">
											<h5>旅費交通費</h5>
											<h5 id="sum1"></h5>
										</div>
										<div class="col-md-2 float-left text-center column2D border-top border-left m-0">
											<h5>消耗品費</h5>
											<h5 id="sum2"></h5>
										</div>
										<div class="col-md-2 float-left text-center column3D border-top border-left m-0">
											<h5>会議費</h5>
											<h5 id="sum3"></h5>
										</div>
										<div class="col-md-2 float-left text-center column4D border-top border-left border-right m-0">
											<h5>通信費</h5>
											<h5 id="sum4"></h5>
										</div>
										<div class="col-md-2 float-left text-center column5D  border-top border-left border-bottom">
											<h5>支払手数料</h5>
											<h5 id="sum5"></h5>
										</div>
										<div class="col-md-2 float-left text-center column6D border-top border-left border-bottom">
											<h5>交際費</h5>
											<h5 id="sum6"></h5>
										</div>
										<div class="col-md-2 float-left text-center column7D border-top border-left border-bottom">
											<h5>その他</h5>
											<h5 id="sum7"></h5>
										</div>
										<div class="col-md-2 float-left text-center column8D border">
											<h5>合計</h5>
											<h5 id="sum8"></h5>
										</div>
									</div>
								</div>
								<div class="mt-5">
									<form id="form2" method="post">
										<div class="clearfix mb-3" id="subjectD">
											<div class="col-md-1 float-left text-center">
												<h5>対象日</h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5>区分</h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5>金額</h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5>口数</h5>
											</div>

											<div class="col-md-1 float-left text-center">
												<h5>合計</h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5>支払先</h5>
											</div>
											<div class="col-md-2 float-left text-center">
												<h5>摘要</h5>
											</div>
											<div class="col-md-2 float-left text-center">
												<h5>詳細</h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5>領収書</h5>
											</div>
											<div class="col-md-1 float-left text-center">
												<h5>処理</h5>
											</div>
										</div>

									</form>
									<div class="form-group row mt-5 mb-0 text-right">
										<div class="col-md-12">
											<select class="mr-3" id="multiCheck" class="p-1">
												<option value=1>個別</option>
												<option value=2>一括処理</option>
												<option value=3>一括未処理</option>
											</select>
										</div>
									</div>
									<div class="form-group row mt-5 mb-0 text-right">
										<div class="col-md-12">
											<button type="submit" class="btn btn-light" id="delete">削除</button>
											<button type="submit" class="btn btn-light ml-3" id="append">追加</button>
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
						, made with by <a href="https://www.creative-tim.com"
							target="_blank">Creative Tim</a> for a better web.
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
<script type="text/javascript" src="common/JS/demand.js"></script>
</html>
<%
	}
%>