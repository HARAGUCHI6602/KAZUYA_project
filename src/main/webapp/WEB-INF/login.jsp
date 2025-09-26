<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>ログイン</title>
  <style>
    body { font-family: sans-serif; margin: 2rem; }
    form { display: grid; gap: 1rem; max-width: 300px; }
    .error { color: red; }
  </style>
</head>
<body>
  <h1>ログイン</h1>

  <%-- エラーメッセージ表示 --%>
  <%
    String err = (String) request.getAttribute("error");
    if (err != null) {
  %>
      <p class="error"><%= err %></p>
  <% } %>

  <form method="post" action="${pageContext.request.contextPath}/login">
    <label>メールアドレス:
      <input type="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>
    </label>
    <label>パスワード:
      <input type="password" name="password" required>
    </label>
    <button type="submit">ログイン</button>
  </form>

  <p>テスト用ユーザー: <code>user@example.com / pass1234</code></p>
</body>
</html>




