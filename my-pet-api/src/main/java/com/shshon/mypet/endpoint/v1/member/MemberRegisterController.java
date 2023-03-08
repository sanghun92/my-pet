package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberRegisterService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class MemberRegisterController {

    private final MemberRegisterService memberRegisterService;

    public MemberRegisterController(MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }

    @PostMapping(MemberPaths.JOIN_MEMBER)
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberRegisterRequest request) {
        MemberDto member = memberRegisterService.createMember(request.toMember());
        return ResponseEntity.created(URI.create("v1/member/" + member.getId())).build();
    }
}
