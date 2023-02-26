package com.shshon.mypet.endpoint.v1;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final MemberRepository memberRepository;
    public DataLoader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addTestMember() {
        memberRepository.save(Member.createMember("test@test.com", "qwer1234","집사"));
    }
}
