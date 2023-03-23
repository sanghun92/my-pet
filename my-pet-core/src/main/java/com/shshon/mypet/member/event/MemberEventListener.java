package com.shshon.mypet.member.event;

import com.shshon.mypet.email.application.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventListener {

    private final EmailService emailService;

    @Async
    @TransactionalEventListener
    public void sendCertificatedEmail(CreateMemberEvent event) {

    }
}
