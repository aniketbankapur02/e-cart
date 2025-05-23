package com.ecart.controller.user;

import com.ecart.dao.UserDAO;
import com.ecart.model.User;
import com.ecart.util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/customer/profile")
public class ProfileServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        User user = userDAO.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate inputs
        if (!validateInputs(request, name, email, phone, address1)) {
            return;
        }

        User user = userDAO.getUserById(userId);
        if (user == null) {
            request.setAttribute("error", "User not found");
            doGet(request, response);
            return;
        }

        // Update basic details
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress1(address1);
        user.setAddress2(address2);

        // Handle password update if requested
        if (currentPassword != null && !currentPassword.isEmpty()) {
            if (!validatePassword(request, user, currentPassword, newPassword, confirmPassword)) {
                doGet(request, response);
                return;
            }
            user.setPassword(encryptPassword(newPassword));
        }

        // Update user
        if (userDAO.updateUser(user)) {
            session.setAttribute("userName", user.getName());
            request.setAttribute("success", "Profile updated successfully");
        } else {
            request.setAttribute("error", "Failed to update profile. Please try again.");
        }

        doGet(request, response);
    }

    private boolean validateInputs(HttpServletRequest request, String name, String email, 
                                 String phone, String address1) {
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Name is required");
            return false;
        }
        if (name.length() > 50) {
            request.setAttribute("error", "Name should not exceed 50 characters");
            return false;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format");
            return false;
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("error", "Invalid phone number");
            return false;
        }
        if (address1 == null || address1.trim().isEmpty()) {
            request.setAttribute("error", "Address Line 1 is required");
            return false;
        }
        return true;
    }

    private boolean validatePassword(HttpServletRequest request, User user, String currentPassword, 
                                   String newPassword, String confirmPassword) {
        if (!user.getPassword().equals(encryptPassword(currentPassword))) {
            request.setAttribute("error", "Current password is incorrect");
            return false;
        }
        if (newPassword == null || newPassword.length() < 10) {
            request.setAttribute("error", "New password must be at least 10 characters long");
            return false;
        }
        if (!ValidationUtil.isValidPassword(newPassword)) {
            request.setAttribute("error", 
                "Password must contain at least one uppercase letter, one lowercase letter, " +
                "one number and one special character");
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match");
            return false;
        }
        return true;
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
