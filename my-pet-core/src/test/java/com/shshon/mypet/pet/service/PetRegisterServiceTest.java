package com.shshon.mypet.pet.service;

import com.shshon.mypet.pet.domain.*;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.event.PetRegisterEvent;
import com.shshon.mypet.petcategory.domain.PetCategory;
import com.shshon.mypet.petcategory.domain.PetCategoryRepository;
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
class PetRegisterServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetCategoryRepository petCategoryRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PetRegisterService petRegisterService;

    @BeforeEach
    void setUp() {
        willDoNothing().given(eventPublisher).publishEvent(any(PetRegisterEvent.class));
    }

    @Test
    @DisplayName("회원 반려동물 등록 요청시 등록 후 반려동물 정보를 반환한다")
    void createMyPetTest() {
        // given
        Long memberId = 1L;
        Long categoryId = 1L;

        PetDto petDto = PetDto.builder()
                .memberId(memberId)
                .categoryId(categoryId)
                .name("루루")
                .birthDay(LocalDate.now())
                .gender(PetGender.MALE)
                .bodyWeight(5)
                .bodyType(PetBodyType.NORMAL)
                .build();
        given(petRepository.save(any(Pet.class))).willReturn(petDto.toEntity());
        given(petCategoryRepository.existsById(categoryId)).willReturn(Boolean.TRUE);

        // when
        petRegisterService.registerMyPet(memberId, categoryId, petDto, null);

        // then
        then(petCategoryRepository).should(times(1)).existsById(categoryId);
        then(petRepository).should(times(1)).save(any(Pet.class));
    }
}
