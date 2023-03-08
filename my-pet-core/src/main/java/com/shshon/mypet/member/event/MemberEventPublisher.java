package com.shshon.mypet.member.event;

import com.shshon.mypet.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishRegisterMemberEvent(Member member) {
        eventPublisher.publishEvent(
                CreateMemberEvent.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .certificationCode(member.getCertificationCode())
                        .build()
        );
    }
}
