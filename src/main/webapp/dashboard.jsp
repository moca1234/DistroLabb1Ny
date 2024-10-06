<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        .container {
            width: 100%;
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .welcome-text {
            text-align: center;
            font-size: 18px;
            margin-bottom: 20px;
        }

        .button-container {
            display: flex;
            justify-content: space-around;
            margin-bottom: 20px;
        }

        .button-container form {
            display: inline;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            display: block;
            text-align: center;
            color: #007bff;
            text-decoration: none;
            font-size: 16px;
            margin-top: 20px;
            transition: color 0.3s;
        }

        a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Menu</h2>

    <p class="welcome-text">Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>

    <div class="button-container">
        <form action="addProduct">
            <input type="submit" value="Add Product" />
        </form>
        <form action="removeProduct">
            <input type="submit" value="Remove Product" />
        </form>
        <form action="showMyProducts">
            <input type="submit" value="Show Products" />
        </form>
    </div>

    <a href="index.jsp">Logout</a>
</div>
</body>
</html>
