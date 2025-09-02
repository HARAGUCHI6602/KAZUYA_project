<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>カテゴリリスト</title>
    <style>
        body { font-family: system-ui, sans-serif; }
        table { border-collapse: collapse; margin-top: 12px; }
        th, td { border: 1px solid #333; padding: 6px 10px; }
        th { background: #f3f3f3; }
    </style>
</head>
<body>
<h2>カテゴリリスト</h2>

<table>
    <tr>
        <th>カテゴリID</th>
        <th>カテゴリ名</th>
    </tr>

    <!-- ここで一覧をぐるぐる表示 -->
    <c:forEach var="cat" items="${categoryList}">
        <tr>
            <td><c:out value="${cat.id}"/></td>
            <td><c:out value="${cat.name}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>



