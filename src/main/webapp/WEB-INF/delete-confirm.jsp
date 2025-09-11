<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.entity.Product" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>削除確認</title>
  <style>
    body { font-family: sans-serif; margin: 24px; }
    .box { border:1px solid #ccc; padding:12px; }
    .btn { padding:6px 10px; }
  </style>
</head>
<body>
  <h1>本当に削除しますか？</h1>
  <div class="box">
    <%
      Product p = (Product) request.getAttribute("product");
    %>
    <p>
      ID: <b><%= p.getId() %></b><br>
      商品名: <b><%= p.getName() %></b><br>
      価格: <b><%= p.getPrice() %></b><br>
      在庫: <b><%= p.getStock() %></b><br>
      カテゴリ: <b><%= p.getCategoryName() %></b>
    </p>

    <form action="<%= request.getContextPath() %>/delete" method="post" style="display:inline;">
      <input type="hidden" name="id" value="<%= p.getId() %>">
      <button type="submit" class="btn">はい（削除）</button>
    </form>
    <form action="<%= request.getContextPath() %>/products" method="get" style="display:inline;">
      <button type="submit" class="btn">いいえ（戻る）</button>
    </form>
  </div>
</body>
</html>

