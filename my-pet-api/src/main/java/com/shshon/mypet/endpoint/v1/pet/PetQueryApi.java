package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.auth.domain.AuthenticationMember;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.pet.response.PetImageResponse;
import com.shshon.mypet.endpoint.v1.pet.response.PetResponse;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.dto.PetImageDto;
import com.shshon.mypet.pet.service.PetQueryService;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import com.shshon.mypet.petcategory.service.PetCategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PetQueryApi {

    private final PetQueryService petQueryService;
    private final PetCategoryQueryService petCategoryQueryService;

    @GetMapping("/v1/pets/mine")
    public PetResponse findMyPet(@AuthenticationMember LoginMember member) {
        PetDto pet = petQueryService.findMyPet(member.id());
        PetCategoryDto petCategoryDto = petCategoryQueryService.findById(pet.categoryId());
        return PetResponse.from(pet, petCategoryDto);
    }

    @GetMapping(value = "/v1/pets/{petId}/images",
            produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public PetImageResponse findPetImage(@PathVariable("petId") Long petId) {
        PetImageDto imageDto = petQueryService.findPetImage(petId);
        return PetImageResponse.from(imageDto);
    }
}
