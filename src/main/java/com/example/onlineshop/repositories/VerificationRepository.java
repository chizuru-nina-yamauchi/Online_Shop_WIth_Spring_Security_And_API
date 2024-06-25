package com.example.onlineshop.repositories;

import com.example.onlineshop.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationToken, Long>{
    VerificationToken findByToken(String token); // Automatically executes a query 'SELECT * FROM verification_token WHERE token = ?' because of JPA.
}
