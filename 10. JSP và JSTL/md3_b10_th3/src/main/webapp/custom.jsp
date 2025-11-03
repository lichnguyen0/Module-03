<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Danh sách khách hàng</title>
  <style>
    table {
      border-collapse: collapse;
      width: 80%;
      margin: 20px auto;
      box-shadow: 0 0 10px rgba(0,0,255,0.1);
    }
    th, td {
      border: 1px solid #333;
      padding: 8px;
      text-align: center;
    }
    th {
      background-color: #f2f2f2;
    }
    img {
      width: 80px;
      height: 80px;
    }
  </style>
</head>
<body>
<h2 style="text-align:center;">Danh sách khách hàng</h2>

<c:set var="customers" value="${[
    {'name':'Mai Văn Hoàn', 'dob':'1983-08-20', 'address':'Hà Nội', 'photo':'images/a1.png'},
    {'name':'Nguyễn Văn Nam', 'dob':'1983-08-21', 'address':'Bắc Giang', 'photo':'images/a2.png'},
    {'name':'Nguyễn Thái Hòa', 'dob':'1983-08-22', 'address':'Nam Định', 'photo':'images/a3.png'},
    {'name':'Trần Đăng Khoa', 'dob':'1983-08-17', 'address':'Hà Tây', 'photo':'images/a4.png'},
    {'name':'Nguyễn Đình Thi', 'dob':'1983-08-19', 'address':'Hà Nội', 'photo':'images/a5.png'}
]}" />

<table>
  <tr>
    <th>Tên</th>
    <th>Ngày sinh</th>
    <th>Địa chỉ</th>
    <th>Ảnh</th>
  </tr>
  <c:forEach var="customer" items="${customers}">
    <tr>
      <td>${customer.name}</td>
      <td>${customer.dob}</td>
      <td>${customer.address}</td>
      <td><img src="${customer.photo}" alt="Avatar"/></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
