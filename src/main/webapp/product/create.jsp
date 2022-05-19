<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 5/18/2022
  Time: 3:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new product</title>
</head>
<body>
<form method="post">
<%--    <input type="text" name="id">--%>
<%--    <input type="text" name="name">--%>
<%--    <input type="text" name="price">--%>
<%--    <input type="text" name="categoryId">--%>
<%--    --%>
    <div class="product-box">
        <span class="sale_tag"></span>
        <p><a href="product_detail.html"><img src="themes/images/ladies/2.jpg"
                                              alt=""/></a></p>
<%--        <input type="text" name="id"><br/>--%>
        <input type="text" name="name"><br/>
        <select name="categoryName" id="categoryName">
            <option value="fruit">fruit</option>
            <option value="flower">flower</option>
            <option value="animal">animal</option>
        </select>
        <input type="text" name="price">
    </div>
    <button>Add</button>
</form>
</body>
</html>
