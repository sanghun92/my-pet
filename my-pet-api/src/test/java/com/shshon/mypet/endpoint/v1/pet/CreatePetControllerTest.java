package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.endpoint.RestControllerTest;
import com.shshon.mypet.endpoint.v1.pet.request.CreatePetRequest;
import com.shshon.mypet.pet.domain.*;
import com.shshon.mypet.pet.event.CreatePetEvent;
import com.shshon.mypet.pet.event.CreatePetEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.shshon.mypet.ApiDocumentUtils.*;
import static com.shshon.mypet.endpoint.v1.auth.AuthorizationExtractor.AUTHORIZATION;
import static com.shshon.mypet.endpoint.v1.pet.request.CreatePetRequest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
class CreatePetControllerTest extends RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @MockBean
    private CreatePetEventListener eventListener;

    private long categoryId;

    @BeforeEach
    public void setUp() {
        super.setUp();

        PetCategory catCategory = PetCategory.createParent(PetType.CAT, "단모종");
        catCategory.addChildCategory("샴");
        petCategoryRepository.save(catCategory);

        this.categoryId = catCategory.getChild().get(0).getId();
    }

    @Test
    @DisplayName("반려동물 등록 요청시 반려 동물 생성 후 201 코드로 응답한다")
    void enrollMyPetRequestThenReturnResponse() throws Exception {
        // given
        CreatePetRequest createPetRequest = CreatePetRequest.builder()
                .categoryId(categoryId)
                .name("루루")
                .birthDay(LocalDate.now())
                .gender(PetGender.MALE.name())
                .bodyWeight(2)
                .bodyType(PetBodyType.NORMAL.name())
                .build();
        MockMultipartFile petImage = new MockMultipartFile("petImage", "coffee_sample.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[0]);
        MockMultipartFile request = new MockMultipartFile("request", "request", MediaType.APPLICATION_JSON_VALUE, toJsonBytes(createPetRequest));
        willDoNothing().given(eventListener).uploadPetImage(any(CreatePetEvent.class));

        // when Bearer
        ResultActions resultActions = this.mockMvc.perform(
                multipart(PetPaths.CREATE_PET)
                        .file(petImage)
                        .file(request)
                        .header(AUTHORIZATION, auth("test@test.com"))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        );

        // then
        ConstraintDescriptions constraints = new ConstraintDescriptions(createPetRequest.getClass());
        resultActions.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("pet/mypet-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("petImage").optional().description("반려동물 이미지"),
                                partWithName("request").description("반려동물 등록값")
                        ),
                        requestPartFields("request",
                                fieldWithPath(FILED_CATEGORY_ID).type(JsonFieldType.NUMBER).description(DESC_CATEGORY_ID)
                                        .attributes(constraints(constraints, FILED_CATEGORY_ID)),
                                fieldWithPath(FILED_NAME).type(JsonFieldType.STRING).description(DESC_NAME)
                                        .attributes(constraints(constraints, FILED_NAME)),
                                fieldWithPath(FILED_BIRTH_DAY).type(JsonFieldType.STRING).description(DESC_BIRTH_DAY)
                                        .attributes(constraints(constraints, FILED_BIRTH_DAY)),
                                fieldWithPath(FILED_GENDER).type(JsonFieldType.STRING).description(DESC_GENDER)
                                        .attributes(constraints(constraints, FILED_GENDER)),
                                fieldWithPath(FILED_BODY_WEIGHT).type(JsonFieldType.NUMBER).description(DESC_BODY_WEIGHT)
                                        .attributes(constraints(constraints, FILED_BODY_WEIGHT)),
                                fieldWithPath(FILED_BODY_TYPE).type(JsonFieldType.STRING).description(DESC_BODY_TYPE)
                                        .attributes(constraints(constraints, FILED_BODY_TYPE))
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header")
                        )
                ));
    }
}
