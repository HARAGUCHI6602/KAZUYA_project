<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ホーム</title>
  <style>
    body { font-family: sans-serif; margin: 2rem; }
    form { display: inline; }
    button { padding: .5rem 1rem; }
  </style>
</head>
<body>
  <h1>ホーム画面</h1>

  <!-- ログイン時に LoginServlet が session.setAttribute("user", ...) を入れている想定 -->
  <p>こんにちは、<strong>${sessionScope.user}</strong> さん！</p>

  <form method="post" action="${pageContext.request.contextPath}/logout">
    <button type="submit">ログアウト</button>
  </form>

  <hr>
  <p>ここはログイン後だけ見られるページです。未ログインなら AuthFilter がログイン画面へ戻します。</p>
</body>
</html>

