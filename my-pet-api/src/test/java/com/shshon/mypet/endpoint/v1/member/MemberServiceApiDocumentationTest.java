package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.endpoint.v1.member.request.MemberChangePasswordRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberService;
import com.shshon.mypet.paths.MemberPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.UUID;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentRequest;
import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentResponse;
import static com.shshon.mypet.docs.util.DocumentFormatGenerator.getDateFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberServiceApi.class)
class MemberServiceApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입 요청시 회원 생성 후 201 코드로 응답한다")
    void createMemberRequestThenReturnResponse() throws Exception {
        // given
        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@test.com")
                .password("myPassword123456!")
                .nickname("루루")
                .phoneNumber("01012345678")
                .birthDay(LocalDate.now())
                .build();
        given(memberService.createMember(any(MemberDto.class))).willReturn(memberDto);

        // when
        MemberRegisterRequest request = MemberRegisterRequest.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
                .phoneNumber(memberDto.getPhoneNumber())
                .birthDay(memberDto.getBirthDay())
                .build();
        ResultActions resultActions = this.mockMvc.perform(
                post(MemberPaths.JOIN_MEMBER)
                        .content(toJsonBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("member/member-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일 주소"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("2자 이상, 20자 이하의 사용자 닉네임"),
                                fieldWithPath("birthDay").type(JsonFieldType.STRING).description("사용자 생일")
                                        .attributes(getDateFormat())
                                        .optional(),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("숫자만 포함 된 사용자 핸드폰 번호")
                                        .optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header")
                        )
                ));
    }

    @Test
    @DisplayName("회원 가입 요청시 회원 생성 후 201 코드로 응답한다")
    void certificateMemberRequestThenReturnResponse() throws Exception {
        // given
        String code = UUID.randomUUID().toString();
        willDoNothing().given(memberService).certificateMember(code);

        // when
        ResultActions resultActions = this.mockMvc.perform(get(MemberPaths.CERTIFICATE_MEMBER)
                .queryParam("code", code)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("member/member-certification",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("code").description("이메일 인증 코드")
                        )
                ));
    }

    @Test
    @DisplayName("회원 비밀번호 변경 요청시 변경 후 200 코드로 응답한다")
    void changeMemberPasswordRequestThenReturnResponse() throws Exception {
        // given
        MemberChangePasswordRequest request = MemberChangePasswordRequest.builder()
                .email("test@test.com")
                .password("NewPassword1234!")
                .build();
        willDoNothing().given(memberService).changePassword(any(MemberDto.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(put(MemberPaths.CHANGE_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("member/member-change-password",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일 주소"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호")
                        )
                ));
    }
}
