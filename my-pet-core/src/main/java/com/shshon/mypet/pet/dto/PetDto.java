package com.shshon.mypet.pet.dto;

import com.shshon.mypet.pet.domain.Pet;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.domain.PetType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetDto(Long id,
                     Long memberId,
                     PetCategoryDto category,
                     PetType type,
                     String name,
                     LocalDate birthDay,
                     PetGender gender,
                     Integer bodyWeight,
                     PetBodyType bodyType) {

    public Pet toEntity() {
        return Pet.builder()
                .memberId(memberId)
                .name(name)
                .birthDay(birthDay)
                .gender(gender)
                .bodyType(bodyType)
                .bodyWeight(bodyWeight)
                .build();
    }
}
