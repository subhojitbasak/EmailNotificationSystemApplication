package com.eds.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean(name = "awsMailSender")
    public JavaMailSender awsMailSender(AwsSesMailConfig awsSesMailConfig) {
        return createMailSender(awsSesMailConfig.getHost(), awsSesMailConfig.getPort(),
                awsSesMailConfig.getUsername(), awsSesMailConfig.getPassword(),
                awsSesMailConfig.getProtocol());
    }

    @Bean(name="gmailMailSender")
    public JavaMailSender smtpMailSender(GmailMailConfig gmailMailConfig){
        return createMailSender(gmailMailConfig.getHost(),gmailMailConfig.getPort(),
                gmailMailConfig.getUsername(), gmailMailConfig.getPassword(),
                gmailMailConfig.getProtocol());
    }

    private JavaMailSender createMailSender(String host, int port, String username, String password, String protocol) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }

}
