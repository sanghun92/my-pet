package com.shshon.mypet.endpoint.v1.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shshon.mypet.member.service.UpdateMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.shshon.mypet.ApiDocumentUtils.getDocumentRequest;
import static com.shshon.mypet.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(UpdateMemberController.class)
class UpdateMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UpdateMemberService updateMemberService;

    @Test
    @DisplayName("회원 가입 요청시 회원 생성 후 201 코드로 응답한다")
    void certificateMemberRequestThenReturnResponse() throws Exception {
        // given
        String code = UUID.randomUUID().toString();
        willDoNothing().given(updateMemberService).certificateMember(code);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(MemberPaths.CERTIFICATE_MEMBER)
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
}
