<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Product" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Select Items</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 10px;
        }
        h2 {
            color: #333;
            text-align: center;
        }
        p {
            text-align: center;
            color: #555;
        }
        .message {
            color: green;
            text-align: center;
            font-weight: bold;
        }
        .error {
            color: red;
            text-align: center;
            font-weight: bold;
        }
        form {
            margin-top: 20px;
            text-align: center;
        }
        form input[type="radio"] {
            margin-right: 10px;
        }
        form input[type="submit"] {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        form input[type="submit"]:hover {
            background-color: #218838;
        }
        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Select Item to Add</h2>

    <p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>

    <%-- Visa meddelanden --%>
    <%
        String message = request.getParameter("message");
        String error = request.getParameter("error");
        if (message != null) {
    %>
    <p class="message"><%= message %></p>
    <%
    } else if (error != null) {
    %>
    <p class="error"><%= error %></p>
    <%
        }
    %>

    <%
        List<Product> items = (List<Product>) session.getAttribute("products");
        if (items != null && !items.isEmpty()) {
    %>
    <form action="addProduct" method="post">
        <% for (Product item : items) { %>
        <div>
            <input type="radio" name="productId" value="<%= item.getId() %>" id="Product_<%= item.getId() %>_<%= item.getId() %>" />
            <label for="Product_<%= item.getId() %>"><%= item.getName() %></label>
        </div>
        <% } %>
        <input type="submit" value="Add Selected Product" />
    </form>
    <%
    } else {
    %>
    <p>No items available.</p>
    <%
        }
    %>

    <a href="dashboard.jsp">Back to Menu</a>
</div>
</body>
</html>
