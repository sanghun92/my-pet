package com.shshon.mypet.pet.dto;

import com.shshon.mypet.pet.domain.Pet;
import com.shshon.mypet.pet.domain.PetBodyType;
import com.shshon.mypet.pet.domain.PetGender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetDto(Long id,
                     Long memberId,
                     Long categoryId,
                     PetImageDto petImage,
                     String name,
                     LocalDate birthDay,
                     PetGender gender,
                     Integer bodyWeight,
                     PetBodyType bodyType) {

    public static PetDto from(Pet pet) {
        return PetDto.builder()
                .id(pet.getId())
                .memberId(pet.getMemberId())
                .categoryId(pet.getCategoryId())
                .petImage(PetImageDto.from(pet.getPetImage()))
                .name(pet.getName())
                .birthDay(pet.getBirthDay())
                .gender(pet.getGender())
                .bodyWeight(pet.getBodyWeight())
                .bodyType(pet.getBodyType())
                .build();
    }

    /*public static PetDto from(PetDto petDto, ImageMetaDataDto imageMetaDataDto) {
        return PetDto.builder()
                .id(petDto.id())
                .memberId(petDto.memberId())
                .category((petDto.category()))
                .petImage(imageMetaDataDto)
                .name(petDto.name())
                .birthDay(petDto.birthDay())
                .gender(petDto.gender())
                .bodyWeight(petDto.bodyWeight())
                .bodyType(petDto.bodyType())
                .build();
    }*/

    public Pet toEntity() {
        return Pet.builder()
                .memberId(memberId)
                .categoryId(categoryId)
                .name(name)
                .birthDay(birthDay)
                .gender(gender)
                .bodyType(bodyType)
                .bodyWeight(bodyWeight)
                .build();
    }
}
