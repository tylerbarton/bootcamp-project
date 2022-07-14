

# The Bootcamp Project
By Tyler Barton

This project explores various techniques for building a web application using microservices to create a deployable appointment management service.

# 🌐 Frontend
<p align="center">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/html/html.png" alt="HTML5 logo" width="64" height="64">
    <img src="https://getbootstrap.com/docs/5.2/assets/brand/bootstrap-logo-shadow.png" alt="Bootstrap logo" width="64" height="64">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/css/css.png" alt="CSS logo" width="64" height="64">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/javascript/javascript.png" alt="JavaScript logo" width="64" height="64">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/react/react.png" alt="React logo" width="64" height="64">
</p>

The frontend features a React application that displays a list of appointments and a form to create new appointments and static pages to display the remainder of the pages.

# ☁ Backend
<p align="center">
    <img src="https://raw.githubusercontent.com/github/explore/5b3600551e122a3277c2c5368af2ad5725ffa9a1/topics/java/java.png" alt="Java logo" width="64" height="64">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/maven/maven.png" alt="Maven logo" width="64" height="64">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/spring-boot/spring-boot.png" alt="Springboot logo" width="64" height="64">
</p>

The backend features two decoupled microservices:
- **Appointment Service**: This service is responsible for managing appointments. It is responsible for creating, updating, and deleting appointments.
- **User Service**: This service is responsible for managing users. It is responsible for creating, updating, and deleting managed clients.

## ⚙ Configuration  
Each microservice can be configured separately in the respective `application.properites` file.

# 💾 Database
<p align="center">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/mongodb/mongodb.png" alt="MongoDB logo" width="64" height="63">
    <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/json/json.png" alt="JSON logo" width="64" height="63">
</p>

The database is a MongoDB database that stores the appointments and users in separate collections. 

# 📑 Documentation
<p align="center">
    <img src="https://raw.githubusercontent.com/swagger-api/swagger.io/wordpress/images/assets/SWU-logo-clr.png" alt="Swagger logo" width="" height="64">
</p>

Documentation for the microservices is generated using Swagger and Swagger UI. It can be accessed by appending the following URL to the host URL: `/swagger-ui/index.html#/`. 

Furthermore, all source code is fully documented with Javadocs.
