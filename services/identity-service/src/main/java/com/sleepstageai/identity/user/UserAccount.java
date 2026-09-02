package com.sleepstageai.identity.user;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean accountEnabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole currentRole = UserRole.USER;

    protected UserAccount(){

    }


    public UserAccount(String email, String passwordHash){
        this.email = email;
        this.passwordHash = passwordHash;
    }


    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public UserRole getCurrentRole() {
        return currentRole;
    }
}
