package com.example.servlet;

import com.example.model.Order;
import com.example.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("customer"),
                        rs.getString("status"),
                        rs.getString("feedbackDescription"),
                        (Integer)rs.getObject("feedbackRating")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/viewOrders.jsp").forward(req, resp);
    }
} 