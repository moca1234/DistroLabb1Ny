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


@WebServlet("/removeProduct")
public class RemoveItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        // Hämta alla objekt
        ArrayList<Product> MyProducts = UserHandler.getMyProducts(username);
        session.setAttribute("myProducts", MyProducts);
        // Skicka vidare till removeItem.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("removeItem.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int userId = UserHandler.getUserByUsername(username).getId();
        int productId = Integer.parseInt(request.getParameter("productId"));
        // Ta bort objektet
        boolean success = UserHandler.removeProducts(userId, productId);
        // Redirecta tillbaka till removeItem.jsp med meddelande
        if (success) {
            response.sendRedirect("removeItem.jsp?message=Product removed successfully");
        } else {
            response.sendRedirect("removeItem.jsp?error=Could not remove product");
        }
    }
}