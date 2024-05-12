package com.jmal.printmanagement.servlets;

import com.jmal.printmanagement.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/agentDashboard")
public class AgentDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PrintRequest> printRequests = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT print_requests.number_of_copies, print_requests.document_path, print_requests.print_date, users.username " +
                    "FROM print_requests " +
                    "JOIN users ON print_requests.teacher_id = users.user_id";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PrintRequest printRequest = new PrintRequest(
                        rs.getString("username"),
                        rs.getInt("number_of_copies"),
                        rs.getString("document_path"),
                        rs.getTimestamp("print_date"));
                printRequests.add(printRequest);
            }
            request.setAttribute("printRequests", printRequests);
            request.getRequestDispatcher("/agentDashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch print requests.");
        }
    }

    public static class PrintRequest {
        private String teacherName;
        private int copies;
        private String documentPath;
        private java.sql.Timestamp printDate;

        public PrintRequest(String teacherName, int copies, String documentPath, java.sql.Timestamp printDate) {
            this.teacherName = teacherName;
            this.copies = copies;
            this.documentPath = documentPath;
            this.printDate = printDate;
        }

        // Getters to access the properties
        public String getTeacherName() {
            return teacherName;
        }

        public int getCopies() {
            return copies;
        }

        public String getDocumentPath() {
            return documentPath;
        }

        public java.sql.Timestamp getPrintDate() {
            return printDate;
        }
    }
}
