package com.shshon.mypet.endpoint.v1.auth;

import com.shshon.mypet.auth.application.AuthFacade;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.dto.EmailVerificationDto;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest;
import com.shshon.mypet.endpoint.v1.auth.request.SendEmailVerificationCodeRequest;
import com.shshon.mypet.endpoint.v1.auth.request.TokenReIssueRequest;
import com.shshon.mypet.paths.AuthPaths;
import com.shshon.mypet.properties.JwtTokenProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentRequest;
import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiDocumentationTest extends ApiDocumentationTest {

    @Mock
    private AuthFacade authFacade;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        JwtTokenProperties jwtTokenProperties = new JwtTokenProperties(
                new JwtTokenProperties.AccessToken("test", 5L),
                new JwtTokenProperties.RefreshToken(5L)
        );
        AuthApi controller = new AuthApi(authFacade, jwtTokenProperties);
        mockMvc = apiMockMvc(controller);
    }

    @Test
    @DisplayName("로그인 요청시 회원 인증 후 토큰 정보를 반환한다.")
    void loginMemberRequestThenReturnTokenResponse() throws Exception {
        // given
        LoginMemberRequest request = LoginMemberRequest.builder()
                .email("test@test.com")
                .password("myPassword123456!")
                .build();
        RefreshToken refreshToken = new RefreshToken(1L, "test@test.com", "0.0.0.1", "PC");
        given(authFacade.login(any(), any(), any())).willReturn(new TokenDto(ACCESS_TOKEN, refreshToken));

        // when
        ResultActions resultActions = this.mockMvc.perform(
                post(AuthPaths.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonBytes(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andDo(document("auth/member-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일 주소"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 패스워드")
                        ),
                        responseCookies(
                                cookieWithName("REFRESH_TOKEN").description("Refresh Token")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("사용자 인증 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("이메일 인증 요청시 해당 이메일 주소로 메일링 후 200 코드로 응답한다")
    void sendEmailVerificationRequestThenReturnResponse() throws Exception {
        // given
        SendEmailVerificationCodeRequest request = new SendEmailVerificationCodeRequest("test@test.com");
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto(UUID.randomUUID().toString(), request.email(), LocalDateTime.now(), true);
        given(authFacade.generateEmailVerificationCode(any(String.class))).willReturn(emailVerificationDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                post(AuthPaths.SEND_EMAIL_VERIFICATION)
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("auth/sendEmailVerification",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("인증할 이메일 주소")
                        )
                ));
    }

    @Test
    @DisplayName("이메일 인증 요청시 해당 이메일 인증 후 200 코드로 응답한다")
    void verifyEmailRequestThenReturnResponse() throws Exception {
        // given
        String code = UUID.randomUUID().toString();
        String email = "test@test.com";

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(AuthPaths.VERIFY_EMAIL)
                        .queryParam("code", code)
                        .queryParam("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("auth/verifyEmail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("code").description("이메일 인증 코드"),
                                parameterWithName("email").description("이메일")
                        )
                ));
    }

    @Test
    @DisplayName("토큰 재발급 요청시 Refresh Token 유효성 검사 후 200 코드로 응답한다")
    void reIssueTokenRequestThenReturnResponse() throws Exception {
        // given
        TokenReIssueRequest request = new TokenReIssueRequest(UUID.randomUUID().toString());
        RefreshToken refreshToken = new RefreshToken(1L, "test@test.com", "0.0.0.1", "PC");
        given(authFacade.reIssueToken(eq(request.refreshToken()), any(HttpRequestClient.class))).willReturn(new TokenDto(ACCESS_TOKEN, refreshToken));

        // when
        ResultActions resultActions = this.mockMvc.perform(
                post(AuthPaths.RE_ISSUE_TOKEN)
                        .content(toJsonBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("auth/reIssueToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("재발급용 토큰")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("사용자 인증 토큰")
                        )
                ));
    }
}
