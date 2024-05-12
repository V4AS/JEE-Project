<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jmal.printmanagement.servlets.FetchSubjectsServlet.Subject" %>

<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
</head>
<body>
<h1>Welcome, <%= request.getAttribute("username") %> </h1>

<form action="UploadServlet" method="post" enctype="multipart/form-data">
    Subject:
    <select name="subject_id">
    <%
           List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
           if (subjects != null) {
               for (int i = 0; i < subjects.size(); i++) {
                   Subject subject = subjects.get(i);
           %>
                   <option value="<%= subject.getSubjectId() %>">
                       <%= subject.getSubjectName() %> - Max Copies: <%= subject.getStudentCount() %>
                   </option>
           <%
               }
           }
           %>
    </select><br>
    Document to Print (PDF only): <input type="file" name="document" accept="application/pdf" required><br>
    Number of Copies: <input type="number" name="copies" min="1" required><br>
    Date and Time of Print: <input type="datetime-local" name="printDate" required><br>
    <input type="submit" value="Submit Print Request">
</form>

<a href="logout">Logout</a>
</body>
</html>
