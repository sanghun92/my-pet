package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.auth.domain.AuthenticationMember;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.member.request.MemberChangePasswordRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.member.application.MemberService;
import com.shshon.mypet.member.dto.MemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class MemberServiceApi {

    private final MemberService memberService;

    @PostMapping("/v1/members")
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberRegisterRequest request) {
        MemberDto member = memberService.createMember(request.toMember());
        return ResponseEntity.created(URI.create("v1/member/" + member.id())).build();
    }

    @PutMapping("/v1/members")
    public void changePassword(@RequestBody @Valid MemberChangePasswordRequest request) {
        memberService.changePassword(request.toMember());
    }

    @DeleteMapping("/v1/members")
    public void deleteMember(@AuthenticationMember LoginMember member) {
        memberService.deleteMember(member);
    }
}
