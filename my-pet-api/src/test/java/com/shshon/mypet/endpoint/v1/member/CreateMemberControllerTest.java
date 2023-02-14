package com.shshon.mypet.endpoint.v1.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shshon.mypet.endpoint.v1.member.request.CreateMemberRequest;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.CreateMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.shshon.mypet.ApiDocumentUtils.*;
import static com.shshon.mypet.endpoint.v1.member.request.CreateMemberRequest.Const.*;
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

@AutoConfigureRestDocs
@WebMvcTest(CreateMemberController.class)
class CreateMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateMemberService createMemberService;

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
        given(createMemberService.createMember(any(MemberDto.class))).willReturn(memberDto);

        // when
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .nickname(memberDto.getNickname())
                .build();
        ResultActions resultActions = this.mockMvc.perform(
                post(MemberPaths.JOIN_MEMBER)
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        ConstraintDescriptions constraints = new ConstraintDescriptions(request.getClass());
        resultActions.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("member/member-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath(FILED_EMAIL).type(JsonFieldType.STRING).description(DESC_EMAIL)
                                        .attributes(constraints(constraints, FILED_EMAIL)),
                                fieldWithPath(FILED_PASSWORD).type(JsonFieldType.STRING).description(DESC_PASSWORD)
                                        .attributes(constraints(constraints, FILED_PASSWORD)),
                                fieldWithPath(FILED_NICKNAME).type(JsonFieldType.STRING).description(DESC_NICKNAME)
                                        .attributes(constraints(constraints, FILED_NICKNAME))
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header")
                        )
                ));
    }
}
