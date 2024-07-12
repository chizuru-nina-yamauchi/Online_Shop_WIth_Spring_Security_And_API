package com.example.onlineshop.controllers;


import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.VerificationToken;
import com.example.onlineshop.service.RoleService;
import com.example.onlineshop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/home")
    public String userHome(Model model, Authentication authentication) {
        AppUser user = userService.findByUsername(authentication.getName());
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "user-home";
    }


    @GetMapping
    public String listUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model){
        AppUser user = userService.findByUsername(username);
        if(user != null){
            model.addAttribute("user", user);
        return "user-detail";
        }else{
            model.addAttribute("error", "User not found");
            return "redirect:/users";
        }
    }


    @GetMapping("/signup")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new AppUser());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpNewUser(@ModelAttribute AppUser user, Model model){
        try {
            AppUser registeredUser = userService.registerNewUser(user);
            model.addAttribute("message", "A verification email has been sent to " + registeredUser.getEmail());
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "registration-confirm";
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

    @Transactional
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, Model model){
        logger.info("confirmRegistration method called with token: " + token);
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Invalid token.");
            logger.warn("Invalid token: " + token);
            return "registration-confirm";
        }
        AppUser user = verificationToken.getUser();
        userService.enableUser(user);
        userService.save(user); // Save the user after enabling
        model.addAttribute("message", "Your account has been verified. You can now log in.");
        logger.info("User enabled and saved: " + user.getUsername());
        return "registration-confirm";
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "redirect:/users";
    }



}
