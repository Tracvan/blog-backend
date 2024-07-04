package com.codegym.controller;

import com.codegym.model.Email;
import com.codegym.service.imp.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendEmail (@RequestBody Email email) {
        emailService.sendSimpleEmail(email.to, email.subject, email.text);
        return "Email sent successfully";
    }
}