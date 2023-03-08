package com.shshon.mypet.email.application;

import com.shshon.mypet.email.dto.MailMessageDto;

public interface EmailService {

    void send(MailMessageDto mailMessage);
}
