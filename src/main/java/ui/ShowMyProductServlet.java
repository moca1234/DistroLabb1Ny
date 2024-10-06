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


@WebServlet("/showMyProducts")
public class ShowMyProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Hämta alla objekt
        String username = (String) session.getAttribute("username");
        ArrayList<Product> MyProducts = UserHandler.getMyProducts(username);
        session.setAttribute("myProducts", MyProducts);
        // Skicka vidare till removeItem.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("showMyItems.jsp");
        dispatcher.forward(request, response);
    }
}