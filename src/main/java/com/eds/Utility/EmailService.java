package com.eds.Utility;

import com.eds.Entity.EmailDetails;
import com.eds.ExceptionHandler.SendEmailException;

public interface EmailService {

    void sendSMTPEmail(EmailDetails emailDetails) throws SendEmailException;

    void sendSESEmail(EmailDetails emailDetails) throws SendEmailException;

    public boolean tokenValidator(String bearerToken);
}
