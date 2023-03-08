package com.shshon.mypet.endpoint.v1.petcategory.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = ChildPetCategoryRegisterRequest.ChildPetCategoryRegisterRequestBuilder.class)
public class ChildPetCategoryRegisterRequest {

    private final String name;
    private final Long parentId;
}
