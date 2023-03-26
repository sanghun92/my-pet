package com.shshon.mypet.member.application;

import com.shshon.mypet.Facade;
import com.shshon.mypet.common.exception.ClientException;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.exception.AuthorizationException;
import com.shshon.mypet.member.service.MemberService;
import com.shshon.mypet.util.CacheNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
@Slf4j
public class MemberFacade {

    private final MemberService memberService;

    @Transactional
    public MemberDto createMember(MemberDto memberDto) {
        Member member = memberService.createMember(memberDto);
        return MemberDto.from(member);
    }

    @Transactional
    public void changePassword(Long id, String prevPassword, String newPassword) {
        log.info("회원 비밀번호 변경 요청 - id : {}", id);
        Member member = memberService.findById(id);
        try {
            member.authenticate(prevPassword);
        } catch (AuthorizationException e) {
            throw new ClientException("이전 비밀번호가 일치하지 않습니다");
        }
        member.changePassword(newPassword);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.MEMBER, key = "#id")
    public MemberDto findMember(Long id) {
        log.info("회원 프로필 조회 요청 - id : {}", id);
        Member member = memberService.findById(id);
        return MemberDto.from(member);
    }

    @Transactional
    @CachePut(cacheNames = CacheNames.MEMBER, key = "#id")
    public MemberDto editMember(Long id, MemberDto toMember) {
        log.info("회원 프로필 정보 변경 요청 - id : {}", id);
        Member member = memberService.findById(id);
        member.edit(toMember.nickname(), toMember.birthDay(), toMember.phoneNumber());
        return MemberDto.from(member);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.MEMBER, key = "#id")
    public void deleteMember(Long id) {
        log.info("회원 삭제 요청 - id : {}", id);
        Member member = memberService.findById(id);
        member.delete();
    }
}
