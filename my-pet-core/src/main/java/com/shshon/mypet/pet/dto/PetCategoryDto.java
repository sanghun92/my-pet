package com.shshon.mypet.pet.dto;

import com.shshon.mypet.pet.domain.PetCategory;
import com.shshon.mypet.pet.domain.PetType;
import lombok.Builder;

@Builder
public record PetCategoryDto(Long id,
                             PetType type,
                             String name,
                             PetCategoryDto parent) {
    public static PetCategoryDto from(PetCategory petCategory) {
        return PetCategoryDto.builder()
                .id(petCategory.getId())
                .type(petCategory.getType())
                .name(petCategory.getName())
                .parent(toParentCategoryDto(petCategory))
                .build();
    }

    private static PetCategoryDto toParentCategoryDto(PetCategory petCategory) {
        if(petCategory.getParent() == null) {
            return null;
        }

        return PetCategoryDto.from(petCategory);
    }
}
