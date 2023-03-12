package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.event.CreateMemberEvent;
import com.shshon.mypet.member.exception.InValidMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MemberDto createMember(MemberDto memberDto) {
        validateNewMember(memberDto);
        Member member = memberRepository.save(memberDto.toMember());
        eventPublisher.publishEvent(
                CreateMemberEvent.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .nickname(member.getNickname())
                        .certificationCode(member.getCertification())
                        .build()
        );
        return MemberDto.from(member);
    }

    private void validateNewMember(MemberDto memberDto) {
        if(memberRepository.findByEmail(memberDto.getEmail()).isPresent()) {
            throw InValidMemberException.duplicatedEmail();
        }
    }
}
