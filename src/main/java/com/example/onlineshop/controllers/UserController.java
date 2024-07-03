package com.example.onlineshop.controllers;


import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.VerificationToken;
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


    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, Model model){
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Invalid token.");
            return "registration-confirm";
        }
        AppUser user = verificationToken.getUser();
        userService.enableUser(user);
        model.addAttribute("message", "Your account has been verified. You can now log in.");
        return "registration-confirm";
    }

    @GetMapping("/users/{username}")
    public String viewUserDetails(@PathVariable String username, Model model) {
        AppUser user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user-detail";
    }


}
