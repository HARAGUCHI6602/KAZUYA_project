<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.Category" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録</title>
<style>
body { font-family:sans-serif; margin:24px; }
label { display:block; margin-top:12px; }
input, select { padding:6px; width:280px; }
a { text-decoration:none; }
</style>
</head>
<body>
  <h1>商品登録フォーム</h1>
  <p><a href="<%=request.getContextPath()%>/products">← 一覧へ戻る</a></p>

  <form action="<%=request.getContextPath()%>/products/store" method="post">
    <label>商品名
      <input type="text" name="name"
             value="<%= request.getAttribute("old_name")!=null?request.getAttribute("old_name"):"" %>" required>
    </label>

    <label>価格（整数）
      <input type="number" name="price" min="0" step="1"
             value="<%= request.getAttribute("old_price")!=null?request.getAttribute("old_price"):"" %>" required>
    </label>

    <label>在庫数（整数）
      <input type="number" name="stock" min="0" step="1"
             value="<%= request.getAttribute("old_stock")!=null?request.getAttribute("old_stock"):"" %>" required>
    </label>

    <label>カテゴリ
      <select name="categoryId" required>
        <option value="">-- 選択してください --</option>
        <%
          List<Category> categories = (List<Category>) request.getAttribute("categories");
          String oldCat = (String) request.getAttribute("old_categoryId");
          if (categories != null) {
            for (Category c : categories) {
              String sel = (oldCat != null && oldCat.equals(String.valueOf(c.getId()))) ? "selected" : "";
        %>
              <option value="<%=c.getId()%>" <%=sel%>><%=c.getName()%></option>
        <%
            }
          } else {
        %>
            <option value="">（カテゴリが読み込めませんでした）</option>
        <%
          }
        %>
      </select>
    </label>

    <p style="margin-top:16px;"><button type="submit">登録</button></p>
  </form>
</body>
</html>

