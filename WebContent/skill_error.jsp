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
<title>エラー</title>
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
			<div class="col-md-8">
				<div class="card">
					<div class="card-header text-center">エラーが発生しました</div>

					<div class="card-body text-center">
						<input type="button" class="btn btn-primary" value="スキルシート入力画面に戻る" onclick="location.href='skill.jsp';">
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