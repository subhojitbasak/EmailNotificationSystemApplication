package com.eds.Controller;

import com.eds.Entity.EmailDetails;
import com.eds.Utility.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        emailService.sendEmail(emailDetails.getTo(),
                emailDetails.getFrom(),
                emailDetails.getSub(),
                emailDetails.getBody());

        System.out.println("To: " + emailDetails.getTo() +
                "From: " + emailDetails.getFrom() +
                "Subject: " + emailDetails.getSub() +
                "Body: " + emailDetails.getBody());

        return "compose_email";
    }

    @PostMapping("/sendfromgateway")
    public void sendEmailTest(@RequestBody HashMap map) {
        EmailDetails emailDetails = new EmailDetails();
        String username = (String) map.get("username");
        String from = "no-reply@gmail.com";
        String to = (String) map.get("email");
        String roles = (String) map.get("roles");
        String body = "Welcome " + username + " \nYou are successfully registered as : " + roles.substring(5);
        System.out.println("From EDS");
        emailService.sendEmail(to, from, "Registered", body);

    }
}



