package com.shshon.mypet.member.event;

import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.email.dto.MailMessageDto;
import com.shshon.mypet.member.exception.InValidCreateMemberEventException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberEventListener {

    private final EmailService emailService;

    @Async
    @TransactionalEventListener
    public void sendCertificatedEmail(CreateMemberEvent event) {
        validateCertificatedEmailEvent(event);
        String certificationLink = String.format("http://localhost:8080/v1/member/certification?code=%s", event.certificationCode());

        Map<String, String> attribute = new HashMap<>();
        attribute.put("name", event.nickname());
        attribute.put("link", certificationLink);

        MailMessageDto mailMessageDto = MailMessageDto.builder()
                .to(new String[]{event.email()})
                .subject(String.format("'%s'님 회원가입 인증 요청드립니다.", event.nickname()))
                .template("memberCertificationMail")
                .attribute(attribute)
                .build();
        emailService.send(mailMessageDto);
    }

    private void validateCertificatedEmailEvent(CreateMemberEvent event) {
        if(event.certificationCode() == null) {
            throw InValidCreateMemberEventException.emptyCertificationCode();
        }

        if(event.email() == null) {
            throw InValidCreateMemberEventException.emptyEmail();
        }

        if(event.nickname() == null) {
            throw InValidCreateMemberEventException.emptyNickName();
        }
    }
}
