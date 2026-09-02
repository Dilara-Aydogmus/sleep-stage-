package com.sleepstageai.identity.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.sleepstageai.identity.user.UserRegistrationService;
import com.sleepstageai.identity.user.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/v1/auth")
    public class UserRegistrationController {
        private final UserRegistrationService registrationService;

        public UserRegistrationController(UserRegistrationService registrationService){
            this.registrationService = registrationService;
        }

        @PostMapping("/register")
        @ResponseStatus(HttpStatus.CREATED)
        public void register(@Valid @RequestBody UserRegistrationRequest request) {
            registrationService.register(request.getEmail(), request.getPassword());

    }

}
