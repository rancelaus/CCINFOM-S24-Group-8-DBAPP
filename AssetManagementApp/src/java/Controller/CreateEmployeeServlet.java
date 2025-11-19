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

@WebServlet("/CreateEmployeeServlet")
public class CreateEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                        throws ServletException, IOException
    {
        // read parameters (names match your HTML form's name attributes)
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String department = request.getParameter("department");
        String position   = request.getParameter("position");
        String contact    = request.getParameter("contactNumber");
        String email      = request.getParameter("email");

        // basic server-side sanity check (optional)
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "First and last name are required.");
            return;
        }

        String sql = "INSERT INTO Employee (firstName, lastName, department, position, contactNumber, email) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName.trim());
            ps.setString(2, lastName.trim());
            ps.setString(3, department == null ? null : department.trim());
            ps.setString(4, position == null ? null : position.trim());
            ps.setString(5, contact == null ? null : contact.trim());
            ps.setString(6, email == null ? null : email.trim());

            ps.executeUpdate();

            // redirect back to employee menu or wherever you want
            response.sendRedirect("employee.html");

        } catch (SQLException e) {
            throw new ServletException("DB Insert Error", e);
        }
    }
}
