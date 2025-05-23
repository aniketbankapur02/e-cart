package com.ecart.controller.cart;

import com.ecart.dao.CartDAO;
import com.ecart.dao.ProductDAO;
import com.ecart.model.CartItem;
import com.ecart.model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/cart/*")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAO();
        productDAO = new ProductDAO();
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
        double total = cartDAO.getCartTotal(userId);
        
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", total);
        request.getRequestDispatcher("/customer/cart.jsp").forward(request, response);
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
        String action = request.getPathInfo();
        String cartItemId = request.getParameter("cartItemId");
        String productId = request.getParameter("productId");
        
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (action) {
            case "/add":
                handleAddToCart(request, response, userId, productId);
                break;
            case "/update":
                handleUpdateCart(request, response, cartItemId);
                break;
            case "/delete":
                handleDeleteFromCart(request, response, cartItemId);
                break;
            case "/remove":
                handleRemoveFromCart(request, response, cartItemId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response,
                               String userId, String productId)
            throws ServletException, IOException {
        
        Product product = productDAO.getProductById(productId);
        if (product == null || product.getQuantity() < 1) {
            request.setAttribute("error", "Product not available");
            doGet(request, response);
            return;
        }

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartDAO.generateCartItemId(userId));
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setProductName(product.getName());
        cartItem.setProductPrice(product.getPrice());
        cartItem.setQuantity(1); // Default quantity when adding to cart

        if (cartDAO.addToCart(cartItem)) {
            response.sendRedirect(request.getContextPath() + "/customer/cart");
        } else {
            request.setAttribute("error", "Failed to add item to cart");
            doGet(request, response);
        }
    }

    private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response,
                                String cartItemId)
            throws ServletException, IOException {
        
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (quantity < 1) {
            request.setAttribute("error", "Quantity must be at least 1");
            doGet(request, response);
            return;
        }

        if (cartDAO.updateQuantity(cartItemId, quantity)) {
            response.sendRedirect(request.getContextPath() + "/customer/cart");
        } else {
            request.setAttribute("error", "Failed to update cart");
            doGet(request, response);
        }
    }

    private void handleDeleteFromCart(HttpServletRequest request, HttpServletResponse response,
                                    String cartItemId)
            throws ServletException, IOException {
        
        if (cartDAO.updateQuantity(cartItemId, 0)) {
            response.sendRedirect(request.getContextPath() + "/customer/cart");
        } else {
            request.setAttribute("error", "Failed to delete item from cart");
            doGet(request, response);
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response,
                                    String cartItemId)
            throws ServletException, IOException {
        
        if (cartDAO.removeFromCart(cartItemId)) {
            response.sendRedirect(request.getContextPath() + "/customer/cart");
        } else {
            request.setAttribute("error", "Failed to remove item from cart");
            doGet(request, response);
        }
    }
}
