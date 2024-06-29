package com.nobroker.ex.controller;


import com.nobroker.ex.service.EmailVerificationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    private EmailVerificationService emailVerificationService;


    //http://localhost:8080/api//send-otp-for-login?email=twilio.send.sms3@gmail.com

    //it will take the email id and to that emailId it will send the otp number
    @PostMapping("/send-otp-for-login")
    public Map<String, String> sendOtpForLogin(@RequestParam String email) {
        return emailVerificationService.sendOtpForLogin(email);
    }

    //http://localhost:8080/api//verify-otp-for-login?email=twilio.demo.14@gmail.com&otp=
    //whatever otp & email you give it will verify that with the value temporarily stored in hashmap
    @PostMapping("/verify-otp-for-login")
    public Map<String, String> verifyOtpForLogin(@RequestParam String email, @RequestParam String otp) {
        return emailVerificationService.verifyOtpForLogin(email, otp);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        request.getSession().invalidate();

        // Clear the security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

    public void test(){
    }

}
