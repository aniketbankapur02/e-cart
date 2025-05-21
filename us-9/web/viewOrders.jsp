<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Order" %>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
%>
<html>
<head><title>All Orders</title></head>
<body>
<h2>All Orders</h2>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Customer</th>
        <th>Status</th>
        <th>Feedback</th>
    </tr>
    <% for (Order order : orders) { %>
    <tr>
        <td><%= order.getId() %></td>
        <td><%= order.getCustomer() %></td>
        <td><%= order.getStatus() %></td>
        <td>
            <% if ("delivered".equals(order.getStatus()) && order.getFeedbackDescription() == null) { %>
                <form action="feedback" method="get">
                    <input type="hidden" name="orderId" value="<%= order.getId() %>" />
                    <button type="submit">Give Feedback</button>
                </form>
            <% } else if (order.getFeedbackDescription() != null) { %>
                <b>Rated:</b> <%= order.getFeedbackRating() %> <br/>
                <b>Feedback:</b> <%= order.getFeedbackDescription() %>
            <% } else { %>
                N/A
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html> 