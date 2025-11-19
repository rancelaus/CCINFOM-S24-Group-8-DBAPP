<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Supplier" %>
<html>
<head>
    <title>Existing Supplier Records</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #333; padding: 6px; }
        th { background: #eee; }
        .container { max-width: 960px; margin: 40px auto; padding: 20px; background:#fff; border-radius:6px; box-shadow:0 4px 12px rgba(0,0,0,0.05);}    
        
        /* 1. Global Styling & Font */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
            color: #333;
        }

        /* 2. Main Container Styling */
        .container {
            width: 80%;
            max-width: 600px;
            margin: 100px auto;
            padding: 30px;
            background-color: #ffffff; 
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); 
            text-align: center;
        }

        /* 3. Title Styling */
        h1 {
            color: #1a73e8;
            font-size: 1.8em;
            margin-bottom: 30px;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }
        
        /* 4. Subheader Styling */
        h2 {
            font-size: 1.3em; 
            font-weight: 500; 
            color: #000000; 
            margin-top: 20px;
            margin-bottom: 15px; 
        }

        /* 5. Link Styling */
        .menu a {
            display: block; 
            text-decoration: none;
            color: #333; 
            padding: 12px 0;
            margin: 5px 0;
            border-radius: 4px;
            transition: background-color 0.2s, color 0.2s; 
        }

        /* 6. Hover Effect */
        .menu a:hover {
            background-color: #e6f0ff; 
            color: #1a73e8; 
        }
        
        form {
            text-align: left;
            display: flex;
            flex-direction: column;
        }

        input[type="text"],
        input[type="email"],
        input[type="submit"] {
            margin-bottom: 10px;
            width: 590px; /* optional: controls input width */
        }

    </style>
</head>
<body>
<div class="container">
    <h1 style="text-align:center;">Existing Supplier Records</h1>

    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <%-- If this page was opened directly (not via servlet), show a link to load the list --%>
    <% if (request.getAttribute("suppliers") == null) { %>
        <p style="color: #666;">No data loaded — <a href="ReadSupplierServlet">Load suppliers</a> (click this to call the servlet).</p>
    <% } %>

    <% List<Supplier> suppliers = (List<Supplier>) request.getAttribute("suppliers"); %>

    <table>
        <tr>
            <th>ID</th><th>Company</th><th>First</th><th>Last</th><th>Contact</th><th>Email</th><th>Street</th><th>City</th><th>ZIP</th>
        </tr>
        <% if (suppliers != null && !suppliers.isEmpty()) {
               for (Supplier s : suppliers) {
        %>
        <tr>
            <td><%= s.getSupplierID() %></td>
            <td><%= s.getCompanyName() %></td>
            <td><%= s.getFirstName() %></td>
            <td><%= s.getLastName() %></td>
            <td><%= s.getContactNumber() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getStreet() %></td>
            <td><%= s.getCity() %></td>
            <td><%= s.getZIP() %></td>
        </tr>
        <%   }
           } else { %>
        <tr><td colspan="9">No suppliers found.</td></tr>
        <% } %>
    </table>
    
    <nav class="menu">
                    <a href="supplier.html">🔙 Back</a>
                    <a href="index.html">🏠 Main Menu</a>

</div>
</body>
</html>
