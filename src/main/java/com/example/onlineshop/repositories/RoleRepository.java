package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); // Automatically executes a query 'SELECT * FROM role WHERE name = ?' because of JPA's query generation from method names.
}
