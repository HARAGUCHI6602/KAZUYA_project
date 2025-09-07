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
</style>
</head>
<body>
  <h1>商品一覧</h1>

  <p><a class="button" href="<%=request.getContextPath()%>/products/register">商品を登録</a></p>

  <%-- 登録直後のメッセージ（任意） --%>
  <%
    String flash = (String)request.getAttribute("flash");
    if (flash != null) {
  %>
    <div class="flash"><%= flash %></div>
  <% } %>

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
          <th>ID</th><th>商品名</th><th>価格</th><th>在庫</th><th>カテゴリ</th>
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

