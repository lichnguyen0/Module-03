<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 05/11/2025
  Time: 11:03 SA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>User Management Application</title>
</head>
<body>
<center>
  <h1>User Management</h1>

  <!-- Link thêm user mới -->
  <h2>
    <a href="/users?action=create">Add New User</a>
  </h2>

  <!-- Form tìm kiếm theo country -->
  <form action="/users" method="get" style="margin-bottom: 10px;">
    <input type="hidden" name="action" value="search">
    <input type="text" name="country" placeholder="Enter country">
    <input type="submit" value="Search">
  </form>

  <!-- Nút sắp xếp theo name -->
  <form action="/users" method="get" style="margin-bottom: 20px;">
    <input type="hidden" name="action" value="sort">
    <input type="submit" value="Sort by Name">
  </form>
</center>

<div align="center">
  <table border="1" cellpadding="5">
    <caption><h2>List of Users</h2></caption>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Country</th>
      <th>Actions</th>
    </tr>

    <c:forEach var="user" items="${listUser}">
      <tr>
        <td><c:out value="${user.id}"/></td>
        <td><c:out value="${user.name}"/></td>
        <td><c:out value="${user.email}"/></td>
        <td><c:out value="${user.country}"/></td>
        <td>
          <a href="/users?action=edit&id=${user.id}">Edit</a> |
          <a href="/users?action=delete&id=${user.id}">Delete</a>
        </td>
      </tr>
    </c:forEach>

    <c:if test="${empty listUser}">
      <tr>
        <td colspan="5">No users found.</td>
      </tr>
    </c:if>
  </table>
</div>
</body>
</html>