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
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("employeeID");
        if (idParam == null || idParam.trim().isEmpty()) {
            sendError(response, "Employee ID is required.");
            return;
        }

        int employeeID;
        try {
            employeeID = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException nfe) {
            sendError(response, "Invalid Employee ID format.");
            return;
        }

        // Read all possible update fields
        String firstName = nullIfEmpty(request.getParameter("firstName"));
        String lastName  = nullIfEmpty(request.getParameter("lastName"));
        String department = nullIfEmpty(request.getParameter("department"));
        String position   = nullIfEmpty(request.getParameter("position"));
        String contact    = nullIfEmpty(request.getParameter("contactNumber"));
        String email      = nullIfEmpty(request.getParameter("email"));

        // Build dynamic UPDATE for only non-null fields
        StringBuilder sql = new StringBuilder("UPDATE Employee SET ");
        List<String> cols = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if (firstName != null) { cols.add("firstName = ?"); values.add(firstName); }
        if (lastName  != null) { cols.add("lastName = ?");  values.add(lastName);  }
        if (department!= null) { cols.add("department = ?"); values.add(department); }
        if (position  != null) { cols.add("position = ?");   values.add(position);  }
        if (contact   != null) { cols.add("contactNumber = ?"); values.add(contact); }
        if (email     != null) { cols.add("email = ?");      values.add(email);     }

        if (cols.isEmpty()) {
            sendError(response, "No fields provided to update.");
            return;
        }

        // join columns
        sql.append(String.join(", ", cols));
        sql.append(" WHERE employeeID = ?");

        // execute update
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object v : values) {
                ps.setString(idx++, v == null ? null : v.toString());
            }
            ps.setInt(idx, employeeID);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                // nothing updated — likely no such employee
                sendError(response, "No employee found with ID: " + employeeID);
                return;
            }

            // success — redirect back to employee menu (or wherever you prefer)
            response.sendRedirect("employee.html");
        } catch (SQLException ex) {
            throw new ServletException("Database update error", ex);
        }
    }

    private static String nullIfEmpty(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!doctype html><html><head><meta charset='utf-8'><title>Update Error</title>");
            out.println("<style>body{font-family:Segoe UI,Arial,sans-serif;padding:24px} .err{color:#d93025}</style>");
            out.println("</head><body>");
            out.println("<h2 class='err'>Update failed</h2>");
            out.println("<p>" + escapeHtml(message) + "</p>");
            out.println("<p><a href='updateEmployee.html'>Back to Update Form</a></p>");
            out.println("</body></html>");
        }
    }

    // minimal HTML escape
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }
}

