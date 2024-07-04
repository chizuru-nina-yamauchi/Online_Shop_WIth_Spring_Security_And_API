package com.example.onlineshop.controllers;

import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.service.RoleService;
import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/home")
    public String adminHome(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("adminName", "Admin");
        return "admin-home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/assign-role")
    public String showAssignRoleForm(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "assign-role";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign-role")
    public String assignRoleToUser(@RequestParam String username, @RequestParam String roleName, Model model) {
        AppUser user = userService.findByUsername(username);
        if (user != null) {
            Role role = roleService.findByName(roleName);
            user.getRoles().add(role);
            userService.save(user);
            model.addAttribute("successMessage", "Role assigned successfully.");
        } else {
            model.addAttribute("errorMessage", "User not found.");
        }
        return "redirect:/admin/home";
    }
}
