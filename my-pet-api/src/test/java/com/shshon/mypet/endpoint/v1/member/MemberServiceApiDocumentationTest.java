package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.endpoint.v1.member.request.MemberChangePasswordRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberEditProfileRequest;
import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.member.application.MemberFacade;
import com.shshon.mypet.member.dto.MemberDto;
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
import java.time.LocalDateTime;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.*;
import static com.shshon.mypet.docs.util.DocumentFormatGenerator.getDateFormat;
import static com.shshon.mypet.endpoint.v1.auth.AuthorizationExtractor.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberServiceApi.class)
class MemberServiceApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private MemberFacade memberFacade;

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
        given(memberFacade.createMember(any(MemberDto.class))).willReturn(memberDto);

        // when
        MemberRegisterRequest request = MemberRegisterRequest.builder()
                .email(memberDto.email())
                .password(memberDto.password())
                .nickname(memberDto.nickname())
                .phoneNumber(memberDto.phoneNumber())
                .birthDay(memberDto.birthDay())
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
    @DisplayName("회원 비밀번호 변경 요청시 변경 후 200 코드로 응답한다")
    void changeMemberPasswordRequestThenReturnResponse() throws Exception {
        // given
        MemberChangePasswordRequest request = new MemberChangePasswordRequest("PrevPassword123!", "NewPassword1234!");
        willDoNothing().given(memberFacade).changePassword(any(Long.class), any(String.class), any(String.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(
                put(MemberPaths.CHANGE_PASSWORD)
                        .header(AUTHORIZATION, auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("member/member-change-password",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                authTokenHeader()
                        ),
                        requestFields(
                                fieldWithPath("password").type(JsonFieldType.STRING).description("영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 이전 비밀번호"),
                                fieldWithPath("newPassword").type(JsonFieldType.STRING).description("영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 신규 비밀번호")
                        )
                ));
    }

    @Test
    @DisplayName("회원 프로필 수정 요청시 변경 후 200 코드로 응답한다")
    void editMemberProfileRequestThenReturnResponse() throws Exception {
        // given
        MemberEditProfileRequest request = new MemberEditProfileRequest("신규 닉네임", LocalDate.now(), "01099881234");
        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@test.com")
                .password("myPassword123456!")
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .birthDay(request.birthDay())
                .createdAt(LocalDateTime.of(2022, 2, 1, 13, 54))
                .build();
        given(memberFacade.editMember(any(Long.class), eq(request.toMember()))).willReturn(memberDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                put(MemberPaths.EDIT_PROFILE)
                        .header(AUTHORIZATION, auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("member/editMember",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                authTokenHeader()
                        ),
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("2자 이상, 20자 이하의 사용자 닉네임"),
                                fieldWithPath("birthDay").type(JsonFieldType.STRING).description("사용자 생일")
                                        .attributes(getDateFormat())
                                        .optional(),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("숫자만 포함 된 사용자 핸드폰 번호")
                                        .optional()
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
                                fieldWithPath("profile.createdAt").type(JsonFieldType.STRING).description("가입 일자")
                        )
                ));
    }
}
