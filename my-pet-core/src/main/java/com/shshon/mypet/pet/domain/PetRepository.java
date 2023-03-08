package com.shshon.mypet.pet.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long>, PetRepositoryCustom {
    boolean existsByName(String name);
}
