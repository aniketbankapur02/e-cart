package com.ecart.controller.user;

import com.ecart.dao.UserDAO;
import com.ecart.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // Validate userId and password
        if (userId == null || userId.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Please enter both Customer ID and Password");
            request.getRequestDispatcher("/customer/login.jsp").forward(request, response);
            return;
        }

        // Validate login credentials
        if (userDAO.validateLogin(userId, password)) {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                // Store user information in session
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userRole", user.getRole());
                
                // Redirect to home page
                response.sendRedirect(request.getContextPath() + "/customer/home");
            } else {
                request.setAttribute("error", "System error occurred. Please try again.");
                request.getRequestDispatcher("/customer/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid Customer ID or Password");
            request.getRequestDispatcher("/customer/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            // If user is already logged in, redirect to home page
            response.sendRedirect(request.getContextPath() + "/customer/home");
        } else {
            request.getRequestDispatcher("/customer/login.jsp").forward(request, response);
        }
    }
}
