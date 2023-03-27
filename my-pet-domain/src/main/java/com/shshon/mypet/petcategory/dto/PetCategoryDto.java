package com.shshon.mypet.petcategory.dto;

import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.domain.PetCategory;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PetCategoryDto(Long id,
                             PetType type,
                             String name,
                             PetCategoryDto parent) {
    public PetCategoryDto(Long id, PetType type, String name) {
        this(id, type, name, null);
    }

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

        return PetCategoryDto.from(petCategory.getParent());
    }

    public static List<PetCategoryDto> from(List<PetCategory> petCategories) {
        return petCategories.stream()
                .map(PetCategoryDto::from)
                .collect(Collectors.toList());
    }

    public PetCategory toEntity() {
        return PetCategory.builder()
                .name(this.name)
                .type(this.type)
                .parent(toParentCategoryEntity(parent))
                .build();
    }

    private PetCategory toParentCategoryEntity(PetCategoryDto parent) {
        if(parent == null) {
            return null;
        }
        return toEntity();
    }
}
