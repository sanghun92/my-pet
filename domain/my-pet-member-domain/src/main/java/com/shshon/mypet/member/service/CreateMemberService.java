package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateMemberService {

    private final MemberRepository memberRepository;

    public CreateMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto createMember(MemberDto memberDto) {
        Member member = memberRepository.save(memberDto.toMember());
        return MemberDto.from(member);
    }
}
