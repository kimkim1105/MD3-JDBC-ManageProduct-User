<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 5/16/2022
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product</title>
</head>
<body>
<h1>Product list</h1>
<br>
<p>
    <a href="/products?action=create">Create new product</a>
</p>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Category</th>
    </tr>
    <c:forEach begin="0" end="${products.size()-1}" var="i">
    <tr>
        <td>${products.get(i).id}</td>
        <td>${products.get(i).name}</td>
        <td>${products.get(i).price}</td>
        <td>${categories.get(i).name}</td>
    </tr>
</c:forEach>
</table>

</body>
</html>
