package com.eds.Controller;

import com.eds.Entity.EmailDetails;
import com.eds.ExceptionHandler.SendEmailException;
import com.eds.Utility.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send/SES")
    public String sendEmailBySes(@RequestBody EmailDetails emailDetails) throws SendEmailException {

        log.info("Sending email to: " + emailDetails.getTo());
        emailService.sendSESEmail(emailDetails);
        log.info("Email Sent successfully...");

        System.out.println("To: " + emailDetails.getTo() +
                " From: " + emailDetails.getFrom() +
                " Subject: " + emailDetails.getSub() +
                " Body: " + emailDetails.getBody());

        return "Message queued!!!";
    }

    @PostMapping("/send/SMTP")
    public String sendEmailBySmtp(@RequestBody EmailDetails emailDetails) throws SendEmailException {
        log.info("Sending email to: " + emailDetails.getTo());
        emailService.sendSMTPEmail(emailDetails);
        log.info("Email Sent successfully...");

        System.out.println("To: " + emailDetails.getTo() +
                " From: " + emailDetails.getFrom() +
                " Subject: " + emailDetails.getSub() +
                " Body: " + emailDetails.getBody());

        return "Message queued!!!";
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

    }
}



