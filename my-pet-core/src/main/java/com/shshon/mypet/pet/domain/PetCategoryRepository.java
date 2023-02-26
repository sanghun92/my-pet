package com.shshon.mypet.pet.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Long>, PetCategoryRepositoryCustom {
}
