package com.ecart.controller.user;

import com.ecart.dao.ProductDAO;
import com.ecart.model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/home")
public class HomeServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
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

        // Get search parameters
        String searchKeyword = request.getParameter("search");
        String category = request.getParameter("category");
        List<Product> products;

        // Get products based on search criteria
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            products = productDAO.searchProducts(searchKeyword);
        } else if (category != null && !category.trim().isEmpty()) {
            products = productDAO.getProductsByCategory(category);
        } else {
            products = productDAO.getAllProducts();
        }

        // Get all categories for the filter dropdown
        List<String> categories = productDAO.getAllCategories();

        // Set attributes for the JSP
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("searchKeyword", searchKeyword);

        // Forward to the home page
        request.getRequestDispatcher("/customer/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // For any POST requests (e.g., adding to cart), redirect to the cart servlet
        response.sendRedirect(request.getContextPath() + "/customer/cart/add?productId=" 
            + request.getParameter("productId"));
    }
}
