package com.example.onlineshop.config;


import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.service.RoleService;
import com.example.onlineshop.service.UserService;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Collections;

@Component
public class DataInit implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInit(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, MultipartConfigElement multipartConfigElement) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initAdminUser();
    }

    private void initRoles() {
        // Check if roles are already initialized
        if (roleService.findAll().isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");

            roleService.save(roleAdmin);
            roleService.save(roleUser);
        }
    }

    private void initAdminUser() {
        // Check if admin user is already initialized
        if (userService.findByUsername("admin") == null) {
            AppUser adminUser = new AppUser();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin")); // Password is "admin"
            adminUser.setEmail("admin@example.com");

            Role roleAdmin = roleService.findByName("ROLE_ADMIN");

            if (roleAdmin == null) {
                throw new RuntimeException("ROLE_ADMIN not found. Please ensure roles are initialized.");
            }

            adminUser.setRoles(Collections.singleton(roleAdmin));
            adminUser.setEnabled(true);

            userService.save(adminUser);
        }else {
            System.out.println("Admin user already exists");
        }
    }



}
