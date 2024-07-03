package com.eds.Config;

import com.eds.Factory.MailServiceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSesMailConfig implements MailServiceProvider {

    @Value("${aws.mail.host}")
    private String host;

    @Value("${aws.mail.port}")
    private int port;

    @Value("${aws.mail.username}")
    private String username;

    @Value("${aws.mail.password}")
    private String password;

    @Value("${aws.mail.protocol}")
    private String protocol;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getPort() {
        return port;
    }
}
