<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Product" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Your Products</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        p {
            text-align: center;
            font-size: 16px;
        }

        .message {
            text-align: center;
            color: green;
            margin-bottom: 10px;
        }

        .error {
            text-align: center;
            color: red;
            margin-bottom: 10px;
        }

        ol {
            padding-left: 20px;
        }

        li {
            background-color: #f9f9f9;
            margin: 10px 0;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
            font-size: 16px;
            transition: color 0.3s;
        }

        a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Your Products</h2>
    <p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>

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
        List<Product> products = (List<Product>) session.getAttribute("myProducts");
        if (products != null && !products.isEmpty()) {
    %>
    <ol>
        <% for (Product product : products) { %>
        <li>
            <!-- Visa specifik information om produkten -->
            <strong>Product Name:</strong> <%= product.getName() %><br>
            <strong>Price:</strong> <%= product.getPrice() %> SEK<br>
            <!-- Lägg till fler fält om det behövs -->
        </li>
        <% } %>
    </ol>
    <%
    } else {
    %>
    <p>No products available.</p>
    <%
        }
    %>

    <a href="dashboard.jsp">Back to Dashboard</a>
</div>
</body>
</html>
