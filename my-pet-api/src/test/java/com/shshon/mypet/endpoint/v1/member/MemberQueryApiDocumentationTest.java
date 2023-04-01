package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.auth.application.AuthFacade;
import com.shshon.mypet.auth.dto.EmailVerificationDto;
import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.member.application.MemberFacade;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.paths.MemberPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.*;
import static com.shshon.mypet.docs.util.DocumentFormatGenerator.getDateFormat;
import static com.shshon.mypet.endpoint.v1.auth.AuthorizationExtractor.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberQueryApi.class)
class MemberQueryApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private MemberFacade memberFacade;

    @MockBean
    private AuthFacade authFacade;

    @Test
    @DisplayName("회원 프로필 조회 요청시 조회 후 200 코드로 응답한다")
    void findMemberProfileRequestThenReturnResponse() throws Exception {
        // given
        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@test.com")
                .password("myPassword123456!")
                .nickname("루루")
                .phoneNumber("01012345678")
                .birthDay(LocalDate.now())
                .createdAt(LocalDateTime.of(2022, 2, 1, 13, 54))
                .build();
        EmailVerificationDto emailVerificationDto = new EmailVerificationDto(UUID.randomUUID().toString(), memberDto.email(), LocalDateTime.now(), true);
        given(memberFacade.findMemberProfileBy(any(Long.class))).willReturn(memberDto);
        given(authFacade.findEmailVerificationBy(any(String.class))).willReturn(emailVerificationDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(MemberPaths.FIND_PROFILE)
                        .header(AUTHORIZATION, auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("member/findProfile",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                authTokenHeader()
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("profile.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("profile.email").type(JsonFieldType.STRING).description("회원 Email 주소"),
                                fieldWithPath("profile.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("profile.birthDay").type(JsonFieldType.STRING).description("회원 생일")
                                        .attributes(getDateFormat())
                                        .optional(),
                                fieldWithPath("profile.phoneNumber").type(JsonFieldType.STRING).description("회원 핸드폰 번호")
                                        .optional(),
                                fieldWithPath("profile.createdAt").type(JsonFieldType.STRING).description("가입 일자"),
                                fieldWithPath("emailVerification.verifiedAt").type(JsonFieldType.STRING).description("이메일 인증 일자").optional(),
                                fieldWithPath("emailVerification.isVerified").type(JsonFieldType.BOOLEAN).description("이메일 인증 여부").optional()
                        )
                ));
    }
}
