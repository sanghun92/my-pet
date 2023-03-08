package com.shshon.mypet.endpoint.v1.petcategory.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = ParentPetCategoryRegisterRequest.ParentPetCategoryRegisterRequestBuilder.class)
public class ParentPetCategoryRegisterRequest {

    private final String type;
    private final String name;

    public PetCategoryDto toDto() {
        return PetCategoryDto.builder()
                .type(PetType.valueOf(type))
                .name(this.name)
                .build();
    }
}
