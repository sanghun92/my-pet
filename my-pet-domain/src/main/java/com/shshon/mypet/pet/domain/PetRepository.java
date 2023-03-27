package com.shshon.mypet.pet.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long>, PetRepositoryCustom {
    boolean existsByName(String name);
}
