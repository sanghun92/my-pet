package com.shshon.mypet.pet.application;

import com.shshon.mypet.pet.domain.*;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.dto.PetImageDto;
import com.shshon.mypet.petcategory.domain.PetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PetQueryServiceTest {

    @Mock
    private PetRepository petRepository;
    @InjectMocks
    private PetQueryService petQueryService;
    private PetCategory category;
    private PetImage petImage;

    @BeforeEach
    void setUP() {
        category = PetCategory.builder()
                .name("샴")
                .type(PetType.CAT)
                .build();

        petImage = PetImage.builder().build();
    }

    @Test
    @DisplayName("회원 반려동물 조회 요청시 등록 된 반려동물 정보를 반환한다")
    void findMyPetTest() {
        // given
        long memberId = 1L;
        PetDto pet = PetDto.builder()
                .memberId(memberId)
                .categoryId(category.getId())
                .petImage(PetImageDto.from(petImage))
                .name("루루")
                .birthDay(LocalDate.now())
                .gender(PetGender.MALE)
                .bodyWeight(3)
                .bodyType(PetBodyType.NORMAL)
                .build();
        given(petRepository.findByMemberId(memberId)).willReturn(Optional.of(pet));

        // when
        PetDto myPet = petQueryService.findMyPet(memberId);

        // then
        assertThat(myPet).isNotNull();
    }
}
