package com.shshon.mypet.endpoint.v1.auth;

import com.shshon.mypet.auth.application.AuthFacade;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RequestClient;
import com.shshon.mypet.auth.dto.EmailVerificationDto;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest;
import com.shshon.mypet.endpoint.v1.auth.request.SendEmailVerificationCodeRequest;
import com.shshon.mypet.endpoint.v1.auth.request.TokenReIssueRequest;
import com.shshon.mypet.endpoint.v1.auth.response.TokenResponse;
import com.shshon.mypet.endpoint.v1.response.ApiResponseV1;
import com.shshon.mypet.properties.JwtTokenProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AuthApi {

    private final AuthFacade authFacade;
    private final JwtTokenProperties jwtTokenProperties;

    @PostMapping("/v1/auth/login")
    public ResponseEntity<ApiResponseV1<TokenResponse>> login(@RequestBody @Valid LoginMemberRequest request,
                                                              @RequestClient HttpRequestClient client) {
        TokenDto tokenDto = authFacade.login(request.email(), request.password(), client);
        return getTokenResponseEntity(tokenDto);
    }

    @PostMapping("/v1/auth/verification")
    public ApiResponseV1<?> sendEmailVerificationCode(@RequestBody SendEmailVerificationCodeRequest request) {
        EmailVerificationDto emailVerificationDto = authFacade.generateEmailVerificationCode(request.email());
        UriComponents uri = UriComponentsBuilder.fromHttpUrl("https://my-pet.o-r.kr/")
                .path("join/email-verification")
                .queryParam("code", emailVerificationDto.code())
                .queryParam("email", emailVerificationDto.email())
                .build(true);
        authFacade.sendEmailVerificationCode(emailVerificationDto, uri.toUriString());
        return ApiResponseV1.ok();
    }

    @GetMapping(value = "/v1/auth/verification", consumes = MediaType.ALL_VALUE)
    public void verifyEmailVerificationCode(@RequestParam("code") String code,
                                            @RequestParam("email") String email) {
        authFacade.verifyEmailVerificationCode(code, email);
    }

    @PostMapping("/v1/auth/token")
    public ResponseEntity<ApiResponseV1<TokenResponse>> reIssueToken(@RequestBody @Valid TokenReIssueRequest request,
                                                                     @RequestClient HttpRequestClient client) {
        TokenDto tokenDto = authFacade.reIssueToken(request.refreshToken(), client);
        return getTokenResponseEntity(tokenDto);
    }

    private ResponseEntity<ApiResponseV1<TokenResponse>> getTokenResponseEntity(TokenDto tokenDto) {
        String refreshToken = tokenDto.refreshToken().token();
        ResponseCookie responseCookie = ResponseCookie.from("REFRESH_TOKEN", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtTokenProperties.refreshToken().validityInSeconds())
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ApiResponseV1.ok(new TokenResponse(tokenDto.accessToken())));
    }
}
