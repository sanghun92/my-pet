package com.shshon.mypet.petcategory.service;

import com.shshon.mypet.petcategory.domain.PetCategory;
import com.shshon.mypet.petcategory.domain.PetCategoryRepository;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.dto.PetCategoryDto;
import com.shshon.mypet.petcategory.exception.PetCategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PetCategoryQueryService {

    private final PetCategoryRepository petCategoryRepository;

    public PetCategoryQueryService(PetCategoryRepository petCategoryRepository) {
        this.petCategoryRepository = petCategoryRepository;
    }

    public List<PetCategoryDto> findPetCategoryByPetType(PetType type) {
        List<PetCategory> petCategories = petCategoryRepository.findByPetType(type);
        return PetCategoryDto.from(petCategories);
    }

    public PetCategoryDto findById(Long categoryId) {
        PetCategory petCategory = petCategoryRepository.findById(categoryId)
                .orElseThrow(PetCategoryNotFoundException::new);
        return PetCategoryDto.from(petCategory);
    }
}
