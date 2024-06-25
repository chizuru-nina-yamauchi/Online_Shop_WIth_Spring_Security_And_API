package com.example.onlineshop.models;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity // This class is a JPA entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER) // One to one relationship between the token and the user. When the user is loaded, the token is also loaded.
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    private Date expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(String token, AppUser user){ // No need to pass the expiry date and time when creating a new token because it will be calculated automatically.
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(24 * 60); // 24 hours * 60 minutes
    } // Automatically calculate the expiry date and time when a new token is created.

    public Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar calendar = Calendar.getInstance(); // Get the current date and time
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes); // Add the expiry time in minutes
        return calendar.getTime(); // Return the future expiry date and time
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }




}
