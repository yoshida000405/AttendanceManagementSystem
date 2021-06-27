<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>パスワード変更エラー</title>
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
					<div class="card-header text-center">パスワード変更に失敗しました</div>

					<div class="card-body text-center">
						<h5>URLの期限は24時間以内です。</h5>
						<h5 class="mt-3">パスワード変更を再申請すると以前に送られたURLは無効となります。</h5>
						<input type="button" class="mt-5 btn btn-primary" value="パスワード変更画面に戻る" onclick="location.href='password_change_request.jsp';">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>