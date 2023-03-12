package com.shshon.mypet.endpoint.v1.pet.response;

import com.shshon.mypet.endpoint.v1.petcategory.response.PetCategoryResponse;
import com.shshon.mypet.endpoint.v1.response.ResponseV1;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.dto.PetImageDto;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import lombok.Builder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@ResponseV1
@Builder
public record PetResponse (

    Long id,
    PetCategoryResponse category,
    String petImageUrl,
    String petName,
    LocalDate birthDay,
    PetGender gender,
    Integer bodyWeight,
    PetBodyType bodyType
) {

    public static PetResponse from(PetDto pet, PetCategoryDto petCategoryDto) {
        return PetResponse.builder()
                .id(pet.id())
                .category(PetCategoryResponse.from(petCategoryDto))
                .petImageUrl(toPetImageUrl(pet.id(), pet.petImage()))
                .petName(pet.name())
                .birthDay(pet.birthDay())
                .gender(pet.gender())
                .bodyWeight(pet.bodyWeight())
                .bodyType(pet.bodyType())
                .build();
    }

    private static String toPetImageUrl(Long petId, PetImageDto petImage) {
        if(petImage == null) {
            return null;
        }

        return UriComponentsBuilder.fromPath("/v1/pets/{petId}/images")
                .build(petId)
                .toString();
    }
}
