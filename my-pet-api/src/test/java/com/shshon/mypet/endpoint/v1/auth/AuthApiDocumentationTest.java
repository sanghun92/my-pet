package com.shshon.mypet.endpoint.v1.auth;

import com.shshon.mypet.auth.application.AuthFacade;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest;
import com.shshon.mypet.endpoint.v1.auth.request.TokenReIssueRequest;
import com.shshon.mypet.paths.AuthPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;
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

@WebMvcTest(AuthApi.class)
class AuthApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private AuthFacade authFacade;

    @Test
    @DisplayName("로그인 요청시 회원 인증 후 토큰 정보를 반환한다.")
    void loginMemberRequestThenReturnTokenResponse() throws Exception {
        // given
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjc2NTM5NTgxLCJleHAiOjE2NzY1Mzk1ODN9.LTTbWaHFm5377EJURkf5NMmjXxDMgaHjGXw5EwUWrZ8";
        RefreshToken refreshToken = new RefreshToken(1L, "test@test.com", "0.0.0.1", "PC");
        given(authFacade.login(any(), any(), any())).willReturn(new TokenDto(accessToken, refreshToken));

        // when
        LoginMemberRequest request = LoginMemberRequest.builder()
                .email("test@test.com")
                .password("myPassword123456!")
                .build();
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
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "test@test.com");

        // when
        ResultActions resultActions = this.mockMvc.perform(
                post(AuthPaths.SEND_EMAIL_VERIFICATION)
                        .content(toJsonString(requestBody))
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
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjc2NTM5NTgxLCJleHAiOjE2NzY1Mzk1ODN9.LTTbWaHFm5377EJURkf5NMmjXxDMgaHjGXw5EwUWrZ8";
        RefreshToken refreshToken = new RefreshToken(1L, "test@test.com", "0.0.0.1", "PC");
        given(authFacade.reIssueToken(eq(request.refreshToken()), any(HttpRequestClient.class))).willReturn(new TokenDto(accessToken, refreshToken));

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
