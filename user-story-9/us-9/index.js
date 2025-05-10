document.addEventListener('DOMContentLoaded', function() {
  // Sample data - in a real app, this would come from the backend
  const urlParams = new URLSearchParams(window.location.search);
  const orderId = urlParams.get('orderId') || '12345';
  const deliveryDate = urlParams.get('deliveryDate') || 'May 8, 2025';
  
  // Update the order information
  document.getElementById('orderNumber').textContent = orderId;
  document.getElementById('deliveryDate').textContent = deliveryDate;
  
  // Setup star rating
  const stars = document.querySelectorAll('.star');
  const ratingInput = document.getElementById('rating');
  const ratingDescription = document.getElementById('ratingDescription');
  const ratingTexts = [
    'Select a rating',
    'Poor',
    'Fair',
    'Good',
    'Very Good',
    'Excellent'
  ];
  
  // Handle star selection
  stars.forEach(star => {
    star.addEventListener('click', function() {
      const value = parseInt(this.getAttribute('data-value'));
      ratingInput.value = value;
      updateStars(value);
      ratingDescription.textContent = ratingTexts[value];
    });
    
    star.addEventListener('mouseover', function() {
      const value = parseInt(this.getAttribute('data-value'));
      
      // Temporarily highlight stars on hover
      stars.forEach((s, index) => {
        if (index < value) {
          s.classList.add('active');
        } else {
          s.classList.remove('active');
        }
      });
    });
  });
  
  // Reset stars to selected value when mouse leaves the container
  document.querySelector('.stars').addEventListener('mouseleave', function() {
    updateStars(parseInt(ratingInput.value));
  });
  
  function updateStars(value) {
    stars.forEach((star, index) => {
      if (index < value) {
        star.classList.add('active');
      } else {
        star.classList.remove('active');
      }
    });
  }
  
  // Form submission
  const feedbackForm = document.getElementById('feedbackForm');
  feedbackForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const rating = ratingInput.value;
    const feedback = document.getElementById('feedback').value;
    
    // Validate form
    if (rating === '0') {
      alert('Please select a rating.');
      return;
    }
    
    if (!feedback.trim()) {
      alert('Please provide feedback.');
      return;
    }
    
    // Here you would normally send data to the backend
    console.log('Submitting feedback:', {
      orderId: orderId,
      rating: rating,
      feedback: feedback
    });
    
    // Show success page
    document.querySelector('.container').innerHTML = `
      <div class="success-page">
        <div class="success-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="#22c55e" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <h1 class="success-title">Thank you for your feedback!</h1>
        <p class="success-message">Your feedback for order <strong>${orderId}</strong> has been submitted successfully.</p>
        <button id="backToOrdersBtn" class="back-button">Back to Orders</button>
      </div>
    `;
    
    // Add event listener to the new back button
    document.getElementById('backToOrdersBtn').addEventListener('click', function() {
      // In a real application, this would redirect to the orders page
      window.history.back();
    });
  });
  
  // Back to orders button
  document.getElementById('backToOrders').addEventListener('click', function(e) {
    e.preventDefault();
    window.history.back();
  });
});