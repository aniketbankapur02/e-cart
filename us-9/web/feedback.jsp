<html>
<head><title>Give Feedback</title></head>
<body>
<h2>Give Feedback</h2>
<form action="feedback" method="post">
    <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>" />
    <label for="description">Feedback Description:</label><br/>
    <textarea name="description" id="description" required></textarea><br/>
    <label for="rating">Rating (1-5):</label><br/>
    <input type="number" name="rating" id="rating" min="1" max="5" required /><br/>
    <button type="submit">Submit Feedback</button>
</form>
</body>
</html> 