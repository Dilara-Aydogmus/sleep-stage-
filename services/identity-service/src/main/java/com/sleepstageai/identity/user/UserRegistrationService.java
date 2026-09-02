package com.sleepstageai.identity.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;

@Service
public class UserRegistrationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder){
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserAccount register(String email, String rawPassword){
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);

        if(userAccountRepository.existsByEmail(normalizedEmail)){
            throw new IllegalArgumentException("Email is already registered");
        }

        String passwordHash = passwordEncoder.encode(rawPassword);

        UserAccount newAccount = new UserAccount(normalizedEmail, passwordHash);

        userAccountRepository.save(newAccount);

        return newAccount;
    }
}
