package com.example.onlineshop.controllers;


import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.service.RoleService;
import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping
    public String listUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "user-detail";
    }


    @GetMapping("/signup")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpNewUser(@ModelAttribute AppUser user){
        userService.registerNewUser(user);
        return "redirect:/login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/assign-role")
    public String showAssignRoleForm(Model model){
        model.addAttribute("roles", roleService.findAll());
        return "assign-role";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign-role")
    public String assignRoleToUser(@RequestParam String username, @RequestParam String roleName){
        AppUser user = userService.findByUsername(username);
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
        userService.save(user);
        return "redirect:/users";
    }

}
