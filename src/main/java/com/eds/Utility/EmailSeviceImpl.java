package com.eds.Utility;

import com.eds.Entity.EmailDetails;
import com.eds.ExceptionHandler.SendEmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EmailSeviceImpl implements EmailService {

    private static final int MAX_RETRY_COUNT = 5;
    private static final long RETRY_DELAY_MS = 6000;

    @Autowired
    private JavaMailSender awsMailSender;

    @Autowired
    private JavaMailSender gmailMailSender;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void sendSMTPEmail(EmailDetails emailDetails) throws SendEmailException {

        MimeMessage message = gmailMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        log.info("Email sending from SMTP server...");

        int retryCount = 0;
        boolean sent = false;
        while (retryCount < MAX_RETRY_COUNT && !sent) {
            try {
                helper.setTo(emailDetails.getTo());
                helper.setFrom(emailDetails.getFrom());
                helper.setSubject(emailDetails.getSub());
                helper.setText(emailDetails.getBody());

                gmailMailSender.send(message);
                sent = true;
                System.out.println("Email sent successfully!!");
            } catch (Exception e) {
                retryCount++;
                log.info("Failed to send email. Retry count: " + retryCount);
                if (retryCount < MAX_RETRY_COUNT) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.info("Max retry attempt reach. Could not send email ");
                    throw new SendEmailException("Max retry attempt reach. Could not send email");
                }

            }
        }
    }


    @Override
    public void sendSESEmail(EmailDetails emailDetails) throws SendEmailException {
        MimeMessage message = awsMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        log.info("Email sending from AWS SES server...");

        int retryCount = 0;
        boolean sent = false;
        while (retryCount < MAX_RETRY_COUNT && !sent) {
            try {
                helper.setTo(emailDetails.getTo());
                helper.setFrom(emailDetails.getFrom());
                helper.setSubject(emailDetails.getSub());
                helper.setText(emailDetails.getBody());

                awsMailSender.send(message);
                sent = true;
                log.info("Email sent successfully!!");
            } catch (Exception e) {
                retryCount++;
                log.info("Failed to send email. Retry count: " + retryCount);
                if (retryCount < MAX_RETRY_COUNT) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.info("Max retry attempt reach. Could not send email ");
                    throw new SendEmailException("Max retry attempt reach. Could not send email");

                }

            }
        }
    }

    public boolean tokenValidator(String bearerToken){
        //1. send API GW request to validate the token
        String api_gateway_url = "http://localhost:8080/fromEDS";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(bearerToken, headers);
        try {
            log.info("sending request to API Gateway....");
            ResponseEntity<String> validatedUser = restTemplate.exchange(api_gateway_url, HttpMethod.POST, requestEntity, String.class);
            System.out.println("FROM RESPONSE ENTITY: USER: "+ validatedUser.getHeaders().get("username"));
            String validateToken = validatedUser.getHeaders().getFirst("validateToken");
            System.out.println("FROM RESPONSE ENTITY: isValid: "+validateToken);
            log.info("-------Token is valid-------");
            return Boolean.parseBoolean(validateToken);
        }catch (Exception ex){
            log.error("------Token is invalid-----");
            return false;
        }
    }
}
