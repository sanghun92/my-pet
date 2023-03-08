package com.shshon.mypet.endpoint.v1.pet.response;

import com.shshon.mypet.endpoint.v1.ResponseV1;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.dto.PetImageDto;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@ResponseV1
@Builder
@Getter
public class PetResponse {

    private final Long id;
    private final PetCategoryResponse category;
    private final String petImageUrl;
    private final String petName;
    private final LocalDate birthDay;
    private final PetGender gender;
    private final Integer bodyWeight;
    private final PetBodyType bodyType;

    public static PetResponse from(PetDto pet, PetCategoryDto petCategoryDto) {
        return PetResponse.builder()
                .id(pet.id())
                .category(PetCategoryResponse.from(petCategoryDto))
                .petImageUrl(toPetImageUrl(pet.petImage()))
                .petName(pet.name())
                .birthDay(pet.birthDay())
                .gender(pet.gender())
                .bodyWeight(pet.bodyWeight())
                .bodyType(pet.bodyType())
                .build();
    }

    private static String toPetImageUrl(PetImageDto petImage) {
        if(petImage == null) {
            return null;
        }

        return "test";
    }
}
