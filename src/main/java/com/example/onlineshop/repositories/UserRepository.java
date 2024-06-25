package com.example.onlineshop.repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // part of the Spring Data JPA framework's query generation from method names. This 'findBy' is fixed.
    // This method will execute a query 'SELECT * FROM user WHERE username = ?' automatically.

    User findByEmail(String email); // Same as above
}
