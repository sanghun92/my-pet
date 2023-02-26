package com.shshon.mypet.pet.service;

import com.shshon.mypet.pet.domain.PetCategory;
import com.shshon.mypet.pet.domain.PetCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadPetCategoryService {

    private final PetCategoryRepository petCategoryRepository;

    public ReadPetCategoryService(PetCategoryRepository petCategoryRepository) {
        this.petCategoryRepository = petCategoryRepository;
    }

    public List<PetCategory> findPetCategoryByRootName(String rootName) {
        return petCategoryRepository.findByName(rootName);
    }
}
