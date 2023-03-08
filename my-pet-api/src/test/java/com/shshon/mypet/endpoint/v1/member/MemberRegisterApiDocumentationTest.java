package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.endpoint.v1.member.request.MemberRegisterRequest;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberRegisterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentRequest;
import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberRegisterController.class)
class MemberRegisterApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private MemberRegisterService memberRegisterService;

    @Test
    @DisplayName("회원 가입 요청시 회원 생성 후 201 코드로 응답한다")
    void createMemberRequestThenReturnResponse() throws Exception {
        // given
        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .email("test@test.com")
                .password("myPassword123456!")
                .nickname("루루")
                .build();
        given(memberRegisterService.createMember(any(MemberDto.class))).willReturn(memberDto);

        // when
        MemberRegisterRequest request = MemberRegisterRequest.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
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
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("2자 이상, 20자 이하의 사용자 닉네임")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header")
                        )
                ));
    }
}
