package com.shshon.mypet.endpoint.v1.pet.response;

import com.shshon.mypet.endpoint.v1.response.ResponseV1;
import com.shshon.mypet.pet.dto.PetImageDto;
import lombok.Builder;
import lombok.Getter;

@ResponseV1
@Builder
public record PetImageResponse(
        byte[] image
) {
    public static PetImageResponse from(PetImageDto imageDto) {
        return PetImageResponse.builder()
                .image(imageDto.getContents())
                .build();
    }
}
