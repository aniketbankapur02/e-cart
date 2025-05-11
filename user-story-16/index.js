// Mock product data for demonstration
const mockProducts = [
    {
        id: 101,
        name: "Wireless Headphones",
        price: 1299,
        category: "Electronics",
        description: "High-quality wireless headphones with noise cancellation",
        quantity: 15
    },
    {
        id: 102,
        name: "Smart Watch",
        price: 2499,
        category: "Electronics",
        description: "Fitness tracking smart watch with heart rate monitor",
        quantity: 8
    },
    {
        id: 103,
        name: "Laptop Backpack",
        price: 899,
        category: "Accessories",
        description: "Water-resistant backpack with laptop compartment",
        quantity: 22
    },
    {
        id: 104,
        name: "Gaming Mouse",
        price: 699,
        category: "Computer Accessories",
        description: "RGB gaming mouse with programmable buttons",
        quantity: 30
    },
    {
        id: 105,
        name: "Yoga Mat",
        price: 499,
        category: "Fitness",
        description: "Non-slip yoga mat with carrying strap",
        quantity: 12
    }
];

// DOM Elements
const searchTypeSelect = document.getElementById('searchType');
const idSearchGroup = document.getElementById('idSearchGroup');
const nameSearchGroup = document.getElementById('nameSearchGroup');
const productIdInput = document.getElementById('productId');
const productNameInput = document.getElementById('productName');
const searchBtn = document.getElementById('searchBtn');
const resetBtn = document.getElementById('resetBtn');
const errorMessage = document.getElementById('errorMessage');
const resultSection = document.getElementById('resultSection');

// Result Fields
const resultId = document.getElementById('resultId');
const resultName = document.getElementById('resultName');
const resultPrice = document.getElementById('resultPrice');
const resultCategory = document.getElementById('resultCategory');
const resultQuantity = document.getElementById('resultQuantity');
const resultDescription = document.getElementById('resultDescription');

// Toggle search input based on search type
searchTypeSelect.addEventListener('change', () => {
    if (searchTypeSelect.value === 'id') {
        idSearchGroup.style.display = 'block';
        nameSearchGroup.style.display = 'none';
        productNameInput.value = '';
    } else {
        idSearchGroup.style.display = 'none';
        nameSearchGroup.style.display = 'block';
        productIdInput.value = '';
    }
    
    // Reset any previous results or errors
    errorMessage.textContent = '';
    resultSection.style.display = 'none';
});

// Search button click handler
searchBtn.addEventListener('click', () => {
    // Clear previous error messages
    errorMessage.textContent = '';
    
    if (searchTypeSelect.value === 'id') {
        searchById();
    } else {
        searchByName();
    }
});

// Reset button click handler
resetBtn.addEventListener('click', () => {
    // Clear all inputs and results
    productIdInput.value = '';
    productNameInput.value = '';
    errorMessage.textContent = '';
    resultSection.style.display = 'none';
});

// Search by Product ID
function searchById() {
    const idValue = productIdInput.value.trim();
    
    // Validate input
    if (!idValue) {
        displayError('Please enter a Product ID');
        return;
    }
    
    const id = parseInt(idValue);
    
    // Validate ID format (3 digits)
    if (id < 100 || id > 999) {
        displayError('Product ID must be a 3-digit number');
        return;
    }
    
    // Search for product
    const product = mockProducts.find(p => p.id === id);
    
    if (product) {
        displayProductDetails(product);
    } else {
        displayError('Product ID not found');
    }
}

// Search by Product Name
function searchByName() {
    const nameValue = productNameInput.value.trim();
    
    // Validate input
    if (!nameValue) {
        displayError('Please enter a Product Name');
        return;
    }
    
    // Search for products (case insensitive)
    const products = mockProducts.filter(p => 
        p.name.toLowerCase().includes(nameValue.toLowerCase())
    );
    
    if (products.length > 0) {
        // If multiple products found, display the first one
        // In a real app, you might want to show a list of all matches
        displayProductDetails(products[0]);
    } else {
        displayError('No products found matching the entered product name');
    }
}

// Display error message
function displayError(message) {
    errorMessage.textContent = message;
    resultSection.style.display = 'none';
}

// Display product details in table format
function displayProductDetails(product) {
    resultId.textContent = product.id;
    resultName.textContent = product.name;
    resultPrice.textContent = `₹${product.price}`;
    resultCategory.textContent = product.category;
    resultDescription.textContent = product.description;
    resultQuantity.textContent = product.quantity;
    
    // Show results section
    resultSection.style.display = 'block';
}

// Handle logout button
const logoutBtn = document.querySelector('.logout-btn');
logoutBtn.addEventListener('click', () => {
    alert('Logging out... Redirecting to login page.');
    // In a real application, this would redirect to the login page
    // window.location.href = "login.html";
});

// Initialize with ID search selected
searchTypeSelect.value = 'id';
idSearchGroup.style.display = 'block';
nameSearchGroup.style.display = 'none';