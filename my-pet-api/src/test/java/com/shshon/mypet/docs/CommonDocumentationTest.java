package com.shshon.mypet.docs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shshon.mypet.docs.snippet.CustomResponseFieldsSnippet;
import com.shshon.mypet.endpoint.v1.common.Docs;
import com.shshon.mypet.endpoint.v1.common.EnumViewController;
import com.shshon.mypet.endpoint.v1.response.OkResponseV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommonDocumentationTest extends ApiDocumentationTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = apiMockMvc(new EnumViewController());
    }

    @Test
    void commons() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                get("/docs")
        );
        Docs docs = getData(resultActions.andReturn());

        resultActions.andExpect(status().isOk())
                .andDo((document("common",
                        responseFields(
                                attributes(key("title").value("공통 응답")),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 일자"),
                                fieldWithPath("timestamp_kst").type(JsonFieldType.STRING).description("(한국 기준) 응답 일자"),
                                subsectionWithPath("data").type(JsonFieldType.OBJECT).description("데이터").optional()
                        ),
                        customResponseFields("custom-response", beneathPath("data.petBodyTypes").withSubsectionId("petBodyTypes"),
                                attributes(key("title").value("bodyType")),
                                enumConvertFieldDescriptor(docs.getPetBodyTypes())
                        ),
                        customResponseFields("custom-response", beneathPath("data.petGenders").withSubsectionId("petGenders"),
                                attributes(key("title").value("gender")),
                                enumConvertFieldDescriptor(docs.getPetGenders())
                        ),
                        customResponseFields("custom-response", beneathPath("data.petTypes").withSubsectionId("petTypes"),
                                attributes(key("title").value("type")),
                                enumConvertFieldDescriptor(docs.getPetTypes())
                        )
                )));
    }

    private Docs getData(MvcResult mvcResult) throws IOException {
        OkResponseV1<Docs> apiResponse = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        return apiResponse.getData();
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes,
                                                                   FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }

    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }
}
