<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Student</title>
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
      max-width: 500px;
      margin: 0 auto;
    }

    .back-link {
      display: inline-block;
      color: #404040;
      text-decoration: none;
      font-size: 13px;
      font-weight: 500;
      margin-bottom: 24px;
      transition: color 0.2s;
    }

    .back-link:hover {
      color: #1a1a1a;
    }

    .back-link::before {
      content: "← ";
    }

    h1 {
      color: #1a1a1a;
      font-size: 28px;
      font-weight: 500;
      margin-bottom: 8px;
    }

    .subtitle {
      color: #737373;
      font-size: 14px;
      margin-bottom: 32px;
    }

    .form-container {
      background: white;
      border: 1px solid #e5e5e5;
      border-radius: 4px;
      padding: 32px;
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

    .btn-submit {
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

    .btn-submit:hover {
      background: #404040;
    }
  </style>
</head>
<body>
<div class="container">
  <a href="students" class="back-link">Back to List</a>

  <h1>Edit Student</h1>
  <p class="subtitle">Update student information</p>

  <div class="form-container">
    <form action="students?action=edit" method="post">
      <input type="hidden" name="id" value="<c:out value='${student.id}'/>"/>

      <div class="form-group">
        <label for="studentCode">Student Code</label>
        <input type="text" id="studentCode" name="studentCode" value="<c:out value='${student.studentCode}'/>" required/>
      </div>

      <div class="form-group">
        <label for="fullName">Full Name</label>
        <input type="text" id="fullName" name="fullName" value="<c:out value='${student.fullName}'/>" required/>
      </div>

      <div class="form-group">
        <label for="className">Class</label>
        <input type="text" id="className" name="className" value="<c:out value='${student.className}'/>" required/>
      </div>

      <div class="form-group">
        <label for="gpa">GPA</label>
        <input type="number" id="gpa" step="0.01" min="0" max="4" name="gpa" value="<c:out value='${student.gpa}'/>" required/>
      </div>

      <button type="submit" class="btn-submit">Update</button>
    </form>
  </div>
</div>
</body>
</html>