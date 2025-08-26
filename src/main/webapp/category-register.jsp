<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%
  // サーブレットから戻ってきたときに表示する用（最初はnullなので空にする）
  List<String> errors = (List<String>) request.getAttribute("errors");
  String categoryId   = (String) request.getAttribute("categoryId");
  String categoryName = (String) request.getAttribute("categoryName");
  if (categoryId == null) categoryId = "";
  if (categoryName == null) categoryName = "";
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>カテゴリ登録</title>
  <style>
    body { font-family: system-ui, sans-serif; max-width: 720px; margin: 24px auto; }
    .error { color:#b00020; }
    label { display:block; margin-top:12px; }
    input[type="text"], input[type="number"] { width: 240px; padding:6px; }
    button { margin-top:16px; padding:8px 16px; }
  </style>
</head>
<body>
  <h1>カテゴリ登録</h1>

  <% if (errors != null && !errors.isEmpty()) { %>
    <div class="error">
      <ul>
        <% for (String e : errors) { %><li><%= e %></li><% } %>
      </ul>
    </div>
  <% } %>

  <!-- フォーム。送信先はサーブレット /category/register -->
  <form method="post" action="<%= request.getContextPath() %>/category/register">
    <label>カテゴリID（数字）
      <input type="number" name="categoryId" required value="<%= categoryId %>">
    </label>

    <label>カテゴリ名
      <input type="text" name="categoryName" required maxlength="100" value="<%= categoryName %>">
    </label>

    <button type="submit">登録する</button>
  </form>

  <p style="margin-top:24px;">
    <a href="<%= request.getContextPath() %>/categories">一覧に戻る</a>
  </p>
</body>
</html>
