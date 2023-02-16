package com.shshon.mypet.endpoint.v1.auth;

import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.AuthService;
import com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest;
import com.shshon.mypet.endpoint.v1.auth.response.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(AuthPaths.LOGIN)
    public TokenResponse login(@RequestBody @Valid LoginMemberRequest request) {
        TokenDto tokenDto = authService.login(request.getEmail(), request.getPassword());
        return TokenResponse.of(tokenDto);
    }
}
