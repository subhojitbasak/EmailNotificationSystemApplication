package com.eds.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSeviceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_DELAY_MS = 6000;
    @Override
    public void sendEmail(String to, String from,String sub, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        int retryCount =0;
        boolean sent = false;

        message.setTo(to);
        message.setText(body);
        message.setSubject(sub);
        message.setFrom(from);
        while(retryCount < MAX_RETRY_COUNT && !sent) {
            try {
                javaMailSender.send(message);
                sent = true;
                System.out.println("Email sent successfully!!");
            } catch (Exception e) {
                retryCount++;
                System.out.println("Failed to send email. Retry count: "+retryCount);
                if(retryCount < MAX_RETRY_COUNT ){
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    }catch (InterruptedException exception){
                        Thread.currentThread().interrupt();
                    }
                }else{
                    System.out.println("Max retry attempt reach. Could not send email ");
                }

            }
        }

    }
}
