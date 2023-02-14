package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.endpoint.v1.member.request.CreateMemberRequest;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.CreateMemberService;
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
public class CreateMemberController {

    private final CreateMemberService createMemberService;

    public CreateMemberController(CreateMemberService createMemberService) {
        this.createMemberService = createMemberService;
    }

    @PostMapping(MemberPaths.JOIN_MEMBER)
    public ResponseEntity<?> joinMember(@RequestBody @Valid CreateMemberRequest request) {
        MemberDto member = createMemberService.createMember(request.toMember());
        return ResponseEntity.created(URI.create("v1/member/" + member.getId())).build();
    }
}
