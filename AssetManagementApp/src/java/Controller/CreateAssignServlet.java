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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreateAssignServlet")
public class CreateAssignServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String emp = request.getParameter("employeeID");   // will be saved to assignedBy
        String asset = request.getParameter("assetID");
        String date = request.getParameter("assignmentDate"); // yyyy-MM-dd
        String status = request.getParameter("status");   // expected "active" or "inactive"

        // basic validation
        if (emp == null || emp.trim().isEmpty() ||
            asset == null || asset.trim().isEmpty() ||
            date == null || date.trim().isEmpty() ||
            status == null || status.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee, Asset, Date, and Status are required.");
            return;
        }

        // Use the actual table name and column names from your DB
        String sql = "INSERT INTO assigning (assetID, assignedBy, assignmentDate, as_status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, Integer.parseInt(asset));
            ps.setInt(2, Integer.parseInt(emp)); // employeeID -> assignedBy
            ps.setDate(3, java.sql.Date.valueOf(date)); // input type=date gives yyyy-mm-dd
            ps.setString(4, status.trim().toLowerCase());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        // optionally: log or use the newId
                    }
                }
            }

            // success: redirect back to assigning menu
            response.sendRedirect("assigning.html");
        } catch (NumberFormatException nfe) {
            throw new ServletException("Invalid numeric input for employeeID or assetID", nfe);
        } catch (SQLException ex) {
            throw new ServletException("Database insert error", ex);
        }
    }
}

