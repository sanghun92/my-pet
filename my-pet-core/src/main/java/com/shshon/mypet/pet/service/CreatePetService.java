package com.shshon.mypet.pet.service;

import com.shshon.mypet.common.exception.EntityNotFoundException;
import com.shshon.mypet.pet.domain.*;
import com.shshon.mypet.pet.dto.MyPetDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreatePetService {

    private final PetRepository petRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final PetBodyWeightRepository petBodyWeightRepository;

    public CreatePetService(PetRepository petRepository,
                            PetCategoryRepository petCategoryRepository,
                            PetBodyWeightRepository petBodyWeightRepository) {
        this.petRepository = petRepository;
        this.petCategoryRepository = petCategoryRepository;
        this.petBodyWeightRepository = petBodyWeightRepository;
    }

    public void createMyPet(Long memberId, Long categoryId, MyPetDto myPetDto) {
        petRepository.save(toPet(memberId, categoryId, myPetDto));
    }

    private Pet toPet(Long memberId, Long categoryId, MyPetDto myPetDto) {
        PetCategory petCategory = petCategoryRepository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);
        PetBodyWeight petBodyWeight = petBodyWeightRepository.findById(myPetDto.getBodyWeightId())
                .orElseThrow(EntityNotFoundException::new);
        return Pet.builder()
                .memberId(memberId)
                .category(petCategory)
                .name(myPetDto.getName())
                .birthDay(myPetDto.getBirthDay())
                .gender(myPetDto.getGender())
                .bodyType(myPetDto.getBodyType())
                .build();
    }
}
