package com.shshon.mypet.pet.event;

import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.pet.domain.Pet;
import lombok.Builder;

@Builder
public record CreatePetEvent(
    Long petId,
    ImageDto petImage
) {
    public static CreatePetEvent of(Pet pet, ImageDto petImage) {
        return CreatePetEvent.builder()
                .petId(pet.getId())
                .petImage(petImage)
                .build();
    }
}
