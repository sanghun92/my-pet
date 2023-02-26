package com.shshon.mypet.pet.domain;

import java.util.List;

public interface PetCategoryRepositoryCustom {

    List<PetCategory> findByName(String name);
}
