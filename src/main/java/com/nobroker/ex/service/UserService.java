package com.nobroker.ex.service;


import com.nobroker.ex.entity.User;
import com.nobroker.ex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Save the user to the database
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void scheduleOtpRemoval(String email, int i, TimeUnit timeUnit) {
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public boolean isEmailVerified(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.isEmailVerified();
    }

}

//isEmailVerified sends an email id, it takes the email, searches the record and from that record im checking
//is this verifies. if true then call email service to generate otp