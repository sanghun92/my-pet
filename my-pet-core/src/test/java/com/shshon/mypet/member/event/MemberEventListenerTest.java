package com.shshon.mypet.member.event;

import com.shshon.mypet.email.application.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberEventListenerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MemberEventListener eventListener;

    @Test
    @DisplayName("회원 가입시 인증 메일을 보낸다")
    void sendCertificatedEmailWhenMemberIsJoinedTest() {
        // given
        CreateMemberEvent event = CreateMemberEvent.builder()
                .nickname("루루")
                .email("test@test.com")
                .certificationCode(UUID.randomUUID().toString())
                .build();
        willDoNothing().given(emailService).send(any());

        // when
        eventListener.sendCertificatedEmail(event);

        // then
        then(emailService).should(times(1)).send(any());
    }
}
