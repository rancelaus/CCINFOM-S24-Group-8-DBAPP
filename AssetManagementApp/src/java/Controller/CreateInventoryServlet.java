package Controller;

import Utilities.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ranseeeee
 */
@WebServlet("/CreateInventoryServlet")
public class CreateInventoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        String itemType = request.getParameter("itemType");
        String unit = request.getParameter("unit");
        int stockQty = Integer.parseInt(request.getParameter("stockQty"));
        int reorderLevel = Integer.parseInt(request.getParameter("reorderLevel"));
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Inventory (itemName, itemType, unit, stockQty, reorderLevel, i_status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            
            stm.setString(1, itemName);
            stm.setString(2, itemType);
            stm.setString(3, unit);
            stm.setInt(4, stockQty);
            stm.setInt(5, reorderLevel);
            stm.setString(6, "active");
            
            stm.executeUpdate();
            
            response.sendRedirect("inventory.html");
        } catch (SQLException e) {
            throw new ServletException("DB Insert Error", e);
        }
    }
}