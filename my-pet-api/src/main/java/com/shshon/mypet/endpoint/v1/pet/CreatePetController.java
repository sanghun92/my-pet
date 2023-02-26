package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.adaptor.image.ImageUploadAdaptor;
import com.shshon.mypet.auth.domain.AuthenticationPrincipal;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.pet.request.CreatePetRequest;
import com.shshon.mypet.pet.service.CreatePetService;
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
public class CreatePetController {

    private final CreatePetService createPetService;
    private final ImageUploadAdaptor imageUploadAdaptor;

    @PostMapping(value = PetPaths.CREATE_PET,
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> enrollMyPet(@AuthenticationPrincipal LoginMember member,
                                         @RequestPart(value = "request") @Valid CreatePetRequest request,
                                         @RequestPart(value = "petImage", required = false) MultipartFile petImage) {
        createPetService.enrollMyPet(
                member.getId(),
                request.getCategoryId(),
                request.toMyPetDto(),
                imageUploadAdaptor.toImageUpload(petImage)
        );
        return ResponseEntity.created(URI.create("v1/pet/mine")).build();
    }
}
