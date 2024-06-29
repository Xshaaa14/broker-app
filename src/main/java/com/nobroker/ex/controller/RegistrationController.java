package com.nobroker.ex.controller;

import com.nobroker.ex.entity.User;
import com.nobroker.ex.service.EmailService;
import com.nobroker.ex.service.EmailVerificationService;
import com.nobroker.ex.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private UserService userService;

    private EmailService emailService;

    private EmailVerificationService emailVerificationService;

    public RegistrationController(UserService userService, EmailService emailService,EmailVerificationService emailVerificationService) {
        this.userService = userService;
        this.emailService = emailService;
        this.emailVerificationService = emailVerificationService;
    }


    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody User user) {
        // Register the user without email verification
        User registeredUser = userService.registerUser(user);
        return emailService.sendOtpEmail(user.getEmail());


    }

    //http://localhost:8080/api/verify-otp?email=&otp=

    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return emailVerificationService.verifyOtp(email, otp);
    }
}

//first i will enter the email
//it will send the otp
//then i will verify that otp