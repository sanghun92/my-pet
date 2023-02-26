package com.shshon.mypet.pet.service;

import com.shshon.mypet.common.exception.AlreadyCreatedEntityException;
import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.pet.domain.Pet;
import com.shshon.mypet.pet.domain.PetCategory;
import com.shshon.mypet.pet.domain.PetCategoryRepository;
import com.shshon.mypet.pet.domain.PetRepository;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.event.CreatePetEvent;
import com.shshon.mypet.pet.exception.PetCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePetService {

    private final PetRepository petRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void enrollMyPet(Long memberId,
                            Long categoryId,
                            PetDto petDto,
                            @Nullable ImageDto petImage) {
        validateNewPet(petDto);

        Pet pet = petRepository.save(toPet(memberId, categoryId, petDto));
        eventPublisher.publishEvent(CreatePetEvent.of(pet, petImage));
    }

    private void validateNewPet(PetDto petDto) {
        if(petRepository.existsByName(petDto.name())) {
            throw new AlreadyCreatedEntityException("'" + petDto.name() + "' 은 이미 등록 된 이름입니다.");
        }
    }

    private Pet toPet(Long memberId, Long categoryId, PetDto petDto) {
        PetCategory petCategory = petCategoryRepository.findById(categoryId)
                .orElseThrow(PetCategoryNotFoundException::new);
        return Pet.builder()
                .memberId(memberId)
                .category(petCategory)
                .name(petDto.name())
                .birthDay(petDto.birthDay())
                .gender(petDto.gender())
                .bodyType(petDto.bodyType())
                .bodyWeight(petDto.bodyWeight())
                .build();
    }
}
