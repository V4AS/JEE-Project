<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.jmal.printmanagement.servlets.AgentDashboardServlet.PrintRequest" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Agent Dashboard</title>
</head>
<body>
<h1>Print Requests</h1>
<table border="1">
    <tr>
        <th>Teacher Name</th>
        <th>Number of Copies</th>
        <th>Document</th>
        <th>Print Date</th>
    </tr>
        <%
        List<PrintRequest> printRequests = (List<PrintRequest>) request.getAttribute("printRequests");
        if (printRequests != null) {
            for (PrintRequest r : printRequests) {
        %>
        <tr>
            <td><%= r.getTeacherName() %></td>
            <td><%= r.getCopies() %></td>
            <td><a href="http://127.0.0.1:8000<%= r.getDocumentPath() %>" target="_blank">View Document</a></td>
            <td><%= r.getPrintDate() %></td>
        </tr>
        <%
            }
        }
        %>
</table>
</body>
</html>
