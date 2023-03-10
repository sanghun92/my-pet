package com.shshon.mypet.endpoint.v1.petcategory.response;

import com.shshon.mypet.endpoint.v1.ResponseV1;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@ResponseV1
@Builder
@Getter
public class PetCategoryResponse {

    private final PetType type;
    private final String name;

    public static PetCategoryResponse from(PetCategoryDto petCategory) {
        return PetCategoryResponse.builder()
                .type(petCategory.type())
                .name(petCategory.name())
                .build();
    }

    public static List<PetCategoryResponse> from(List<PetCategoryDto> petCategories) {
        return petCategories.stream()
                .map(PetCategoryResponse::from)
                .collect(Collectors.toList());
    }
}
