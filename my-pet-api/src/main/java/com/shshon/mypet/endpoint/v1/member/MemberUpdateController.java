package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.member.service.MemberUpdateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class MemberUpdateController {

    private final MemberUpdateService memberUpdateService;

    public MemberUpdateController(MemberUpdateService memberUpdateService) {
        this.memberUpdateService = memberUpdateService;
    }

    @GetMapping(value = MemberPaths.CERTIFICATE_MEMBER, consumes = MediaType.ALL_VALUE)
    public void certificateMember(@RequestParam("code") String code) {
        memberUpdateService.certificateMember(code);
    }
}
