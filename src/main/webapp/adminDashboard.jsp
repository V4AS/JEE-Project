<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
<h1>User Management</h1>
<!-- Example form for activating a user -->
<form action="adminDashboard" method="post">
    <input type="hidden" name="action" value="activate">
    <input type="number" name="userId" required>
    <input type="submit" value="Activate User">
</form>
<!-- Example form for deactivating a user -->
<form action="adminDashboard" method="post">
    <input type="hidden" name="action" value="deactivate">
    <input type="number" name="userId" required>
    <input type="submit" value="Deactivate User">
</form>
</body>
</html>
