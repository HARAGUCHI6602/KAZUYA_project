<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>カテゴリリスト</title>
</head>
<body>
  <h2>カテゴリリスト</h2>

  <!-- デバッグ用：categoryList の中身を確認 -->
  <p>中身: <c:out value="${categoryList}" /></p>

  <table border="1">
    <tr>
      <th>カテゴリID</th>
      <th>カテゴリ名</th>
    </tr>
    <c:forEach var="category" items="${categoryList}">
      <tr>
        <td>${category.categoryId}</td>
        <td>${category.categoryName}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
