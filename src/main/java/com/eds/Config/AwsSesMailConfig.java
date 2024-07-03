package com.eds.Config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AwsSesMailConfig {

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

    // getters
}
