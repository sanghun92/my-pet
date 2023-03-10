package com.shshon.mypet.pet.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.pet.domain.PetImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PetImageDto {

    private final Long id;
    private final ImageMetaData imageMetaData;
    private final byte[] contents;

    @QueryProjection
    public PetImageDto(Long id, ImageMetaData imageMetaData) {
        this(id, imageMetaData, null);
    }

    public static PetImageDto from(PetImage petImage) {
        return PetImageDto.builder()
                .id(petImage.getId())
                .imageMetaData(petImage.getImageMetaData())
                .contents(petImage.getContentBytes())
                .build();
    }
}
