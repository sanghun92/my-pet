package com.shshon.mypet.endpoint.v1.pet.response;

import com.shshon.mypet.endpoint.v1.ResponseV1;
import com.shshon.mypet.pet.dto.PetImageDto;
import lombok.Builder;
import lombok.Getter;

@ResponseV1
@Getter
@Builder
public class PetImageResponse {

    private final byte[] image;

    public static PetImageResponse from(PetImageDto imageDto) {
        return PetImageResponse.builder()
                .image(imageDto.getContents())
                .build();
    }
}
