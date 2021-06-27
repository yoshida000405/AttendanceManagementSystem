<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("key") == null) {
		response.sendRedirect("password_change_request.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>パスワード変更画面</title>
<link rel="stylesheet" href="common/css/bootstrap.css">
<link rel="stylesheet" href="common/css/app.css">
<link rel="stylesheet" href="common/css/styles.css">

<script type="text/javascript" src="common/JS/app.js"></script>
<script type="text/javascript" src="common/JS/jquery-3.5.0.js"></script>
<script type="text/javascript" src="common/JS/bootstrap.js"></script>
</head>
<body>
	<div id="app">
		<nav class="navbar navbar-expand-sm navbar-light bg-white shadow-sm">
			<a class="navbar-brand" href="home.jsp">RigareJapan</a>
			<button class="navbar-toggler" id="right_nav" type="button"
				data-toggle="collapse" data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="{{ __('Toggle navigation') }}">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<!-- Left Side Of Navbar -->
				<ul class="navbar-nav mr-auto">
				</ul>
				<%
					if (session.getAttribute("loginUserId") != null) {
				%>
				<!-- Right Side Of Navbar -->
				<ul class="navbar-nav ml-auto">
					<!-- Authentication Links -->
					<li class="nav-item">
						<form name="form1" method="post" action="LogoutChk">
                  			<a href="#" onClick="document.form1.submit();">ログアウト</a>
                  		</form>
					</li>
				</ul>
				<%
					}
				%>
			</div>
		</nav>
	</div>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card">
					<div class="card-header">パスワード変更</div>

					<div class="card-body">
						<form>
							<div class="form-group row">
								<label for="userId"
									class="col-md-4 col-form-label text-md-right">ID</label>

								<div class="col-md-6">
									<input id="userId" type="text" class="form-control"
										name="userId" required>
								</div>
							</div>

							<div class="form-group row">
								<label for="userId"
									class="col-md-4 col-form-label text-md-right">新しいパスワード</label>

								<div class="col-md-6">
									<input id="password" type="password" class="form-control"
										name="password" required>
								</div>
							</div>

							<div class="form-group row">
								<label for="passwordConfirm"
									class="col-md-4 col-form-label text-md-right">新しいパスワード(確認用)</label>

								<div class="col-md-6">
									<input id="passwordConfirm" type="password"
										class="form-control" name="passwordConfirm" required>
								</div>
							</div>


							<div class="form-group row mb-0">
								<div class="col-md-12 text-center">
									<button type="button" class="btn btn-primary" id="update">変更</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="common/JS/password.js"></script>
</html>