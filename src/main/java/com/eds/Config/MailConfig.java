package com.eds.Config;

import com.eds.Factory.AwsFactory;
import com.eds.Factory.GmailFactory;
import com.eds.Factory.MailServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Slf4j
public class MailConfig {
    @Bean(name = "awsMailSender")
    public JavaMailSender awsMailSender(AwsFactory awsFactory) {
        MailServiceProvider sesProvider = awsFactory.getMailServiceProvider();

        log.debug("PROVIDER FROM CONTROLLER:\nHOST " + sesProvider.getHost() + "\nPORT " + sesProvider.getPort() + "\nUSERNAME " +
                sesProvider.getUsername() + "\nPASSWORD " + sesProvider.getPassword() + "\nPROTOCOL " +
                sesProvider.getProtocol());
        return createMailSender(sesProvider.getHost(), sesProvider.getPort(),
                sesProvider.getUsername(), sesProvider.getPassword(),
                sesProvider.getProtocol());
    }

    @Bean(name = "gmailMailSender")
    public JavaMailSender smtpMailSender(GmailFactory gmailFactory) {
        MailServiceProvider smtpProvider = gmailFactory.getMailServiceProvider();

        log.debug("PROVIDER FROM CONTROLLER:\nHOST " + smtpProvider.getHost() + "\nPORT " + smtpProvider.getPort() + "\nUSERNAME " +
                smtpProvider.getUsername() + "\nPASSWORD " + smtpProvider.getPassword() + "\nPROTOCOL " +
                smtpProvider.getProtocol());
        return createMailSender(smtpProvider.getHost(), smtpProvider.getPort(),
                smtpProvider.getUsername(), smtpProvider.getPassword(),
                smtpProvider.getProtocol());
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
