package com.example.onlineshop.repositories;

import com.example.onlineshop.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username); // part of the Spring Data JPA framework's query generation from method names. This 'findBy' is fixed.
    // This method will execute a query 'SELECT * FROM user WHERE username = ?' automatically.

    Optional<AppUser> findByEmail(String email); // Same as above
}
