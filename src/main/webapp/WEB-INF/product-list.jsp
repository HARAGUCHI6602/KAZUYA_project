<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.Product" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>商品一覧</title>
  <style>
    body { font-family: system-ui, -apple-system, "Segoe UI", Arial, sans-serif; margin: 24px; }
    h1 { margin: 0 0 12px; }
    .toolbar { margin: 12px 0 16px; display:flex; gap:8px; align-items:center; }
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
    .spacer { flex:1; }
    .whoami { color:#555; font-size: 0.9rem; }
  </style>
</head>
<body>
  <h1>商品一覧</h1>

  <!-- 上部メニュー：★ログアウト追加 -->
  <div class="toolbar">
    <a class="btn" href="<%= request.getContextPath() %>/products/register">商品を登録</a>
    <a class="btn" href="<%= request.getContextPath() %>/home.jsp">ホーム</a>
    <span class="spacer"></span>
    <span class="whoami">
      <% Object u = session.getAttribute("user"); if (u != null) { %>
        ログイン中: <%= u.toString() %>
      <% } %>
    </span>
    <a class="btn" href="<%= request.getContextPath() %>/logout">ログアウト</a>
  </div>

  <!-- ▼ フラッシュ / エラー -->
  <%
    String flash = (String) request.getAttribute("flashMessage");
    if (flash == null) { flash = (String) session.getAttribute("flashMessage"); }
    String reqErr = (String) request.getAttribute("error");
  %>
  <% if (flash != null) { %><div class="flash"><%= flash %></div><% } %>
  <% if (reqErr != null) { %><div class="error"><%= reqErr %></div><% } %>
  <% session.removeAttribute("flashMessage"); %>

  <%
    @SuppressWarnings("unchecked")
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
          String cat = (p.getCategoryName() == null || p.getCategoryName().isEmpty()) ? "-" : p.getCategoryName();
      %>
        <tr>
          <td><%= p.getId() %></td>
          <td><%= p.getName() %></td>
          <td>¥<%= p.getPrice() %></td>
          <td><%= p.getStock() %></td>
          <!-- カテゴリ“名前”を表示 -->
          <td><%= cat %></td>
          <td class="actions">
            <a class="btn" href="<%= request.getContextPath() %>/products/edit?id=<%= p.getId() %>">編集</a>
            <form action="<%= request.getContextPath() %>/products/delete" method="post" style="display:inline;">
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

