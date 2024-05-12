<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Register</h2>
<form method="post" action="register">
    Username: <input type="text" name="username" required><br>
    Password: <input type="password" name="password" required><br>
    Role: <select name="role">
        <option value="teacher">Teacher</option>
        <option value="print_agent">Print Agent</option>
        <option value="administrator">Administrator</option>
    </select><br>
    <input type="submit" value="Register">
</form>
</body>
</html>
