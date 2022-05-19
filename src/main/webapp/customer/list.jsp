<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 5/16/2022
  Time: 10:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customer</title>
</head>
<body>
<h1>Customer list</h1>
<br>
<p>
    <a href="/customers?action=create">Create new customer</a>
</p>
<table>
    <tr>
    <td>ID</td>
    <td>Name</td>
    <td>Age</td>
    </tr>
    <c:forEach items="${customers}" var="cus">
        <tr>
            <td>${cus.id}</td>
            <td>${cus.name}</td>
            <td>${cus.age}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
