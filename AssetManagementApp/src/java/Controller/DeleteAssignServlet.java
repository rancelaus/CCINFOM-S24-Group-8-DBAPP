/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author kheig
 */
import Utilities.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteAssignServlet")
public class DeleteAssignServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String idParam = request.getParameter("assignmentID");
        if (idParam == null || idParam.trim().isEmpty()) {
            sendError(response, "Assignment ID is required.");
            return;
        }

        int assignmentID;
        try {
            assignmentID = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException nfe) {
            sendError(response, "Invalid Assignment ID format.");
            return;
        }

        String sql = "DELETE FROM assigning WHERE assignmentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, assignmentID);
            int deleted = ps.executeUpdate();

            if (deleted == 0) {
                sendError(response, "No assignment found with ID: " + assignmentID);
                return;
            }

            // success
            response.sendRedirect("assigning.html");
        } catch (SQLException ex) {
            throw new ServletException("Database delete error", ex);
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!doctype html><html><head><meta charset='utf-8'><title>Delete Error</title>");
            out.println("<style>body{font-family:'Segoe UI',Tahoma,Arial,sans-serif;background:#f7f7f7;padding:40px;color:#333}.card{max-width:700px;margin:auto;background:#fff;padding:24px;border-radius:8px;box-shadow:0 6px 20px rgba(0,0,0,.06);text-align:center}.err{color:#d93025}</style>");
            out.println("</head><body><div class='card'><h2 class='err'>Delete failed</h2>");
            out.println("<p>" + escapeHtml(message) + "</p>");
            out.println("<p><a href='deleteAssign.html'>🔙 Back</a> · <a href='assigning.html'>Assigning Menu</a></p>");
            out.println("</div></body></html>");
        }
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'", "&#x27;");
    }
}
