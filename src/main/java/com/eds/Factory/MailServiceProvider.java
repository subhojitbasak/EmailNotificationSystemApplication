package com.eds.Factory;

public interface MailServiceProvider {
     String getHost();
    String getProtocol();
    String getUsername();
    String getPassword();
    int getPort();
}
