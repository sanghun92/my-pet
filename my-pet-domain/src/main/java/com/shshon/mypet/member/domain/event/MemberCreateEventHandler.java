package com.shshon.mypet.member.domain.event;

import com.shshon.mypet.auth.application.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberCreateEventHandler {

    private final AuthFacade authFacade;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void sendEmailVerificationCode(MemberCreateEvent event) {
        String email = event.email();
        authFacade.sendEmailVerificationCode(email);
    }
}
