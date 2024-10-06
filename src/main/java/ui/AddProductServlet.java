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
        // Hämta username från sessionen
        HttpSession session = request.getSession();

        // Hämta alla objekt
        ArrayList<Product> products = UserHandler.getAllPoducts();
        session.setAttribute("products", products);

        // Skicka vidare till addItem.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("addItem.jsp");
        dispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        // Hämta det valda itemId
        String productIdParam = request.getParameter("productId");
        // Kontrollera att itemIdParam inte är null eller tom
        if (productIdParam == null || productIdParam.isEmpty()) {
            // Om inget item valdes, omdirigera tillbaka med ett felmeddelande
            response.sendRedirect("addItem.jsp?error=No product selected");
            return; // Avbryt vidare bearbetning
        }
        int userId = UserHandler.getUserByUsername(username).getId();
        int productId = Integer.parseInt(request.getParameter("productId"));

        // Lägg till objektet
        boolean success = UserHandler.addProduct(userId, productId);

        // Redirecta tillbaka till addItem.jsp med meddelande
        if (success) {
            response.sendRedirect("addItem.jsp?message=Product added successfully");
        } else {
            response.sendRedirect("addItem.jsp?error=Could not add product");
        }
    }
}