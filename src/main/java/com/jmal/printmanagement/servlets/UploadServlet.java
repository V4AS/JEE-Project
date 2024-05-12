package com.jmal.printmanagement.servlets;

import com.jmal.printmanagement.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String subjectIdStr = request.getParameterValues("subject_id")[0];
        String copiesStr = request.getParameterValues("copies")[0];
        System.out.println("subjectIdStr IS"+subjectIdStr);
        System.out.println("copiesStr IS"+copiesStr);

        if (subjectIdStr == null || subjectIdStr.isEmpty() || copiesStr == null || copiesStr.isEmpty()) {
            System.out.println("ERROR HAPPEND IS");

            request.setAttribute("error", "Subject and number of copies are required.");
            request.getRequestDispatcher("/teacherDashboard.jsp").forward(request, response);
            return;
        }

        int subjectId = Integer.parseInt(subjectIdStr);
        int copies = Integer.parseInt(copiesStr);

        String printDate = request.getParameter("printDate");
        Part filePart = request.getPart("document");
        String fileName = filePart.getSubmittedFileName();
        String filePath = "/uploads/" + fileName;

        File uploadsDir = new File(getServletContext().getRealPath("/uploads"));
        if (!uploadsDir.exists()) {
            uploadsDir.mkdir();
        }
        filePart.write(new File(uploadsDir, fileName).getAbsolutePath());
        System.out.println("uploadsDir IS"+uploadsDir);

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT student_count FROM subjects WHERE subject_id = ?");
            checkStmt.setInt(1, subjectId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && copies > rs.getInt("student_count")) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Number of copies exceeds the number of students.");
                return;
            }
            HttpSession session = request.getSession(false);

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO print_requests (subject_id, document_path, number_of_copies, print_date, teacher_id) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, subjectId);
            stmt.setString(2, filePath);
            stmt.setInt(3, copies);
            stmt.setString(4, printDate);
            stmt.setInt(5,        (int) session.getAttribute("id"));
            stmt.executeUpdate();
            System.out.println("stmt IS"+stmt);

            response.sendRedirect("fetchSubjects");
        } catch (SQLException e) {
            throw new ServletException("Database connection problem", e);
        }
    }
}
