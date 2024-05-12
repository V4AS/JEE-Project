package com.jmal.printmanagement.servlets;

import com.jmal.printmanagement.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fetchSubjects")
public class FetchSubjectsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("user");
        List<Subject> subjects = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT subject_id, name, student_count FROM subjects WHERE teacher_id = (SELECT user_id FROM users WHERE username = ?)");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                subjects.add(new Subject(
                        rs.getInt("subject_id"),
                        rs.getString("name"),
                        rs.getInt("student_count")));
            }
            request.setAttribute("subjects", subjects);
            request.setAttribute("username", username);
            System.out.println("USERNAME IS"+username);
            System.out.println("subjects IS"+subjects);

            request.getRequestDispatcher("/teacherDashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch subjects.");
        }
    }

    public static class Subject {
        private int subjectId;
        private String subjectName;

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public int getStudentCount() {
            return studentCount;
        }

        public void setStudentCount(int studentCount) {
            this.studentCount = studentCount;
        }

        private int studentCount;

        public Subject(int subjectId, String subjectName, int studentCount) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
            this.studentCount = studentCount;
        }
    }
}
