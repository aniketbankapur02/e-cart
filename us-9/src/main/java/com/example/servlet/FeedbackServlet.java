package com.example.servlet;

import com.example.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Show feedback form
        req.getRequestDispatcher("/feedback.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String description = req.getParameter("description");
        int rating = Integer.parseInt(req.getParameter("rating"));
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO feedback (orderId, description, rating) VALUES (?, ?, ?)");
            ps.setInt(1, orderId);
            ps.setString(2, description);
            ps.setInt(3, rating);
            ps.executeUpdate();
            // Also update order feedback fields
            PreparedStatement ps2 = conn.prepareStatement("UPDATE orders SET feedbackDescription=?, feedbackRating=? WHERE id=?");
            ps2.setString(1, description);
            ps2.setInt(2, rating);
            ps2.setInt(3, orderId);
            ps2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("orders");
    }
} 