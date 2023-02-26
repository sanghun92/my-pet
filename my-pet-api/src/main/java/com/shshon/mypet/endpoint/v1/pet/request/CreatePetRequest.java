package com.shshon.mypet.endpoint.v1.pet.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.dto.PetDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@JsonDeserialize(builder = CreatePetRequest.CreatePetRequestBuilder.class)
public class CreatePetRequest {

    public static final String FILED_CATEGORY_ID = "categoryId";
    public static final String DESC_CATEGORY_ID = "반려동물 타입";
    private static final String MESSAGE_NOT_EMPTY_CATEGORY_ID = "반려동물 타입은 필수값 입니다.";
    public static final String FILED_NAME = "name";
    public static final String DESC_NAME = "반려동물 이름";
    private static final String MESSAGE_NOT_EMPTY_NAME = "반려동물 이름은 필수값 입니다.";
    public static final String FILED_BIRTH_DAY = "birthDay";
    public static final String DESC_BIRTH_DAY = "반려동물 생일 {yyyy-MM-dd}";
    private static final String MESSAGE_NOT_EMPTY_BIRTH_DAY = "반려동물 생일은 필수값 입니다.";
    public static final String FILED_GENDER = "gender";
    public static final String DESC_GENDER = "반려동물 성별 [MALE, FEMALE]";
    private static final String MESSAGE_NOT_EMPTY_GENDER = "반려동물 성별은 필수값 입니다.";
    public static final String FILED_BODY_WEIGHT = "bodyWeight";
    public static final String DESC_BODY_WEIGHT = "반려동물 몸무게";
    private static final String MESSAGE_NOT_EMPTY_BODY_WEIGHT = "반려동물 몸무게는 필수값 입니다.";
    public static final String FILED_BODY_TYPE = "bodyType";
    public static final String DESC_BODY_TYPE = "반려동물 체형 타입 [SLIM, NORMAL, CHUBBY]";
    private static final String MESSAGE_NOT_EMPTY_BODY_TYPE = "반려동물 체형 타입은 필수값 입니다.";

    @JsonProperty(FILED_CATEGORY_ID)
    @JsonPropertyDescription(DESC_CATEGORY_ID)
    @NotNull(message = MESSAGE_NOT_EMPTY_CATEGORY_ID)
    private final Long categoryId;

    @JsonProperty(FILED_NAME)
    @JsonPropertyDescription(DESC_NAME)
    @NotNull(message = MESSAGE_NOT_EMPTY_NAME)
    private final String name;

    @JsonProperty(FILED_BIRTH_DAY)
    @JsonPropertyDescription(DESC_BIRTH_DAY)
    @NotNull(message = MESSAGE_NOT_EMPTY_BIRTH_DAY)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate birthDay;

    @JsonProperty(FILED_GENDER)
    @JsonPropertyDescription(DESC_GENDER)
    @NotNull(message = MESSAGE_NOT_EMPTY_GENDER)
    private final String gender;

    @JsonProperty(FILED_BODY_WEIGHT)
    @JsonPropertyDescription(DESC_BODY_WEIGHT)
    @NotNull(message = MESSAGE_NOT_EMPTY_BODY_WEIGHT)
    private final Integer bodyWeight;

    @JsonProperty(FILED_BODY_TYPE)
    @JsonPropertyDescription(DESC_BODY_TYPE)
    @NotNull(message = MESSAGE_NOT_EMPTY_BODY_TYPE)
    private final String bodyType;

    public PetDto toMyPetDto() {
        return PetDto.builder()
                .name(this.name)
                .birthDay(this.birthDay)
                .gender(PetGender.valueOf(gender))
                .bodyWeight(this.bodyWeight)
                .bodyType(PetBodyType.valueOf(this.bodyType))
                .build();
    }
}
