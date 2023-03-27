package com.shshon.mypet.petcategory.domain;

import com.shshon.mypet.pet.domain.PetType;

import java.util.List;

public interface PetCategoryRepositoryCustom {

    List<PetCategory> findByPetType(PetType type);
}
