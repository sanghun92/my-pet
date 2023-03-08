package com.shshon.mypet.endpoint.v1.petcategory;

import com.shshon.mypet.endpoint.v1.petcategory.request.ParentPetCategoryRegisterRequest;
import com.shshon.mypet.petcategory.service.PetCategoryRegisterService;
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
public class PetCategoryServiceController {

    private final PetCategoryRegisterService petCategoryRegisterService;

    @PostMapping(PetCategoryPaths.REGISTER_PARENT_PET_CATEGORY)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerParentPetCategory(ParentPetCategoryRegisterRequest request) {
        petCategoryRegisterService.registerParentPetCategory(request.toDto());
    }

    @PostMapping(PetCategoryPaths.REGISTER_CHILD_PET_CATEGORY)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerChildPetCategory(@PathVariable("id") Long parentId, String childPetCategoryName) {
        petCategoryRegisterService.registerChildPetCategory(parentId, childPetCategoryName);
    }
}
