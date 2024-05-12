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

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt;
            if ("activate".equals(action)) {
                stmt = conn.prepareStatement("UPDATE users SET active = TRUE WHERE user_id = ?");
            } else if ("deactivate".equals(action)) {
                stmt = conn.prepareStatement("UPDATE users SET active = FALSE WHERE user_id = ?");
            } else {
                throw new IllegalArgumentException("Unknown action: " + action);
            }
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            response.sendRedirect("adminDashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to update user status.");
        }
    }
}
