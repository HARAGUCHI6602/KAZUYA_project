<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,java.util.Map" %>
<%@ page import="model.entity.Product,model.entity.Category" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>商品編集</title>
  <style>
    body { font-family: system-ui, -apple-system, "Segoe UI", Arial, sans-serif; margin:24px; }
    h1 { margin-bottom: 12px; }
    form { max-width: 520px; }
    label { display:block; margin-top:12px; font-weight:bold; }
    input[type=text], select { width: 100%; padding:8px; box-sizing: border-box; }
    .actions { margin-top: 16px; }
    .btn { display:inline-block; padding:8px 12px; border:1px solid #333; border-radius:6px; text-decoration:none; background:#fff; }
    .btn:hover { background:#f4f4f4; }
    .error { color:#c00; font-size:0.9em; margin-top:4px; }
    .alert { background:#ffe6e6; border:1px solid #f5a3a3; padding:8px; margin: 8px 0 16px; }
  </style>
</head>
<body>
<%
  Product product = (Product) request.getAttribute("product");
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  Map<String,String> errors = (Map<String,String>) request.getAttribute("errors");
  String generalError = (errors != null) ? errors.get("general") : null;
%>

  <h1>商品編集</h1>

  <% if (generalError != null) { %>
    <div class="alert"><%= generalError %></div>
  <% } %>

  <form method="post" action="<%= request.getContextPath() %>/products/update">
    <!-- ID（非表示） -->
    <input type="hidden" name="id" value="<%= product != null ? product.getId() : 0 %>">

    <!-- 商品名 -->
    <label for="name">商品名</label>
    <input type="text" id="name" name="name"
           value="<%= product != null && product.getName()!=null ? product.getName() : "" %>">
    <% if (errors != null && errors.get("name") != null) { %>
      <div class="error"><%= errors.get("name") %></div>
    <% } %>

    <!-- 価格 -->
    <label for="price">価格（円）</label>
    <input type="text" id="price" name="price"
           value="<%= product != null ? product.getPrice() : 0 %>">
    <% if (errors != null && errors.get("price") != null) { %>
      <div class="error"><%= errors.get("price") %></div>
    <% } %>

    <!-- 在庫 -->
    <label for="stock">在庫（個）</label>
    <input type="text" id="stock" name="stock"
           value="<%= product != null ? product.getStock() : 0 %>">
    <% if (errors != null && errors.get("stock") != null) { %>
      <div class="error"><%= errors.get("stock") %></div>
    <% } %>

    <!-- カテゴリ -->
    <label for="categoryId">カテゴリ</label>
    <select id="categoryId" name="categoryId">
      <option value="0">-- 選択してください --</option>
      <%
        int selectedCid = (product != null) ? product.getCategoryId() : 0;
        if (categories != null) {
          for (Category c : categories) {
      %>
        <option value="<%= c.getId() %>" <%= (selectedCid == c.getId()) ? "selected" : "" %>><%= c.getName() %></option>
      <%
          }
        }
      %>
    </select>
    <% if (errors != null && errors.get("categoryId") != null) { %>
      <div class="error"><%= errors.get("categoryId") %></div>
    <% } %>

    <div class="actions">
      <button class="btn" type="submit">保存</button>
      <a class="btn" href="<%= request.getContextPath() %>/products">一覧へ戻る</a>
    </div>
  </form>
</body>
</html>
