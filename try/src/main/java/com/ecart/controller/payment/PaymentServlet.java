package com.ecart.controller.payment;

import com.ecart.dao.CartDAO;
import com.ecart.dao.OrderDAO;
import com.ecart.dao.UserDAO;
import com.ecart.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/checkout")
public class PaymentServlet extends HttpServlet {
    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();
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
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        
        if (cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/customer/cart");
            return;
        }

        double totalAmount = cartDAO.getCartTotal(userId);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalAmount", totalAmount);
        request.getRequestDispatcher("/customer/payment.jsp").forward(request, response);
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
        String paymentMode = request.getParameter("paymentMode");
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        double totalAmount = cartDAO.getCartTotal(userId);

        if (cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/customer/cart");
            return;
        }

        // Create order
        Order order = orderDAO.createOrder(userId, cartItems, totalAmount);
        if (order == null) {
            request.setAttribute("error", "Failed to create order. Please try again.");
            doGet(request, response);
            return;
        }

        // Process payment
        Payment payment;
        String validationError = null;

        if ("CREDIT_CARD".equals(paymentMode)) {
            String cardNumber = request.getParameter("cardNumber");
            String cardHolderName = request.getParameter("cardHolderName");
            String expiryDate = request.getParameter("expiryDate");
            String cvvCode = request.getParameter("cvvCode");

            payment = new Payment(order.getOrderId(), userId, totalAmount,
                    cardNumber, cardHolderName, expiryDate, cvvCode);

            if (!payment.isValidCardNumber()) {
                validationError = "Invalid card number. Must be 16 digits.";
            } else if (!payment.isValidCardHolderName()) {
                validationError = "Card holder name must be at least 10 characters.";
            } else if (!payment.isValidExpiryDate()) {
                validationError = "Invalid expiry date. Use MM/YY format.";
            } else if (!payment.isValidCvvCode()) {
                validationError = "Invalid CVV code. Must be 4 digits.";
            }
        } else if ("UPI".equals(paymentMode)) {
            String upiId = request.getParameter("upiId");
            payment = new Payment(order.getOrderId(), userId, totalAmount, upiId);

            if (!payment.isValidUpiId()) {
                validationError = "Invalid UPI ID format.";
            }
        } else {
            validationError = "Invalid payment mode selected.";
            payment = null;
        }

        if (validationError != null) {
            request.setAttribute("error", validationError);
            doGet(request, response);
            return;
        }

        // Process the payment
        String transactionId = orderDAO.processPayment(payment);
        if (transactionId == null) {
            request.setAttribute("error", "Payment processing failed. Please try again.");
            doGet(request, response);
            return;
        }

        // Update order with payment details
        order.setPaymentMode(paymentMode);
        order.setTransactionId(transactionId);
        order.setStatus("CONFIRMED");

        // Create invoice
        Invoice invoice = orderDAO.createInvoice(order, transactionId);
        if (invoice == null) {
            request.setAttribute("error", "Failed to generate invoice. Please contact support.");
            doGet(request, response);
            return;
        }

        // Clear the cart
        cartDAO.clearCart(userId);

        // Add user details to invoice for display
        User user = userDAO.getUserById(userId);
        invoice.setCustomerName(user.getName());
        invoice.setCustomerEmail(user.getEmail());
        invoice.setCustomerPhone(user.getPhone());
        invoice.setCustomerAddress(user.getAddress1() + 
            (user.getAddress2() != null ? ", " + user.getAddress2() : "") +
            ", " + user.getCity() + ", " + user.getState() + " - " + user.getZipcode());

        // Set success attributes for confirmation page
        request.setAttribute("invoice", invoice);
        request.getRequestDispatcher("/customer/payment-success.jsp").forward(request, response);
    }
}
