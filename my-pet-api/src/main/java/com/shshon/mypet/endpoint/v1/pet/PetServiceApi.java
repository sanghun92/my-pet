package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.auth.domain.AuthenticationMember;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.pet.request.PetRegisterRequest;
import com.shshon.mypet.mapper.image.ImageDtoMapper;
import com.shshon.mypet.pet.service.PetRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PetServiceApi {

    private final PetRegisterService petRegisterService;
    private final ImageDtoMapper imageDtoMapper;

    @PostMapping(value = "/v1/pets",
            consumes = { MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> registerMyPet(@AuthenticationMember LoginMember member,
                                           @RequestPart(value = "request") @Valid PetRegisterRequest request,
                                           @RequestPart(value = "petImage", required = false) MultipartFile petImage) {
        petRegisterService.registerMyPet(
                member.id(),
                request.categoryId(),
                request.toMyPetDto(),
                imageDtoMapper.toImageMetaData(petImage)
        );
        return ResponseEntity.created(URI.create("v1/pet/mine")).build();
    }
}
