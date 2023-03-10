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
    @DisplayName("?????? ?????? ????????? ?????? ?????? ??? 201 ????????? ????????????")
    void createMemberRequestThenReturnResponse() throws Exception {
        // given
        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@test.com")
                .password("myPassword123456!")
                .nickname("??????")
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
                                fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("?????? ???,???????????? ??????, ??????????????? ????????? 1??? ????????? ????????? 8??? ~ 20?????? ????????????"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("2??? ??????, 20??? ????????? ????????? ?????????"),
                                fieldWithPath("birthDay").type(JsonFieldType.STRING).description("????????? ??????")
                                        .attributes(getDateFormat())
                                        .optional(),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("????????? ?????? ??? ????????? ????????? ??????")
                                        .optional()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ?????? ??? 201 ????????? ????????????")
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
                                parameterWithName("code").description("????????? ?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ???????????? ?????? ????????? ?????? ??? 200 ????????? ????????????")
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
                                fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("?????? ???,???????????? ??????, ??????????????? ????????? 1??? ????????? ????????? 8??? ~ 20?????? ????????????")
                        )
                ));
    }
}
