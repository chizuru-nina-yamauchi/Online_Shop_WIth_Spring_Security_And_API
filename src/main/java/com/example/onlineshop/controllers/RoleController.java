package com.example.onlineshop.controllers;

import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.service.RoleService;
import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign")
    public String assignRoleToUser(@RequestParam String username, @RequestParam String roleName) {
        AppUser user = userService.findByUsername(username);
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
        userService.save(user);
        return "Role assigned successfully";
    }
}
