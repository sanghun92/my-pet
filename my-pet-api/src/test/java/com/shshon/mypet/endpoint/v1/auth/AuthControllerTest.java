package com.shshon.mypet.endpoint.v1.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.AuthService;
import com.shshon.mypet.endpoint.RestControllerTest;
import com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest;
import com.shshon.mypet.endpoint.v1.auth.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.shshon.mypet.ApiDocumentUtils.*;
import static com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest.Const.*;
import static com.shshon.mypet.endpoint.v1.auth.response.TokenResponse.Const.DESC_TOKEN;
import static com.shshon.mypet.endpoint.v1.auth.response.TokenResponse.Const.FILED_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
class AuthControllerTest extends RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
                        .content(objectMapper.writeValueAsBytes(request))
        );

        // then
        ConstraintDescriptions reqConstraints = new ConstraintDescriptions(request.getClass());
        ConstraintDescriptions resConstraints = new ConstraintDescriptions(TokenResponse.class);
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andDo(document("auth/member-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath(FILED_EMAIL).type(JsonFieldType.STRING).description(DESC_EMAIL)
                                        .attributes(constraints(reqConstraints, FILED_EMAIL)),
                                fieldWithPath(FILED_PASSWORD).type(JsonFieldType.STRING).description(DESC_PASSWORD)
                                        .attributes(constraints(reqConstraints, FILED_PASSWORD))
                        ),
                        responseFields(
                                fieldWithPath(FILED_TOKEN).type(JsonFieldType.STRING).description(DESC_TOKEN)
                                        .attributes(constraints(resConstraints, FILED_TOKEN))
                        )
                ));
    }
}
