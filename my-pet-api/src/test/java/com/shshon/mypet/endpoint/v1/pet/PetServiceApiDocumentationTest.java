package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.docs.ApiDocumentationTest;
import com.shshon.mypet.docs.util.DocumentLinkGenerator;
import com.shshon.mypet.endpoint.v1.pet.request.PetRegisterRequest;
import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.mapper.image.ImageDtoMapper;
import com.shshon.mypet.paths.PetPaths;
import com.shshon.mypet.pet.application.PetService;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.dto.PetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.shshon.mypet.docs.util.ApiDocumentUtils.*;
import static com.shshon.mypet.docs.util.DocumentFormatGenerator.getDateFormat;
import static com.shshon.mypet.docs.util.DocumentLinkGenerator.DocUrl.PET_BODY_TYPES;
import static com.shshon.mypet.docs.util.DocumentLinkGenerator.DocUrl.PET_GENDERS;
import static com.shshon.mypet.endpoint.v1.auth.AuthorizationExtractor.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetServiceApi.class)
class PetServiceApiDocumentationTest extends ApiDocumentationTest {

    @MockBean
    private PetService petService;

    @MockBean
    private ImageDtoMapper imageDtoMapper;

    private String memberEmail;
    private long categoryId;

    @BeforeEach
    public void setUp() {
        super.setUp();

        this.memberEmail = "test@test.com";
        this.categoryId = 1L;
    }

    @Test
    @DisplayName("반려동물 등록 요청시 반려 동물 생성 후 201 코드로 응답한다")
    void registerMyPetRequestThenReturnResponse() throws Exception {
        // given
        PetRegisterRequest petRegisterRequest = PetRegisterRequest.builder()
                .categoryId(categoryId)
                .name("루루")
                .birthDay(LocalDate.now())
                .gender(PetGender.MALE.name())
                .bodyWeight(2)
                .bodyType(PetBodyType.NORMAL.name())
                .build();
        MockMultipartFile petImage = new MockMultipartFile("petImage", "coffee_sample.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[0]);
        MockMultipartFile request = new MockMultipartFile("request", "request", MediaType.APPLICATION_JSON_VALUE, toJsonBytes(petRegisterRequest));
        willDoNothing().given(petService).registerMyPet(any(Long.class), any(Long.class), any(PetDto.class), any(ImageDto.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(
                multipart(PetPaths.REGISTER_MY_PET)
                        .file(petImage)
                        .file(request)
                        .header(AUTHORIZATION, auth(memberEmail))
                        .contentType(MediaType.MULTIPART_MIXED_VALUE)
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("pet/mypet-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                authTokenHeader()
                        ),
                        requestParts(
                                partWithName("petImage").optional().description("반려동물 이미지"),
                                partWithName("request").description("반려동물 등록값")
                        ),
                        requestPartFields("request",
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("반려동물 타입"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("반려동물 이름"),
                                fieldWithPath("birthDay").type(JsonFieldType.STRING).description("반려동물 생일")
                                        .attributes(getDateFormat()),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(PET_GENDERS)),
                                fieldWithPath("bodyWeight").type(JsonFieldType.NUMBER).description("반려동물 몸무게"),
                                fieldWithPath("bodyType").type(JsonFieldType.STRING).description(DocumentLinkGenerator.generateLinkCode(PET_BODY_TYPES))
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Header")
                        )
                ));
    }
}
