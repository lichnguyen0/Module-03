<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background: #fafafa;
            padding: 40px 20px;
        }

        .container {
            max-width: 1100px;
            margin: 0 auto;
        }

        h1 {
            color: #1a1a1a;
            font-size: 28px;
            font-weight: 500;
            margin-bottom: 32px;
        }

        .actions-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 16px;
            margin-bottom: 24px;
            flex-wrap: wrap;
        }

        .btn-add {
            padding: 10px 20px;
            background: #1a1a1a;
            color: white;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            border-radius: 3px;
            transition: background 0.2s;
        }

        .btn-add:hover {
            background: #404040;
        }

        .search-form {
            display: flex;
            gap: 8px;
            flex: 1;
            max-width: 400px;
        }

        .search-form input[type="text"] {
            flex: 1;
            padding: 10px 12px;
            border: 1px solid #d4d4d4;
            border-radius: 3px;
            font-size: 14px;
        }

        .search-form input[type="text"]:focus {
            outline: none;
            border-color: #1a1a1a;
        }

        .search-form input[type="submit"] {
            padding: 10px 20px;
            background: white;
            color: #1a1a1a;
            border: 1px solid #d4d4d4;
            border-radius: 3px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s;
        }

        .search-form input[type="submit"]:hover {
            border-color: #1a1a1a;
        }

        .table-container {
            background: white;
            border: 1px solid #e5e5e5;
            border-radius: 4px;
            overflow: hidden;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background: #fafafa;
            border-bottom: 1px solid #e5e5e5;
        }

        th {
            padding: 14px 16px;
            text-align: left;
            font-weight: 500;
            font-size: 13px;
            color: #404040;
        }

        td {
            padding: 14px 16px;
            border-bottom: 1px solid #f5f5f5;
            color: #1a1a1a;
            font-size: 14px;
        }

        tbody tr:hover {
            background: #fafafa;
        }

        tbody tr:last-child td {
            border-bottom: none;
        }

        .action-links {
            display: flex;
            gap: 16px;
        }

        .action-links a {
            color: #404040;
            text-decoration: none;
            font-size: 13px;
            font-weight: 500;
            transition: color 0.2s;
        }

        .action-links a:hover {
            color: #1a1a1a;
        }

        .action-links a.delete:hover {
            color: #dc2626;
        }

        .empty-state {
            text-align: center;
            padding: 48px 20px;
            color: #737373;
            font-size: 14px;
        }

        @media (max-width: 768px) {
            .actions-bar {
                flex-direction: column;
                align-items: stretch;
            }

            .search-form {
                max-width: 100%;
            }

            table {
                font-size: 13px;
            }

            th, td {
                padding: 12px 10px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Student Management</h1>

    <div class="actions-bar">
        <a href="students?action=create" class="btn-add">Add Student</a>

        <form action="students" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Search...">
            <input type="submit" value="Search">
        </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Student Code</th>
                <th>Full Name</th>
                <th>Class</th>
                <th>GPA</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${listStudent}">
                <tr>
                    <td><c:out value="${student.id}"/></td>
                    <td><c:out value="${student.studentCode}"/></td>
                    <td><c:out value="${student.fullName}"/></td>
                    <td><c:out value="${student.className}"/></td>
                    <td><c:out value="${student.gpa}"/></td>
                    <td>
                        <div class="action-links">
                            <a href="students?action=edit&id=${student.id}">Edit</a>
                            <a href="students?action=delete&id=${student.id}" class="delete" onclick="return confirm('Delete this student?')">Delete</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty listStudent}">
                <tr>
                    <td colspan="6" class="empty-state">
                        No students found
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>