<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 05/11/2025
  Time: 9:14 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Thêm sản phẩm</title>
</head>
<body>
<h2>Thêm sản phẩm mới</h2>
<form action="/products" method="post">
  <input type="hidden" name="action" value="create"/>
  <table>
    <tr>
      <td>Tên sản phẩm:</td>
      <td><input type="text" name="name" required/></td>
    </tr>
    <tr>
      <td>Giá:</td>
      <td><input type="number" step="0.01" name="price" required/></td>
    </tr>
    <tr>
      <td>Mô tả:</td>
      <td><textarea name="description" rows="4" cols="30"></textarea></td>
    </tr>
    <tr>
      <td>Nhà sản xuất:</td>
      <td><input type="text" name="producer" /></td>
    </tr>
    <tr>
      <td></td>
      <td>
        <button type="submit">Lưu</button>
        <a href="/products">Hủy</a>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
