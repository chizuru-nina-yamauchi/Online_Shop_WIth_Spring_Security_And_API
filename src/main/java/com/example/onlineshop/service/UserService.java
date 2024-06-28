package com.example.onlineshop.service;

import com.example.onlineshop.models.AppUser;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.VerificationToken;
import com.example.onlineshop.repositories.RoleRepository;
import com.example.onlineshop.repositories.UserRepository;
import com.example.onlineshop.repositories.VerificationRepository;
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

    public AppUser registerNewUser(AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }

    public void createVerificationToken(AppUser user, String token){
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token);
    }

    public void enableUser(AppUser user){
        user.setEnabled(true);
        userRepository.save(user);
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
        javaMailSender.send(email);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

}
