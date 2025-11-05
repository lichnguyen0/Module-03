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
    <title>>Kết quả tìm kiếm</title>
</head>
<body>
<h2>Kết quả tìm kiếm</h2>

<form action="/products" method="get">
  <input type="hidden" name="action" value="search"/>
  <input type="text" name="keyword" value="${param.keyword}" placeholder="Nhập tên sản phẩm..." />
  <button type="submit">Tìm</button>
  <a href="/products">Danh sách đầy đủ</a>
</form>

<c:if test="${empty products}">
  <p>Không tìm thấy sản phẩm khớp với '<c:out value='${param.keyword}'/>'</p>
</c:if>

<c:if test="${not empty products}">
  <table border="1">
    <tr>
      <th>ID</th><th>Tên</th><th>Giá</th><th>Nhà sản xuất</th><th>Hành động</th>
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
          <a href="/products?action=delete&id=${p.id}" onclick="return confirm('Xác nhận xoá?');">Xoá</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>

</body>
</html>
