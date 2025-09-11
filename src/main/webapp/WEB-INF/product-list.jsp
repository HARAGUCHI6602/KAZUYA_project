<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧</title>
<style>
body { font-family: sans-serif; margin: 24px; }
table { border-collapse: collapse; width: 100%; }
th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
a.button { display:inline-block; padding:6px 10px; border:1px solid #333; text-decoration:none; margin-bottom:12px; }
.flash { background:#e6ffed; border:1px solid #9ae6b4; padding:8px; margin-bottom:12px; }
.error { background:#ffe6e6; border:1px solid #f5a3a3; padding:8px; margin-bottom:12px; }
</style>
</head>
<body>
  <h1>商品一覧</h1>

  <p><a class="button" href="<%=request.getContextPath()%>/products/register">商品を登録</a></p>

  <%-- ▼ 削除後などのフラッシュメッセージ（sessionから取り出して表示→消す） --%>
  <%
    String flash = (String) session.getAttribute("flashMessage");
    String ferr  = (String) session.getAttribute("flashError");
    if (flash != null) { %><div class="flash"><%= flash %></div><% }
    if (ferr  != null) { %><div class="error"><%= ferr %></div><% }
    session.removeAttribute("flashMessage");
    session.removeAttribute("flashError");
  %>

  <%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null || products.isEmpty()) {
  %>
    <p>データがありません。</p>
  <%
    } else {
  %>
    <table>
      <thead>
        <tr>
          <th>ID</th><th>商品名</th><th>価格</th><th>在庫</th><th>カテゴリ</th><th>操作</th> <%-- ★ 操作列追加 --%>
        </tr>
      </thead>
      <tbody>
      <%
        for (Product p : products) {
      %>
        <tr>
          <td><%= p.getId() %></td>
          <td><%= p.getName() %></td>
          <td><%= p.getPrice() %></td>
          <td><%= p.getStock() %></td>
          <td><%= p.getCategoryName() %></td>
          <td>
            <%-- ★ 削除確認へ遷移（GET） --%>
            <form action="<%= request.getContextPath() %>/delete-confirm" method="get" style="display:inline;">
              <input type="hidden" name="id" value="<%= p.getId() %>">
              <button type="submit">削除</button>
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

