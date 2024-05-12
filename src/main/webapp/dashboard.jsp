<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Welcome, <%= request.getSession().getAttribute("user") %></h1>
<p>This is your dashboard.</p>
<a href="logout">Logout</a>
</body>
</html>
