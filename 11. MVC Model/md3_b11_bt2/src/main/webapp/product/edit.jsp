<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 05/11/2025
  Time: 9:15 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Sửa sản phẩm</title>
</head>
<body>
<h2>Sửa sản phẩm</h2>

<c:if test="${product == null}">
  <p>Sản phẩm không tồn tại.</p>
  <a href="/products">Quay lại danh sách</a>
</c:if>

<c:if test="${product != null}">
  <form action="/products" method="post">
    <input type="hidden" name="action" value="edit"/>
    <input type="hidden" name="id" value="${product.id}"/>
    <table>
      <tr>
        <td>Tên sản phẩm:</td>
        <td><input type="text" name="name" value="${product.name}" required/></td>
      </tr>
      <tr>
        <td>Giá:</td>
        <td><input type="number" step="0.01" name="price" value="${product.price}" required/></td>
      </tr>
      <tr>
        <td>Mô tả:</td>
        <td><textarea name="description" rows="4" cols="30">${product.description}</textarea></td>
      </tr>
      <tr>
        <td>Nhà sản xuất:</td>
        <td><input type="text" name="producer" value="${product.producer}"/></td>
      </tr>
      <tr>
        <td></td>
        <td>
          <button type="submit">Cập nhật</button>
          <a href="/products">Hủy</a>
        </td>
      </tr>
    </table>
  </form>
</c:if>

</body>
</html>
