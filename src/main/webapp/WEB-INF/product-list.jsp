<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.Product" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>商品一覧</title>
  <style>
    body { font-family: system-ui, -apple-system, "Segoe UI", Arial, sans-serif; margin: 24px; }
    h1 { margin: 0 0 12px; }
    .toolbar { margin: 12px 0 16px; }
    a.btn, button.btn {
      display:inline-block; padding:6px 10px; border:1px solid #333; border-radius:6px;
      text-decoration:none; background:#fff; cursor:pointer;
    }
    a.btn:hover, button.btn:hover { background:#f4f4f4; }
    .flash { background:#e6ffed; border:1px solid #9ae6b4; padding:8px; margin: 8px 0 16px; }
    .error { background:#ffe6e6; border:1px solid #f5a3a3; padding:8px; margin: 8px 0 16px; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background:#f4f5f7; }
    .actions a, .actions form { display:inline-block; margin-right:6px; }
    .muted { color:#777; }
  </style>
</head>
<body>
  <h1>商品一覧</h1>

  <!-- 上部メニュー -->
  <div class="toolbar">
    <a class="btn" href="<%= request.getContextPath() %>/products/register">商品を登録</a>
    <a class="btn" href="<%= request.getContextPath() %>/home.jsp">ホーム</a>
  </div>

  <!-- ▼ フラッシュ/エラー（session → 表示 → セッションから削除） -->
  <%
    String flash = (String) session.getAttribute("flashMessage");
    String ferr  = (String) session.getAttribute("flashError");
    if (flash != null) { %><div class="flash"><%= flash %></div><% }
    if (ferr  != null) { %><div class="error"><%= ferr %></div><% }
    session.removeAttribute("flashMessage");
    session.removeAttribute("flashError");
  %>

  <!-- ▼ サーブレットからのエラー（request属性） -->
  <%
    String reqErr = (String) request.getAttribute("error");
    if (reqErr != null) { %><div class="error"><%= reqErr %></div><% }
  %>

  <%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null || products.isEmpty()) {
  %>
    <p class="muted">商品データがありません。</p>
  <%
    } else {
  %>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>商品名</th>
          <th>価格</th>
          <th>在庫</th>
          <th>カテゴリ</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      <%
        for (Product p : products) {
      %>
        <tr>
          <td><%= p.getId() %></td>
          <td><%= p.getName() %></td>
          <td>¥<%= p.getPrice() %></td>
          <td><%= p.getStock() %></td>
          <td><%= (p.getCategoryName() == null || p.getCategoryName().isEmpty()) ? "-" : p.getCategoryName() %></td>
          <td class="actions">
            <!-- ★ 編集ページへ遷移 -->
            <a class="btn" href="<%= request.getContextPath() %>/products/edit?id=<%= p.getId() %>">編集</a>

            <!-- ★ 削除確認へ（GET） -->
            <form action="<%= request.getContextPath() %>/delete-confirm" method="get" style="display:inline;">
              <input type="hidden" name="id" value="<%= p.getId() %>">
              <button class="btn" type="submit">削除</button>
            </form>
          </td>
        </tr>
      <%
        }
      %>
      </tbody>
    </table>
  <%
    }
  %>
</body>
</html>


