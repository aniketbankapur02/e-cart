# US-9: Customer Feedback Servlet Project

## Overview
This project allows customers to give feedback for their delivered orders. It uses Java Servlets and Derby DB (embedded mode).

## Project Structure
```
us-9/
  ├── src/main/java/com/example/model/         # Data models
  ├── src/main/java/com/example/servlet/       # Servlets
  ├── src/main/java/com/example/util/          # Utility classes (DB connection)
  ├── web/WEB-INF/web.xml                      # Servlet configuration
  ├── web/feedback.jsp                         # Feedback form UI
  ├── derby/                                   # Derby DB files
  └── README.md                                # This file
```

## Prerequisites
- JDK 8 or above
- Apache Derby (included as embedded)
- Apache Tomcat or any Servlet container

## Setup Instructions
1. **Build the project** (compile Java files and package as WAR if needed).
2. **Deploy to Tomcat**: Copy the `web` directory contents to your Tomcat webapps directory, or package as WAR.
3. **Derby DB**: The database will be created automatically in the `derby/` folder on first run.
4. **Access the app**: Open your browser and go to `http://localhost:8080/us-9/`.

## Features
- View all delivered orders
- Add feedback (description + rating) for delivered orders

## Notes
- Feedback can only be given for delivered orders.
- Derby DB runs in embedded mode (no separate server needed). 


Yes, the provided codebase includes a **complete JSP and servlet implementation** for the user story:

- **OrderServlet**: Lists all orders, shows a "Give Feedback" button only for delivered orders without feedback.
- **FeedbackServlet**: Displays a feedback form and saves feedback to the Derby DB.
- **viewOrders.jsp**: Displays all orders and feedback options.
- **feedback.jsp**: Form for submitting feedback.
- **DBUtil**: Handles Derby DB connection and table creation.
- **Model classes**: For Order and Feedback.

---

## Detailed Eclipse Setup Instructions

### 1. **Install Prerequisites**
- **Eclipse IDE for Java EE Developers** (or any Eclipse with Dynamic Web Project support)
- **Apache Tomcat** (or any Servlet container)
- **JDK 8 or above**
- **Download Apache Derby**: [https://db.apache.org/derby/](https://db.apache.org/derby/)

---

### 2. **Project Import and Configuration**
#### a. **Import the Project**
1. Open Eclipse.
2. Go to `File` > `Import...` > `General` > `Existing Projects into Workspace`.
3. Select the root directory containing the `us-9` folder.
4. Click `Finish`.

#### b. **Add Derby Library**
1. Download `derby.jar` from the Derby website.
2. Place `derby.jar` in the `us-9/lib/` directory.
3. Right-click the project in Eclipse > `Build Path` > `Configure Build Path...`
4. Go to the `Libraries` tab.
5. Click `Add JARs...` and select `lib/derby.jar` from your project.
6. Click `OK`.

#### c. **Configure Tomcat**
1. In Eclipse, go to `Servers` view (Window > Show View > Servers).
2. Add a new server (Tomcat).
3. Right-click the server > `Add and Remove...` > Add your `us-9` project.

#### d. **Set Output Folder (if needed)**
- Make sure the output folder is set to `build` as in `.classpath`.

---

### 3. **Project Structure**
```
us-9/
  ├── src/main/java/com/example/model/         # Data models (Order, Feedback)
  ├── src/main/java/com/example/servlet/       # Servlets (OrderServlet, FeedbackServlet)
  ├── src/main/java/com/example/util/          # DBUtil for Derby connection
  ├── web/WEB-INF/web.xml                      # Servlet configuration
  ├── web/viewOrders.jsp                       # Orders list page
  ├── web/feedback.jsp                         # Feedback form page
  ├── derby/                                   # Derby DB files (auto-created)
  ├── lib/derby.jar                            # Derby DB driver (you add this)
  ├── .classpath, .project                     # Eclipse project files
  └── README.md                                # Documentation
```

---

### 4. **How It Works**
- **On first run**, Derby DB tables are created automatically.
- **Access the app**:  
  - Go to: `http://localhost:8080/us-9/orders`
  - You will see all orders (empty at first unless you add some).
  - For delivered orders without feedback, a "Give Feedback" button appears.
  - Clicking it opens the feedback form.
  - Submitting feedback saves it to the DB and updates the order.

---

### 5. **Adding Sample Data (Optional)**
You can add sample orders directly in Derby using a tool or by extending `DBUtil` with an insert method, or by running SQL like:
```sql
INSERT INTO orders (id, customer, status) VALUES (1, 'Alice', 'delivered');
INSERT INTO orders (id, customer, status) VALUES (2, 'Bob', 'pending');
```
You can do this in a Derby SQL tool or by adding a static block in `DBUtil` for demo purposes.

---

### 6. **Troubleshooting**
- **ClassNotFoundException for Derby**: Ensure `derby.jar` is in your build path.
- **JSP/Servlet errors**: Make sure Tomcat is running and the project is deployed.
- **Database not created**: The `derby/` folder should appear after first run; check for write permissions.

---

If you need a sample data loader or further customization, let me know!
