package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.member.service.UpdateMemberService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class UpdateMemberController {

    private final UpdateMemberService updateMemberService;

    public UpdateMemberController(UpdateMemberService updateMemberService) {
        this.updateMemberService = updateMemberService;
    }

    @GetMapping(value = MemberPaths.CERTIFICATE_MEMBER, consumes = MediaType.ALL_VALUE)
    public void certificateMember(@RequestParam("code") String code) {
        updateMemberService.certificateMember(code);
    }
}
