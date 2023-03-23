package com.shshon.mypet.endpoint.v1.petcategory;

import com.shshon.mypet.endpoint.v1.petcategory.request.ParentPetCategoryRegisterRequest;
import com.shshon.mypet.petcategory.application.PetCategoryRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class PetCategoryServiceApi {

    private final PetCategoryRegisterService petCategoryRegisterService;

    @PostMapping("/v1/pets/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerParentPetCategory(ParentPetCategoryRegisterRequest request) {
        petCategoryRegisterService.registerParentPetCategory(request.toDto());
    }

    @PostMapping("/v1/pets/category/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerChildPetCategory(@PathVariable("id") Long parentId, String childPetCategoryName) {
        petCategoryRegisterService.registerChildPetCategory(parentId, childPetCategoryName);
    }
}
