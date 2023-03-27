package com.shshon.mypet.petcategory.application;

import com.shshon.mypet.petcategory.domain.PetCategory;
import com.shshon.mypet.petcategory.domain.PetCategoryRepository;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import com.shshon.mypet.petcategory.exception.PetCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetCategoryRegisterService {

    private final PetCategoryRepository petCategoryRepository;

    public void registerParentPetCategory(PetCategoryDto petCategory) {
        petCategoryRepository.save(petCategory.toEntity());
    }

    public void registerChildPetCategory(Long parentId, String childPetCategoryName) {
        PetCategory parentPetCategory = petCategoryRepository.findById(parentId)
                .orElseThrow(PetCategoryNotFoundException::new);
        parentPetCategory.addChildCategory(childPetCategoryName);
    }
}
