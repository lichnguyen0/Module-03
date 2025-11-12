<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Student Management</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            background: #fafafa;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .login-container {
            background: white;
            border: 1px solid #e5e5e5;
            border-radius: 4px;
            padding: 48px;
            width: 100%;
            max-width: 380px;
        }

        h1 {
            color: #1a1a1a;
            font-size: 22px;
            font-weight: 500;
            margin-bottom: 8px;
        }

        .subtitle {
            color: #737373;
            font-size: 13px;
            margin-bottom: 32px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            color: #404040;
            font-size: 13px;
            font-weight: 500;
            margin-bottom: 6px;
        }

        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #d4d4d4;
            border-radius: 3px;
            font-size: 14px;
            transition: border-color 0.2s;
            background: white;
        }

        .form-group input:focus {
            outline: none;
            border-color: #1a1a1a;
        }

        .btn-login {
            width: 100%;
            padding: 11px;
            background: #1a1a1a;
            color: white;
            border: none;
            border-radius: 3px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s;
            margin-top: 8px;
        }

        .btn-login:hover {
            background: #404040;
        }

        .error-message {
            background: #fef2f2;
            color: #991b1b;
            padding: 10px 12px;
            border-radius: 3px;
            margin-top: 16px;
            font-size: 13px;
            border-left: 2px solid #991b1b;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>Admin Login</h1>
    <p class="subtitle">Student Management System</p>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required/>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required/>
        </div>

        <button type="submit" class="btn-login">Login</button>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
    </form>
</div>
</body>
</html>