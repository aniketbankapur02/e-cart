package com.ecart.controller.user;

import com.ecart.dao.UserDAO;
import com.ecart.model.User;
import com.ecart.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String country = request.getParameter("country");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String zipcode = request.getParameter("zipcode");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate form data
        String validationError = ValidationUtil.validateRegistrationForm(
            name, email, phone, password, confirmPassword, zipcode
        );

        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        if (userDAO.isEmailExists(email)) {
            request.setAttribute("error", "Email already registered");
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
            return;
        }

        // Create new user
        User user = new User();
        user.setUserId(userDAO.generateUserId());
        user.setName(name);
        user.setCountry(country);
        user.setState(state);
        user.setCity(city);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setZipcode(zipcode);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(ValidationUtil.encryptPassword(password));
        user.setRole("CUSTOMER");

        // Register user
        if (userDAO.registerUser(user)) {
            request.setAttribute("success", "Registration successful! Your Customer ID is: " + user.getUserId());
            request.setAttribute("userId", user.getUserId());
            request.getRequestDispatcher("/customer/registration-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
    }
}
