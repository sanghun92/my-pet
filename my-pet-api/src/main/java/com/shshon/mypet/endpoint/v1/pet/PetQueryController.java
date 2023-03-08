package com.shshon.mypet.endpoint.v1.pet;

import com.shshon.mypet.auth.domain.AuthenticationPrincipal;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.endpoint.v1.pet.response.PetResponse;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.service.PetQueryService;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import com.shshon.mypet.petcategory.service.PetCategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PetQueryController {

    private final PetQueryService petQueryService;
    private final PetCategoryQueryService petCategoryQueryService;

    @GetMapping(PetPaths.FIND_MY_PET)
    public PetResponse findMyPet(@AuthenticationPrincipal LoginMember member) {
        PetDto pet = petQueryService.findMyPet(member.getId());
        PetCategoryDto petCategoryDto = petCategoryQueryService.findById(pet.categoryId());
        return PetResponse.from(pet, petCategoryDto);
    }
}
