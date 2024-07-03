package com.eds.Utility;

import com.eds.Entity.EmailDetails;
import com.eds.ExceptionHandler.SendEmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSeviceImpl implements EmailService {

    private static final int MAX_RETRY_COUNT = 5;
    private static final long RETRY_DELAY_MS = 6000;

    @Autowired
    private JavaMailSender awsMailSender;

    @Autowired
    private JavaMailSender gmailMailSender;


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
}
