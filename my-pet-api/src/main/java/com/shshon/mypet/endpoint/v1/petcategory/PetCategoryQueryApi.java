package com.shshon.mypet.endpoint.v1.petcategory;

import com.shshon.mypet.endpoint.v1.petcategory.response.PetCategoryResponse;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.application.PetCategoryQueryService;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PetCategoryQueryApi {

    private final PetCategoryQueryService petCategoryQueryService;

    @GetMapping("/v1/pets/category")
    public List<PetCategoryResponse> findPetCategoryByPetType(@RequestParam("petType") PetType type) {
        List<PetCategoryDto> petCategories = petCategoryQueryService.findPetCategoryByPetType(type);
        return PetCategoryResponse.from(petCategories);
    }
}
