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

@WebServlet("/CreateSupplierServlet")
public class CreateSupplierServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                        throws ServletException, IOException
    {
        String company = request.getParameter("companyName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");

        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "INSERT INTO Supplier (companyName, firstName, lastName, contactNumber, email, zip, street, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            
            stm.setString(1, company);
            stm.setString(2, firstName);
            stm.setString(3, lastName);
            stm.setString(4, contact);
            stm.setString(5, email);
             stm.setString(6, zip);
            stm.setString(7, street);
            stm.setString(8, city);
           
            
            stm.executeUpdate();

            response.sendRedirect("supplier.html");
        } catch (SQLException e) {
            throw new ServletException("DB Insert Error", e);
        }
    }
}

