### Assignment: Building an Online Shop with Currency Conversion


Assignment created and guided with README file by Safwan kher(https://github.com/safwan-kher).

---
README and instruction by Safwan kher.


#### Objective:
In this assignment, you will build an online shop application using Java Spring Boot, Spring Security, JPA, Hibernate, and Thymeleaf. The application will allow users to sign up, log in, browse products, add products to their cart, and convert prices between different currencies using the ExchangeRate API.

#### Requirements:
1. **User Management**:
   - Users should be able to sign up and log in.
   - Users should have roles (`ROLE_USER`, `ROLE_ADMIN`).
   - Admins should be able to assign the `ROLE_ADMIN` to other users.

2. **Product Management**:
   - Admins should be able to add, update, and delete products.
   - Users should be able to view products and add them to their cart.

3. **Currency Conversion**:
   - Users should be able to convert product prices between different currencies using the ExchangeRate API.

4. **Security**:
   - Implement authentication and authorization using Spring Security.
   - Passwords should be securely stored using `BCryptPasswordEncoder`.

5. **Database**:
   - Use JPA and Hibernate to manage entities and relationships.
   - Use PostgreSQL as the database.

6. **User Interface**:
   - Use Thymeleaf to create dynamic HTML templates for the user interface.

#### Concepts to Implement:
1. **Spring Boot**:
   - Create a Spring Boot application with the necessary dependencies.
   - Configure application properties.

2. **Spring Security**:
   - Implement authentication and authorization.
   - Use role-based access control.

3. **JPA and Hibernate**:
   - Define entities and relationships.
   - Use repositories to perform CRUD operations.

4. **Thymeleaf**:
   - Create dynamic HTML templates.
   - Use Thymeleaf expressions to populate content.

5. **Controllers**:
   - Implement controllers to handle HTTP requests and return views.
   - Handle form submissions.

6. **Service Layer**:
   - Implement business logic in the service layer.
   - Interact with repositories to perform database operations.

7. **RESTful API Integration**:
   - Use `RestTemplate` to make HTTP requests to the ExchangeRate API.

8. **Bidirectional Relationships**:
   - Implement bidirectional relationships between entities.

9. **Error Handling**:
   - Implement basic error handling.

### Step-by-Step Instructions:

#### Step 1: Set Up the Project
1. Create a new Spring Boot project with the following dependencies:
   - Spring Web
   - Spring Security
   - Spring Data JPA
   - PostgreSQL Driver
   - Thymeleaf
   - Spring Boot DevTools

2. Configure the `application.properties` file with the necessary settings:
   ```properties
   # Server Configuration
   server.port=8080

   # Database Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/onlineshop
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=org.postgresql.Driver

   # JPA/Hibernate properties
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   # Email Configuration
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_email_password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true

   # ExchangeRate-API Key
   exchangerate.api.key=your_api_key
   ```

#### Step 2: Implement User Management
1. **Entities**:
   - Create `User` and `Role` entities with a many-to-many relationship.
   - Create `VerificationToken` entity for email verification.

2. **Repositories**:
   - Create `UserRepository`, `RoleRepository`, and `VerificationTokenRepository`.

3. **Services**:
   - Implement `UserService` to handle user-related operations.
   - Implement email verification logic.

4. **Controllers**:
   - Implement `UserController` to handle user-related requests (signup, login, assign admin role, verify email).

5. **Security Configuration**:
   - Implement `SecurityConfig` to configure authentication and authorization.

#### Step 3: Implement Product Management
1. **Entities**:
   - Create `Product` entity with fields like `id`, `name`, `description`, `price`, and `currency`.

2. **Repositories**:
   - Create `ProductRepository`.

3. **Services**:
   - Implement `ProductService` to handle product-related operations.

4. **Controllers**:
   - Implement `ProductController` to handle product-related requests (add, update, delete, view products).

#### Step 4: Implement Currency Conversion
1. **Service**:
   - Implement `CurrencyConverterService` to interact with the ExchangeRate API.

2. **Controller**:
   - Implement `CurrencyConverterController` to handle currency conversion requests.

3. **Templates**:
   - Create `currency-converter.html` to allow users to convert product prices between different currencies.

#### Step 5: Implement User Interface
1. **Templates**:
   - Create `login.html`, `signup.html`, `home.html`, `admin-home.html`, `product-list.html`, `product-form.html`, and `currency-converter.html` using Thymeleaf.

2. **Dynamic Content**:
   - Use Thymeleaf expressions to dynamically populate content in the templates.

#### Step 6: Test the Application
1. **Sign Up and Log In**:
   - Test user signup and login functionality.
   - Verify email and assign admin role.

2. **Product Management**:
   - Test adding, updating, deleting, and viewing products.

3. **Currency Conversion**:
   - Test converting product prices between different currencies.

4. **Security**:
   - Test role-based access control.

### Submission:
Submit the following:
1. Source code of the project.
2. A README file with instructions on how to run the application.
3. Screenshots or a video demonstrating the functionality of the application.

### Concepts Explained:

1. **Spring Boot**:
   - Spring Boot simplifies the setup and development of Spring applications by providing default configurations and embedded servers. It allows you to create stand-alone, production-grade Spring-based applications with minimal configuration.

2. **Spring Security**:
   - Spring Security provides authentication and authorization capabilities. It allows you to secure your application by implementing role-based access control and password encoding.

3. **JPA and Hibernate**:
   - JPA (Java Persistence API) is a specification for object-relational mapping (ORM) in Java. Hibernate is an implementation of JPA. They allow you to map Java objects to database tables and perform CRUD operations.

4. **Thymeleaf**:
   - Thymeleaf is a modern server-side Java template engine for web and standalone environments. It allows you to create dynamic HTML templates and populate them with data from your application.

5. **Controllers**:
   - Controllers handle HTTP requests, interact with the service layer, and return views. They follow the Model-View-Controller (MVC) pattern.

6. **Service Layer**:
   - The service layer contains business logic and interacts with repositories to perform database operations. It encapsulates the core functionality of the application.

7. **RESTful API Integration**:
   - RESTful APIs allow you to interact with external services. In this assignment, you will use the ExchangeRate API to fetch conversion rates and supported currencies.

8. **Bidirectional Relationships**:
   - Bidirectional relationships between entities allow you to navigate the relationship from both sides. For example, a `User` can have multiple `Roles`, and a `Role` can have multiple `Users`.

9. **Error Handling**:
   - Error handling ensures that your application can gracefully handle unexpected situations. It includes throwing exceptions and providing meaningful error messages to the user.

By completing this assignment, you will gain hands-on experience with these concepts and learn how to build a secure, user-friendly online shop application with currency conversion capabilities.


------


From here by Chizuru Yamauchi


## Project Instructions

### 1. Set Up the Project
1. New Project: From IntelliJ IDEA, create a new Maven project.
2. Dependencies: Add dependencies for Spring Boot, Spring Data JPA, PostgreSQL, Thymeleaf, and Bootstrap 5.
3. On terminal, git init -> git remote add origin git@URL -> git remote -v -> git pull origin main -> git branch -M main -> git add. -> git commit -m "----" -> git push origin main


### 2. Configure PostgreSQL Database
1. On terminal, sudo -u postgres psql -> CREATE DATABASE database_name; -> \c database_name -> \q

2. Application Properties: Configure the PostgreSQL database connection in the `application.properties` file(and put the file name in .gitignore to secure the sensitive information.)

### 3. Add Java source code and html files

## Result: How it looks like and works

### You request **localhost:8080**

![pic1](./src/main/resources/static/pic1.png)

**There is no user so far in the database and we have to sign up first.**

![pic2](./src/main/resources/static/pic2.png)

**After filling the sign-up form and click 'Sign Up', the user get the verification email**

![pic3](./src/main/resources/static/pic3.png)

**Verification Email**

![pic4](./src/main/resources/static/pic4.png)




**Newly log in with a new username and a new password as a user(role)**.
![pic5](./src/main/resources/static/pic5.png)


**Email Testing:** Test passed.

![picTest](./src/main/resources/static/picTestPassed.png)


### **Log in again as a user**

![pic7](./src/main/resources/static/pic7.png)

**Home as a user**

![pic8](./src/main/resources/static/pic8.png)

**Click 'Shopping' and the user(demoChizuru) can see product lists** :(Jump to Product List and see all the products)
  
*Because demoChizuru logged in as a user now, there is no feature of 'Add New Product'

![pic25](./src/main/resources/static/pic25.png)

### **Log in as an admin**
**Home as an admin**

![pic9](./src/main/resources/static/pic9.png)

**Click 'Admin Home'** and jump to Admin Home Page and see all the option admin could do

![pic10](./src/main/resources/static/pic10.png)

**Click Product List** and see all the products. It is empty now because there is no product yet. But since the user logged in as an admin, the admin can add a new product with 'Add New Product' button.

![pic11](./src/main/resources/static/pic11.png)

**Click Add Product** and add a new product

![pic12](./src/main/resources/static/pic12.png)

![pic13](./src/main/resources/static/pic13.png)

**Click 'Save' the product** then the product list will show the product

![pic14](./src/main/resources/static/pic14.png)


**Click 'View'** to see the product detail

![pic15](./src/main/resources/static/pic15.png)


**Click 'Edit'** to edit the product

![pic16](./src/main/resources/static/pic16.png)

**Click 'Convert Price'** to go to the convert currency page to convert the price with product id number to different currencies

![pic17](./src/main/resources/static/pic17.png)

**Assign role to the user as admin**

![pic18](./src/main/resources/static/pic18.png)

**After assigning the role, you can see the modification on the database**

![pic20](./src/main/resources/static/pic20.png)

![pic19](./src/main/resources/static/pic19.png)

**The user ('user_id 6' = demoChizuru) is now assigned as an admin on database. 'role_id 5' = ROLE_ADMIN)**'

![pic21](./src/main/resources/static/pic21.png)

**demoChizuru can access to 'Admin Home' and also can see the page as a normal user for 'Shopping'**

![pic23](./src/main/resources/static/pic23.png)

**demoChizuru can go to Admin Home and do the admin tasks**

![pic24](./src/main/resources/static/pic24.png)

**When you want to log out** click logout, and you will be logged out, then you can log in again as a user or admin.

![pic22](./src/main/resources/static/pic22.png)

