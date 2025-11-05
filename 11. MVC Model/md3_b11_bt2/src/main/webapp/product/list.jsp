<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 05/11/2025
  Time: 8:53 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Danh sách sản phẩm</title>
</head>
<body>
<h2>Danh sách sản phẩm</h2>
<a href="/products?action=create">Thêm sản phẩm mới</a>
<form action="/products" method="get">
    <input type="hidden" name="action" value="search">
    <input type="text" name="keyword" placeholder="Nhập tên sản phẩm...">
    <input type="submit" value="Tìm kiếm">
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Nhà sản xuất</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price}</td>
            <td>${p.producer}</td>
            <td>
                <a href="/products?action=view&id=${p.id}">Xem</a>
                <a href="/products?action=edit&id=${p.id}">Sửa</a>
                <a href="/products?action=delete&id=${p.id}">Xoá</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
