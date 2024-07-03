package com.eds.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GmailFactory implements MailFactory{

    @Autowired
    private MailServiceProvider gmailMailConfig;
    @Override
    public MailServiceProvider getMailServiceProvider() {
        return gmailMailConfig;
    }
}
