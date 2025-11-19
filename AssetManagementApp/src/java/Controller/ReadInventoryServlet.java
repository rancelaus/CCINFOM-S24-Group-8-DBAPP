package Controller;

import Utilities.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
@WebServlet("/ReadInventoryServlet")
public class ReadInventoryServlet extends HttpServlet {

    // small helper to escape output for HTML to avoid XSS
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("itemID");
        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // page start + styling (keeps your site look)
            out.println("<!doctype html><html><head><meta charset='utf-8'><title>Inventory Record</title>");
            out.println("<style>");
            out.println("body{font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;background:#f7f7f7;margin:0;padding:0;color:#333}");
            out.println(".container{width:80%;max-width:700px;margin:80px auto;padding:28px;background:#fff;border-radius:8px;box-shadow:0 6px 20px rgba(0,0,0,0.06);text-align:center}");
            out.println("h1{color:#1a73e8;font-size:1.8rem;margin-bottom:18px;border-bottom:1px solid #eee;padding-bottom:10px}");
            out.println(".kv{ text-align:left; max-width:560px; margin:12px auto; }");
            out.println(".kv .row{ display:flex; justify-content:space-between; padding:10px 12px; border-bottom:1px solid #f0f0f0 }");
            out.println(".kv .label{ font-weight:600; color:#333 }");
            out.println(".kv .value{ color:#555 }");
            out.println("a.button{display:inline-block;margin-top:18px;padding:10px 16px;background:#1a73e8;color:#fff;border-radius:6px;text-decoration:none}");
            out.println("</style></head><body><div class='container'>");
            out.println("<h1>Inventory Lookup</h1>");

            if (idParam == null || idParam.trim().isEmpty()) {
                out.println("<p>Please provide an Inventory ID. <a href='inventory.html'>Back</a></p>");
                out.println("</div></body></html>");
                return;
            }

            int itemID;
            try {
                itemID = Integer.parseInt(idParam.trim());
            } catch (NumberFormatException nfe) {
                out.println("<p>Invalid Inventory ID format. <a href='inventory.html'>Back</a></p>");
                out.println("</div></body></html>");
                return;
            }

            String sql = "SELECT * FROM Inventory WHERE itemID = ?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, itemID);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        out.println("<p>No inventory found with ID: <strong>" + esc(String.valueOf(itemID)) + "</strong></p>");
                        out.println("<p><a href='inventory.html'>🔙 Back</a></p>");
                        out.println("</div></body></html>");
                        return;
                    }

                    // Print all columns dynamically (so it will show whatever columns exist)
                    ResultSetMetaData md = rs.getMetaData();
                    int cols = md.getColumnCount();

                    out.println("<div class='kv'>");
                    out.println("<h2>Inventory ID: " + esc(String.valueOf(itemID)) + "</h2>");
                    for (int i = 1; i <= cols; i++) {
                        String colName = md.getColumnLabel(i);
                        String val = rs.getString(i);
                        out.println("<div class='row'><div class='label'>" + esc(colName) + "</div><div class='value'>" + esc(val) + "</div></div>");
                    }
                    out.println("</div>");

                    out.println("<p><a class='button' href='inventory.html'>Back to Inventory Menu</a> <a style='margin-left:12px' href='index.html'>Main Menu</a></p>");
                }

            } catch (SQLException ex) {
                out.println("<h2 style='color:#d93025'>Database error</h2>");
                out.println("<pre style='color:darkred'>" + esc(ex.getMessage()) + "</pre>");
                out.println("<p><a href='inventory.html'>Back</a></p>");
            }

            out.println("</div></body></html>");
        }
    }
}