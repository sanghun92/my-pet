package com.shshon.mypet.endpoint.v1.pet.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.dto.PetDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetRegisterRequest(

        @JsonProperty("categoryId")
        @NotNull(message = "반려동물 타입은 필수값 입니다.")
        Long categoryId,

        @JsonProperty("name")
        @NotEmpty(message = "반려동물 이름은 필수값 입니다.")
        String name,

        @JsonProperty("birthDay")
        @NotNull(message = "반려동물 생일은 필수값 입니다.")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthDay,

        @JsonProperty("gender")
        @NotEmpty(message = "반려동물 성별은 필수값 입니다.")
        String gender,

        @JsonProperty("bodyWeight")
        @NotNull(message = "반려동물 몸무게는 필수값 입니다.")
        Integer bodyWeight,

        @JsonProperty("bodyType")
        @NotEmpty(message = "반려동물 체형 타입은 필수값 입니다.")
        String bodyType
) {
    public PetDto toMyPetDto() {
        return PetDto.builder()
                .name(this.name)
                .birthDay(this.birthDay)
                .gender(PetGender.valueOf(this.gender))
                .bodyWeight(this.bodyWeight)
                .bodyType(PetBodyType.valueOf(this.bodyType))
                .build();
    }
}
