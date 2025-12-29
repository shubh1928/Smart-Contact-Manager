# 📇 Smart Contact Manager 2.0

Smart Contact Manager 2.0 is a secure, cloud-enabled contact management application built using **Spring Boot**, **Spring Security**, **OAuth2**, **MySQL**, and **Tailwind CSS**.  
It allows users to manage their personal contacts safely with modern authentication, responsive UI, and scalable architecture.

---

## 🚀 Features

### 🔐 Authentication & Security
- User Registration & Login
- Email Verification using SMTP
- Spring Security (Session-based Authentication)
- OAuth2 Login (Google & GitHub)
- BCrypt Password Encryption
- CSRF & XSS Protection

### 👥 Contact Management
- Add, View, Update & Delete Contacts (CRUD)
- Search contacts by name, email, or phone
- Pagination & Sorting for large datasets
- Favorite contacts for quick access

### ☁️ Cloud & Media
- Cloudinary / AWS S3 integration for contact image uploads
- Secure image storage using URLs

### 📤 Additional Utilities
- Export contacts (PDF / Excel)
- Send emails directly to contacts
- Dark Mode / Light Mode support

### 🎨 Modern UI
- Tailwind CSS for responsive design
- Thymeleaf with reusable fragments
- JavaScript & Flowbite for interactive UI components

---

## 🏗️ Tech Stack

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Security
- Spring Data JPA (Hibernate)
- OAuth2 (Google & GitHub)

### Frontend
- Thymeleaf
- Tailwind CSS
- JavaScript
- Flowbite

### Database
- MySQL

### Tools & Platforms
- Maven
- Git & GitHub
- Eclipse IDE
- Cloudinary / AWS S3
- SMTP (Email Service)

---

## 🧠 Architecture

The project follows the **MVC (Model-View-Controller)** architecture:

Controller → Service → Repository → Database
↓
View (Thymeleaf + Tailwind)

yaml
Copy code

This ensures clean separation of concerns and scalability.

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/shubh1928/Smart-Contact-Manager.git
cd Smart-Contact-Manager
2️⃣ Database Configuration
Create a MySQL database:

sql
Copy code
CREATE DATABASE scm;
Update application.properties:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/scm
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

3️⃣ Email (SMTP) Configuration
properties
Copy code
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

4️⃣ OAuth2 Configuration (Google / GitHub)
properties
Copy code
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
(Similar configuration for GitHub OAuth)

5️⃣ Cloudinary / AWS S3 Configuration
properties
Copy code
cloudinary.cloud-name=YOUR_CLOUD_NAME
cloudinary.api-key=YOUR_API_KEY
cloudinary.api-secret=YOUR_API_SECRET

6️⃣ Tailwind CSS Setup
bash
Copy code
npm install
npm run build

7️⃣ Run the Application
bash
Copy code
mvn spring-boot:run
Open in browser:

arduino
Copy code
http://localhost:8080

📂 Project Structure
css
Copy code
scm/
 ├── src/main/java
 │   ├── controller
 │   ├── service
 │   ├── repository
 │   ├── config
 │   └── model
 ├── src/main/resources
 │   ├── templates
 │   ├── static
 │   └── application.properties
 ├── pom.xml
 └── README.md

🔒 Security Highlights
Encrypted passwords using BCrypt

Secure session handling

User-specific data isolation

OAuth2-based authentication

📈 Why This Project Matters
This project demonstrates:

Real-world Spring Boot application development

Secure authentication & authorization

Third-party API integrations

Optimized database handling using pagination

Clean and maintainable code structure

🧑‍💻 Author
Shubham Gopale
Java Full Stack Developer
GitHub: https://github.com/shubh1928

⭐ Support
If you like this project:

⭐ Star the repository

🍴 Fork it

📢 Share it on LinkedIn

📌 Future Enhancements
REST API version

Angular / React frontend

Role-based access control

Docker deployment

📄 License
This project is created for learning and portfolio purposes.
