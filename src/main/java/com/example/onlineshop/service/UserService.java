package com.example.onlineshop.service;

import java.util.logging.Logger;


import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.VerificationToken;
import com.example.onlineshop.repositories.RoleRepository;
import com.example.onlineshop.repositories.UserRepository;
import com.example.onlineshop.repositories.VerificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationRepository tokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;


    private static final Logger logger = Logger.getLogger(UserService.class.getName());


    /**
     * This method is part of the UserDetailsService interface in Spring Security.
     * It's used to retrieve a user's authentication and authorization information.
     *
     * @param username the username of the user to load
     * @return a UserDetails object representing the user's authentication and authorization information
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * This method is used to convert the roles of the user to a collection of GrantedAuthority.  (Collection<> means a collection of objects. ? means it can be any type of object.)
     * GrantedAuthority is an interface in Spring Security that represents an authority granted to an Authentication object.
     *
     * @param roles the roles to convert to GrantedAuthority objects
     * @return a collection of GrantedAuthority objects representing the user's authorities
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public AppUser registerNewUser(AppUser user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));
        user.setEnabled(false); // initially disable the user

        AppUser registeredUser = userRepository.save(user);
        String token = UUID.randomUUID().toString();
        createVerificationToken(registeredUser, token);
        sendVerificationEmail(registeredUser, token);

        return registeredUser;
    }

    public void createVerificationToken(AppUser user, String token){
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public void enableUser(AppUser user){
        AppUser managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        managedUser.setEnabled(true);
        userRepository.save(managedUser);
        logger.info("User enabled: " + managedUser.getUsername());
    }

    public void sendVerificationEmail(AppUser user, String token){
        // To send the email, we need the recipient's email address, the subject, and the message.
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:8080/registrationConfirm?token=" + token;
        String message = "To confirm your e-mail address, please click the link below:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + confirmationUrl);
        try {
            javaMailSender.send(email);
        } catch(Exception e) {
            System.err.println("Failed to send email" + e.getMessage());
        }
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    public AppUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
