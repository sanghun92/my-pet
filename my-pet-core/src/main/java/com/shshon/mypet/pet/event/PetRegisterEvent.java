package com.shshon.mypet.pet.event;

import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.pet.domain.Pet;
import lombok.Builder;

@Builder
public record PetRegisterEvent(
    Long petId,
    ImageDto image
) {
    public static PetRegisterEvent of(Pet pet, ImageDto imageDto) {
        return PetRegisterEvent.builder()
                .petId(pet.getId())
                .image(imageDto)
                .build();
    }

    public boolean hasImage() {
        return image != null;
    }
}
