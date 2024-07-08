package com.example.onlineshop.repositories;

import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long>{
    VerificationToken findByToken(String token); // Automatically executes a query 'SELECT * FROM verification_token WHERE token = ?' because of JPA.

    VerificationToken findByUser(AppUser user); // Automatically executes a query 'SELECT * FROM verification_token WHERE user_id = ?' because of JPA.
}
