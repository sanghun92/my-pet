package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.auth.domain.AuthenticationMember;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.member.request.MemberChangePasswordRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberEditProfileRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.endpoint.v1.member.response.MemberResponse;
import com.shshon.mypet.member.application.MemberFacade;
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

    private final MemberFacade memberFacade;

    @PostMapping("/v1/members")
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberRegisterRequest request) {
        MemberDto member = memberFacade.createMember(request.toMember());
        return ResponseEntity.created(URI.create("v1/member/" + member.id())).build();
    }

    @PutMapping("/v1/members/password")
    public void changePassword(@AuthenticationMember LoginMember member,
                               @RequestBody @Valid MemberChangePasswordRequest request) {
        memberFacade.changePassword(member.id(), request.password(), request.newPassword());
    }

    @PutMapping("/v1/members")
    public MemberResponse editMemberProfile(@AuthenticationMember LoginMember member,
                                            @RequestBody @Valid MemberEditProfileRequest request) {
        MemberDto editedMember = memberFacade.editMember(member.id(), request.toMember());
        return MemberResponse.from(editedMember);
    }

    @DeleteMapping("/v1/members")
    public void deleteMember(@AuthenticationMember LoginMember member) {
        memberFacade.deleteMember(member.id());
    }
}
