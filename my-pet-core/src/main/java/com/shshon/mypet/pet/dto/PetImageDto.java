package com.shshon.mypet.pet.dto;

import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.pet.domain.PetImage;
import lombok.Builder;

@Builder
public record PetImageDto(Long id,
                          ImageMetaData imageMetaData) {
    public static PetImageDto from(PetImage petImage) {
        return PetImageDto.builder()
                .id(petImage.getId())
                .imageMetaData(petImage.getImageMetaData())
                .build();
    }
}
