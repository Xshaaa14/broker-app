package com.nobroker.ex.service;

import com.nobroker.ex.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationService {

    static final Map<String, String> emailOtpMapping = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService; //it will just call generate otp method & give us otp

    public Map<String, String> verifyOtp(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);
        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                userService.verifyEmail(user);
                emailOtpMapping.remove(email);
                response.put("status", "success");
                response.put("message", "Email verified successfully");
            } else {
                response.put("status", "error");
                response.put("message", "User not found");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }
        return response;
    }

    //it will just call generate otp method & give us otp
    // Define a ScheduledExecutorService to schedule tasks
    public Map<String, String> sendOtpForLogin(String email) {
        if (userService.isEmailVerified(email)) {
            String otp = emailService.generateOtp();
            emailOtpMapping.put(email, otp);

            // Send OTP to the user's email
            emailService.sendOtpEmail(email);

            // Schedule a task to remove expired OTPs periodically
            scheduleOtpRemoval(email, 3, TimeUnit.MINUTES);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "OTP sent successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Email is not verified");
            return response;
        }

        private void scheduleOtpRemoval(String email, long delay, TimeUnit timeUnit) {
            scheduler.schedule(() -> {
                // Remove the OTP after the specified delay
                emailOtpMapping.remove(email);
            }, delay, timeUnit);
        }

    //it will take email and otp and compare with hashmap and it will return success
    public Map<String, String> verifyOtpForLogin(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);

        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {
            emailOtpMapping.remove(email);
            response.put("status", "success");
            response.put("message", "OTP verified successfully");
        } else {
            // Invalid OTP
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }

        return response;
    }
}


//what is your day to day activity?
//sir i used to get defects i was responsible to fix the defects

//what kind of defects did you fixed in your projects? mention few of them