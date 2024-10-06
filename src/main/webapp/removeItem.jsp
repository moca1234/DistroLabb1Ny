<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Product" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Remove Product</title>
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
            max-width: 500px;
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

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        input[type="radio"] {
            margin: 10px 0;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
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
    <h2>Select product to remove</h2>

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

    <form action="removeProduct" method="post">
        <% for (Product product : products) { %>
        <input type="radio" name="productId" value="<%= product.getId() %>" />
        <%= product.getName() %><br>
        <% } %>
        <input type="submit" value="Remove Selected Product" />
    </form>

    <%
    } else {
    %>
    <p>No products available.</p>
    <%
        }
    %>

    <a href="dashboard.jsp">Back to Menu</a>
</div>
</body>
</html>
