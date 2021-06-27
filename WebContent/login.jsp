<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") != null) {
		response.sendRedirect("home.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>ログイン画面</title>
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
		</nav>
	</div>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-12">
				<div class="card">
					<div class="card-header">ログイン</div>

					<div class="card-body">
						<form action="LoginChk" method="post">
							<div class="form-group row">
								<label for="userId" class="col-md-3 col-form-label text-md-right">ID</label>

								<div class="col-md-6 ml-3">
									<input id="userId" type="text" class="form-control" name="userId" required>
								</div>
							</div>

							<div class="form-group row">
								<label for="password"
									class="col-md-3 col-form-label text-md-right">パスワード</label>

								<div class="col-md-6 ml-3">
									<input id="password" type="password" class="form-control" name="password" required>
								</div>
							</div>

							<div class="form-group row">
								<div class="col-md-12 text-center">
									<a href="password_change_request.jsp">パスワードを忘れた</a>
								</div>
							</div>

							<div class="form-group row mb-0 text-center">
								<div class="col-md-12">
									<button type="submit" class="btn btn-primary">ログイン</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<%
	}
%>