package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.endpoint.v1.member.request.MemberChangePasswordRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberRegisterService;
import com.shshon.mypet.member.service.MemberUpdateService;
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

    private final MemberRegisterService memberRegisterService;
    private final MemberUpdateService memberUpdateService;

    @PostMapping("/v1/members")
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberRegisterRequest request) {
        MemberDto member = memberRegisterService.createMember(request.toMember());
        return ResponseEntity.created(URI.create("v1/member/" + member.getId())).build();
    }

    @PutMapping("/v1/members")
    public void changePassword(@RequestBody @Valid MemberChangePasswordRequest request) {
        memberUpdateService.changePassword(request.toMember());
    }

    @GetMapping(value = "/v1/members/certification", consumes = MediaType.ALL_VALUE)
    public void certificateMember(@RequestParam("code") String code) {
        memberUpdateService.certificateMember(code);
    }
}
