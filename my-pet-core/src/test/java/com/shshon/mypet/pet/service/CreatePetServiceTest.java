package com.shshon.mypet.pet.service;

import com.shshon.mypet.pet.domain.*;
import com.shshon.mypet.pet.dto.PetCategoryDto;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.event.CreatePetEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreatePetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetCategoryRepository petCategoryRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CreatePetService createPetService;

    @BeforeEach
    void setUp() {
        willDoNothing().given(eventPublisher).publishEvent(any(CreatePetEvent.class));
    }

    @Test
    @DisplayName("회원 반려동물 등록 요청시 등록 후 반려동물 정보를 반환한다")
    void createMyPetTest() {
        // given
        Long memberId = 1L;
        Long categoryId = 1L;
        PetCategory petCategory = PetCategory.builder()
                .type(PetType.CAT)
                .name("샴")
                .build();
        given(petCategoryRepository.findById(categoryId)).willReturn(Optional.of(petCategory));

        PetDto petDto = PetDto.builder()
                .memberId(memberId)
                .type(PetType.CAT)
                .name("루루")
                .birthDay(LocalDate.now())
                .gender(PetGender.MALE)
                .bodyWeight(5)
                .bodyType(PetBodyType.NORMAL)
                .category(PetCategoryDto.from(petCategory))
                .build();
        given(petRepository.save(any(Pet.class))).willReturn(petDto.toEntity());

        // when
        createPetService.enrollMyPet(memberId, categoryId, petDto, null);

        // then
        then(petCategoryRepository).should(times(1)).findById(categoryId);
        then(petRepository).should(times(1)).save(any(Pet.class));
    }
}
