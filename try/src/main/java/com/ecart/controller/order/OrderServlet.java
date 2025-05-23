package com.ecart.controller.order;

import com.ecart.dao.OrderDAO;
import com.ecart.model.Order;
import com.ecart.model.OrderCancellation;
import com.ecart.model.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/customer/orders/*")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
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
        String statusFilter = request.getParameter("status");
        List<Order> orders;

        if (statusFilter != null && !statusFilter.isEmpty()) {
            orders = orderDAO.getOrdersByStatus(userId, statusFilter);
        } else {
            orders = orderDAO.getOrdersByCustomerId(userId);
        }

        request.setAttribute("orders", orders);
        request.setAttribute("orderStatuses", OrderStatus.values());
        request.setAttribute("selectedStatus", statusFilter);
        request.getRequestDispatcher("/customer/orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Handle order cancellation
        if (pathInfo.equals("/cancel")) {
            handleOrderCancellation(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void handleOrderCancellation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String orderId = request.getParameter("orderId");
        String reason = request.getParameter("reason");
        String confirmCancel = request.getParameter("confirm");

        if (orderId == null || orderId.trim().isEmpty()) {
            request.setAttribute("error", "Order ID is required");
            doGet(request, response);
            return;
        }

        // Get the order details
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            request.setAttribute("error", "Order not found");
            doGet(request, response);
            return;
        }

        // Verify order belongs to the logged-in user
        String userId = (String) request.getSession().getAttribute("userId");
        if (!order.getCustomerId().equals(userId)) {
            request.setAttribute("error", "Unauthorized access to order");
            doGet(request, response);
            return;
        }

        // Check if order can be cancelled
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            request.setAttribute("error", 
                "Only orders in 'Confirmed' status can be cancelled. Current status: " + 
                order.getStatus().getDisplayName());
            doGet(request, response);
            return;
        }

        // If confirmation is not yet given, show confirmation page
        if (confirmCancel == null || !confirmCancel.equals("yes")) {
            request.setAttribute("orderToCancel", order);
            request.getRequestDispatcher("/customer/confirm-cancellation.jsp").forward(request, response);
            return;
        }

        // Process cancellation
        if (reason == null || reason.trim().isEmpty()) {
            request.setAttribute("error", "Cancellation reason is required");
            request.setAttribute("orderToCancel", order);
            request.getRequestDispatcher("/customer/confirm-cancellation.jsp").forward(request, response);
            return;
        }

        OrderCancellation cancellation = new OrderCancellation(
            orderId,
            reason,
            new Timestamp(System.currentTimeMillis()),
            order.getTotalAmount()
        );

        boolean cancelled = orderDAO.cancelOrder(cancellation);
        if (cancelled) {
            request.setAttribute("success", "Order cancelled successfully");
            request.setAttribute("cancellation", cancellation);
            request.getRequestDispatcher("/customer/cancellation-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to cancel order. Please try again.");
            doGet(request, response);
        }
    }
}
