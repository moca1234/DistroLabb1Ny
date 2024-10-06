package ui;

import bo.UserHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (UserHandler.Login(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Wrong username or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp"); // Återgå till formuläret
            dispatcher.forward(request, response);
        }
    }
}