package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.docs.util.DocumentLinkGenerator;
import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.paths.PetPaths;
import com.shshon.mypet.pet.application.PetQueryService;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.dto.PetImageDto;
import com.shshon.mypet.petcategory.application.PetCategoryQueryService;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.UUID;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.*;
import static com.shshon.mypet.docs.util.DocumentLinkGenerator.DocUrl.*;
import static com.shshon.mypet.endpoint.v1.auth.AuthorizationExtractor.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetQueryApi.class)
class PetQueryApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private PetQueryService petQueryService;

    @MockBean
    private PetCategoryQueryService petCategoryQueryService;

    private String userEmail;
    private long categoryId;

    @BeforeEach
    public void setUp() {
        this.userEmail = "test@test.com";
        this.categoryId = 1L;
    }

    @Test
    @DisplayName("반려동물 조회 요청시 반려 동물 조회 후 200 코드로 응답한다")
    void findMyPetRequestThenReturnResponse() throws Exception {
        // given
        ImageMetaData imageMetaData = ImageMetaData.builder()
                .path(UUID.randomUUID().toString())
                .name("test")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .size(0L)
                .build();
        given(petQueryService.findMyPet(any(Long.class)))
                .willReturn(PetDto.builder()
                        .id(1L)
                        .memberId(1L)
                        .categoryId(categoryId)
                        .petImage(PetImageDto.builder()
                                .id(1L)
                                .imageMetaData(imageMetaData)
                                .build())
                        .name("루루")
                        .birthDay(LocalDate.now())
                        .gender(PetGender.MALE)
                        .bodyWeight(3)
                        .bodyType(PetBodyType.SLIM)
                        .build());
        given(petCategoryQueryService.findById(any(Long.class)))
                .willReturn(PetCategoryDto.builder()
                        .id(categoryId)
                        .type(PetType.CAT)
                        .name("샴")
                        .build());

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(PetPaths.FIND_MY_PET)
                        .header(AUTHORIZATION, auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("pet/mypet-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                authTokenHeader()
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("category").type(JsonFieldType.OBJECT).description("반려동물 카테고리"),
                                fieldWithPath("category.type").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(PET_TYPES)),
                                fieldWithPath("category.name").type(JsonFieldType.STRING).description("반려동물 타입명"),
                                fieldWithPath("petImageUrl").type(JsonFieldType.STRING).description("반려동물 이미지 URL"),
                                fieldWithPath("petName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("birthDay").type(JsonFieldType.VARIES).description("생일"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(PET_GENDERS)),
                                fieldWithPath("bodyWeight").type(JsonFieldType.NUMBER).description("몸무게"),
                                fieldWithPath("bodyType").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(PET_BODY_TYPES))
                        )
                ));
    }
}
