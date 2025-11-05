<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 05/11/2025
  Time: 9:16 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Chi tiết sản phẩm</title>
</head>
<body>
<h2>Chi tiết sản phẩm</h2>

<c:if test="${product == null}">
  <p>Sản phẩm không tồn tại.</p>
  <a href="/products">Quay lại danh sách</a>
</c:if>

<c:if test="${product != null}">
  <table border="0">
    <tr><td><strong>ID:</strong></td><td>${product.id}</td></tr>
    <tr><td><strong>Tên:</strong></td><td>${product.name}</td></tr>
    <tr><td><strong>Giá:</strong></td><td>${product.price}</td></tr>
    <tr><td><strong>Mô tả:</strong></td><td>${product.description}</td></tr>
    <tr><td><strong>Nhà sản xuất:</strong></td><td>${product.producer}</td></tr>
  </table>

  <p>
    <a href="/products?action=edit&id=${product.id}">Sửa</a> |
    <a href="/products?action=delete&id=${product.id}" onclick="return confirm('Xác nhận xoá sản phẩm này?');">Xoá</a> |
    <a href="/products">Quay lại danh sách</a>
  </p>
</c:if>

</body>
</html>
