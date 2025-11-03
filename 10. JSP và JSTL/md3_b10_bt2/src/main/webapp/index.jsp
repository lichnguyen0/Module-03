<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Simple Calculator</title>
</head>
<body>
<h1>Simple Calculator</h1>
<form action="calculate" method="post">
    <fieldset>
        <legend>Calculator</legend>
        <label>First operand: </label>
        <input type="text" name="firstOperand"><br><br>

        <label>Operator: </label>
        <select name="operator">
            <option value="Addition">Addition</option>
            <option value="Subtraction">Subtraction</option>
            <option value="Multiplication">Multiplication</option>
            <option value="Division">Division</option>
        </select><br><br>

        <label>Second operand: </label>
        <input type="text" name="secondOperand"><br><br>

        <input type="submit" value="Calculate">
    </fieldset>
</form>
</body>
</html>
