package com.eds.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsFactory implements MailFactory{

    @Autowired
    private MailServiceProvider awsSesMailConfig;
    @Override
    public MailServiceProvider getMailServiceProvider() {
        return awsSesMailConfig;
    }
}
