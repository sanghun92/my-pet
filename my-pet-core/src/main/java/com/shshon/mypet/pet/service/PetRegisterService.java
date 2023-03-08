package com.shshon.mypet.pet.service;

import com.shshon.mypet.common.exception.AlreadyCreatedEntityException;
import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.pet.domain.Pet;
import com.shshon.mypet.petcategory.domain.PetCategoryRepository;
import com.shshon.mypet.pet.domain.PetRepository;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.event.PetRegisterEvent;
import com.shshon.mypet.petcategory.exception.PetCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetRegisterService {

    private final PetRepository petRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void registerMyPet(Long memberId,
                              Long categoryId,
                              PetDto petDto,
                              @Nullable ImageDto imageDto) {
        validateNewPet(categoryId, petDto);

        Pet pet = petRepository.save(toPet(memberId, categoryId, petDto));
        eventPublisher.publishEvent(PetRegisterEvent.of(pet, imageDto));
    }

    private void validateNewPet(Long categoryId, PetDto petDto) {
        if(petRepository.existsByName(petDto.name())) {
            throw new AlreadyCreatedEntityException("'" + petDto.name() + "' 은 이미 등록 된 이름입니다.");
        }

        if(!petCategoryRepository.existsById(categoryId)) {
            throw new PetCategoryNotFoundException();
        }
    }

    private Pet toPet(Long memberId, Long categoryId, PetDto petDto) {
        return Pet.builder()
                .memberId(memberId)
                .categoryId(categoryId)
                .name(petDto.name())
                .birthDay(petDto.birthDay())
                .gender(petDto.gender())
                .bodyType(petDto.bodyType())
                .bodyWeight(petDto.bodyWeight())
                .build();
    }
}
