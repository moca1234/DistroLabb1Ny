package ui;

import bo.Product;
import bo.UserHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ArrayList<Product> products = UserHandler.getAllPoducts();
        session.setAttribute("products", products);

        RequestDispatcher dispatcher = request.getRequestDispatcher("addItem.jsp");
        dispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String productIdParam = request.getParameter("productId");
        if (productIdParam == null || productIdParam.isEmpty()) {
            response.sendRedirect("addItem.jsp?error=No product selected");
            return;
        }
        int userId = UserHandler.getUserByUsername(username).getId();
        int productId = Integer.parseInt(request.getParameter("productId"));

        boolean success = UserHandler.addProduct(userId, productId);

        if (success) {
            response.sendRedirect("addItem.jsp?message=Product added successfully");
        } else {
            response.sendRedirect("addItem.jsp?error=Could not add product");
        }
    }
}