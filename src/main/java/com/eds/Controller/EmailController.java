package com.eds.Controller;

import com.eds.Entity.EmailDetails;
import com.eds.ExceptionHandler.SendEmailException;
import com.eds.Utility.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send/SES")
    public String sendEmailBySes(@RequestHeader("Bearer-token") String bearerToken, @RequestBody EmailDetails emailDetails) throws SendEmailException {

        log.info("----------------Token validation starting----------------");
        boolean valid = emailService.tokenValidator(bearerToken);
        log.info("----------------Token validation ends--------------------");
        if(valid) {

            log.info("Sending email to: " + emailDetails.getTo());
            emailService.sendSESEmail(emailDetails);
            log.info("Email Sent successfully...");

            System.out.println("To: " + emailDetails.getTo() +
                    " From: " + emailDetails.getFrom() +
                    " Subject: " + emailDetails.getSub() +
                    " Body: " + emailDetails.getBody());

            return "Message queued!!!";
        }else {
            return "Failed token";
        }
    }

    @PostMapping("/send/SMTP")
    public String sendEmailBySmtp(@RequestHeader("Bearer-token") String bearerToken, @RequestBody EmailDetails emailDetails) throws SendEmailException {
        log.info("----------------Token validation starting----------------");
        boolean valid = emailService.tokenValidator(bearerToken);
        log.info("----------------Token validation ends--------------------");
        if(valid){
        log.info("Sending email to: " + emailDetails.getTo());
        emailService.sendSMTPEmail(emailDetails);
        log.info("Email Sent successfully...");

        System.out.println("To: " + emailDetails.getTo() +
                " From: " + emailDetails.getFrom() +
                " Subject: " + emailDetails.getSub() +
                " Body: " + emailDetails.getBody());

        return "Message queued!!!";
        }else {
            return "Failed token";
        }
    }


    @PostMapping("/sendEmail/SES")
    public String sendEmailTest(@RequestHeader("Bearer-token") String bearerToken, @RequestBody HashMap map) {
        System.out.println("Bearer: " + bearerToken);
        boolean valid = emailService.tokenValidator(bearerToken);
        System.out.println("TOKEN IS:" + valid);
        if (valid) {
            EmailDetails emailDetails = new EmailDetails();

            String username = (String) map.get("username");
            String from = "no-reply@gmail.com";
            String to = (String) map.get("email");
            String roles = (String) map.get("roles");
            String body = "Welcome " + username + " \nYou are successfully registered as : " + roles.substring(5);
            System.out.println("From EDS");
            return "From EDS";

        } else {
            return "Failed token";
        }

    }
}



