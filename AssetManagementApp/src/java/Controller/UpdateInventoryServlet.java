/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author ranseeeee
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

@WebServlet("/UpdateInventoryServlet")
public class UpdateInventoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("itemID");
        if (idParam == null || idParam.trim().isEmpty()) {
            sendError(response, "Inventory ID is required.");
            return;
        }

        int itemID;
        try {
            itemID = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException nfe) {
            sendError(response, "Invalid Inventory ID format.");
            return;
        }

        // Read all possible update fields
        String itemName = nullIfEmpty(request.getParameter("itemName"));
        String itemType  = nullIfEmpty(request.getParameter("itemType"));
        String unit = nullIfEmpty(request.getParameter("unit"));
        Integer stockQty = nullIfEmpty(request.getParameter("stockQty")) == null 
        ? null 
        : Integer.valueOf(request.getParameter("stockQty"));
        Integer reorderLevel = nullIfEmpty(request.getParameter("reorderLevel")) == null 
        ? null 
        : Integer.valueOf(request.getParameter("reorderLevel"));
        String i_status      = nullIfEmpty(request.getParameter("i_status"));

        // Build dynamic UPDATE for only non-null fields
        StringBuilder sql = new StringBuilder("UPDATE Inventory SET ");
        List<String> cols = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if (itemName != null) { cols.add("itemName = ?"); values.add(itemName); }
        if (itemType  != null) { cols.add("itemType = ?");  values.add(itemType);  }
        if (unit != null) { cols.add("unit = ?"); values.add(unit); }
        if (stockQty  != null) { cols.add("stockQty = ?");   values.add(stockQty);  }
        if (reorderLevel   != null) { cols.add("reorderLevel = ?"); values.add(reorderLevel); }
        if (i_status     != null) { cols.add("i_status = ?");      values.add(i_status);     }

        if (cols.isEmpty()) {
            sendError(response, "No fields provided to update.");
            return;
        }

        // join columns
        sql.append(String.join(", ", cols));
        sql.append(" WHERE itemID = ?");

        // execute update
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object v : values) {
                ps.setString(idx++, v == null ? null : v.toString());
            }
            ps.setInt(idx, itemID);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                // nothing updated — likely no such employee
                sendError(response, "No item found with ID: " + itemID);
                return;
            }

            // success — redirect back to employee menu (or wherever you prefer)
            response.sendRedirect("inventory.html");
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
            out.println("<p><a href='updateInventory.html'>Back to Update Form</a></p>");
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