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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAssignServlet")
public class UpdateAssignServlet extends HttpServlet {

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;")
                .replace(">","&gt;").replace("\"","&quot;");
    }

    private String nullIfEmpty(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("assignmentID");
        String emp = nullIfEmpty(request.getParameter("employeeID"));   // assignedBy
        String asset = nullIfEmpty(request.getParameter("assetID"));    // assetID
        String date = nullIfEmpty(request.getParameter("assignmentDate")); // assignmentDate
        String status = nullIfEmpty(request.getParameter("status"));    // as_status

        response.setContentType("text/html; charset=UTF-8");

        // validate ID
        if (idParam == null || idParam.trim().isEmpty()) {
            sendError(response, "Assignment ID is required.");
            return;
        }

        int assignmentID;
        try {
            assignmentID = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            sendError(response, "Invalid Assignment ID.");
            return;
        }

        // build update dynamically
        List<String> cols = new ArrayList<>();
        List<Object> vals = new ArrayList<>();

        if (emp != null) { cols.add("assignedBy = ?"); vals.add(Integer.parseInt(emp)); }
        if (asset != null) { cols.add("assetID = ?"); vals.add(Integer.parseInt(asset)); }
        if (date != null) { cols.add("assignmentDate = ?"); vals.add(Date.valueOf(date)); }
        if (status != null) { cols.add("as_status = ?"); vals.add(status.toLowerCase()); }

        if (cols.isEmpty()) {
            sendError(response, "No fields were provided to update.");
            return;
        }

        String sql = "UPDATE assigning SET " + String.join(", ", cols) + " WHERE assignmentID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int idx = 1;
            for (Object v : vals) {
                if (v instanceof Integer) ps.setInt(idx++, (Integer)v);
                else if (v instanceof Date) ps.setDate(idx++, (Date)v);
                else ps.setString(idx++, v.toString());
            }

            ps.setInt(idx, assignmentID);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                sendError(response, "No assignment found with ID: " + assignmentID);
                return;
            }

            // success — redirect or show a success message
            response.sendRedirect("assigning.html");

        } catch (SQLException e) {
            throw new ServletException("Database update error", e);
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();

        out.println("<!doctype html><html><head><meta charset='utf-8'><title>Update Error</title>");
        out.println("<style>");
        out.println("body{font-family:'Segoe UI',Tahoma;background:#f7f7f7;padding:40px;color:#333}");
        out.println(".card{max-width:600px;margin:auto;background:#fff;padding:24px;border-radius:8px;"
                + "box-shadow:0 6px 20px rgba(0,0,0,0.06);text-align:center}");
        out.println("</style></head><body>");

        out.println("<div class='card'>");
        out.println("<h2 style='color:#d93025'>Update Failed</h2>");
        out.println("<p>" + esc(message) + "</p>");
        out.println("<p><a href='updateAssign.html'>🔙 Back to Update</a></p>");
        out.println("</div></body></html>");
    }
}

