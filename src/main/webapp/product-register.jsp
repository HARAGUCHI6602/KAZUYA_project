<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.entity.Category" %>
<%
    @SuppressWarnings("unchecked")
    List<String> errors = (List<String>) request.getAttribute("errors");

    String oldName  = (String) request.getAttribute("old_name");
    String oldPrice = (String) request.getAttribute("old_price");
    String oldStock = (String) request.getAttribute("old_stock");
    String oldCatId = (String) request.getAttribute("old_categoryId");

    @SuppressWarnings("unchecked")
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    if (categories == null) categories = new ArrayList<>();
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>商品登録</title>
  <style>
    body { font-family: system-ui, sans-serif; margin: 24px; }
    .errors { background:#fee; border:1px solid #f88; padding:12px; margin-bottom:16px; }
    .errors li { margin-left: 1em; }
    label { display:block; margin-top:10px; }
    input, select { width: 280px; padding:6px; }
    button { margin-top:16px; padding:8px 16px; }
    a { margin-left:8px; }
  </style>
</head>
<body>
  <h1>商品登録</h1>

  <% if (errors != null && !errors.isEmpty()) { %>
    <div class="errors">
      <strong>入力内容を確認してください</strong>
      <ul>
        <% for (String e : errors) { %><li><%= e %></li><% } %>
      </ul>
    </div>
  <% } %>

 <form action="${pageContext.request.contextPath}/products/register" method="post">
    <label>商品名
      <input type="text" name="name" value="<%= oldName != null ? oldName : "" %>" required>
    </label>

    <label>価格
      <input type="number" name="price" min="0" step="1"
             value="<%= oldPrice != null ? oldPrice : "" %>" required>
    </label>

    <label>在庫数
      <input type="number" name="stock" min="0" step="1"
             value="<%= oldStock != null ? oldStock : "" %>" required>
    </label>

    <label>カテゴリ
      <select name="categoryId" required>
        <option value="">-- 選択してください --</option>
        <% for (Category c : categories) {
             String sel = (oldCatId != null && oldCatId.equals(String.valueOf(c.getId()))) ? "selected" : "";
        %>
          <option value="<%= c.getId() %>" <%= sel %>><%= c.getName() %></option>
        <% } %>
      </select>
    </label>

    <button type="submit">登録する</button>
    <a href="<%= request.getContextPath() %>/products">一覧へ戻る</a>
  </form>
</body>
</html>



