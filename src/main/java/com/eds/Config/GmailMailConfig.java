package com.eds.Config;

import com.eds.Factory.MailServiceProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
public class GmailMailConfig implements MailServiceProvider {
    @Value("${gmail.mail.host}")
    private String host;

    @Value("${gmail.mail.port}")
    private int port;

    @Value("${gmail.mail.username}")
    private String username;

    @Value("${gmail.mail.password}")
    private String password;

    @Value("${gmail.mail.protocol}")
    private String protocol;

}
