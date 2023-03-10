package com.shshon.mypet.endpoint.v1.member;

import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.member.service.MemberUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentRequest;
import static com.shshon.mypet.docs.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberUpdateController.class)
class MemberUpdateControllerDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private MemberUpdateService memberUpdateService;

    @Test
    @DisplayName("회원 가입 요청시 회원 생성 후 201 코드로 응답한다")
    void certificateMemberRequestThenReturnResponse() throws Exception {
        // given
        String code = UUID.randomUUID().toString();
        willDoNothing().given(memberUpdateService).certificateMember(code);

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
