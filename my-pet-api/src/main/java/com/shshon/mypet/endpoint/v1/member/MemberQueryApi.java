package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.auth.domain.AuthenticationMember;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.member.response.MemberResponse;
import com.shshon.mypet.member.application.MemberFacade;
import com.shshon.mypet.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MemberQueryApi {

    private final MemberFacade memberFacade;

    @GetMapping("/v1/members/mine")
    public MemberResponse findMemberProfile(@AuthenticationMember LoginMember member) {
        MemberDto memberDto = memberFacade.findMember(member.id());
        return MemberResponse.from(memberDto);
    }
}
