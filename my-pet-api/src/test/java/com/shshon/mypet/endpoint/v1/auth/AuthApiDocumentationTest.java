package com.shshon.mypet.endpoint.v1.auth;

import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.AuthService;
import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentRequest;
import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("로그인 요청시 회원 인증 후 토큰 정보를 반환한다.")
    void loginMemberRequestThenReturnTokenResponse() throws Exception {
        // given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjc2NTM5NTgxLCJleHAiOjE2NzY1Mzk1ODN9.LTTbWaHFm5377EJURkf5NMmjXxDMgaHjGXw5EwUWrZ8";
        given(authService.login(any(), any())).willReturn(TokenDto.of(token));

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
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andDo(document("auth/member-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일 주소"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 패스워드")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("token").type(JsonFieldType.STRING).description("사용자 인증 토큰")
                        )
                ));
    }
}
