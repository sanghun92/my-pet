package com.shshon.mypet.pet.event;

import com.shshon.mypet.image.application.ImageService;
import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.image.dto.ImageDto;
import com.shshon.mypet.pet.domain.Pet;
import com.shshon.mypet.pet.domain.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PetRegisterEventListenerTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private PetRegisterEventListener eventListener;

    @Test
    @DisplayName("반려 동물 등록시 이미지를 등록한다")
    void uploadPetImageTest() {
        // given
        Pet pet = Pet.builder().build();
        ImageDto imageDto = ImageDto.builder().name("샘플.jpg").build();
        ImageMetaData imageMetaData = ImageMetaData.builder()
                .path(UUID.randomUUID().toString())
                .name("샘플.jpg")
                .contentType("image")
                .size(0L)
                .build();

        given(petRepository.findById(any(Long.class))).willReturn(Optional.of(pet));
        given(imageService.upload(any(ImageDto.class))).willReturn(imageMetaData);

        // when
        PetRegisterEvent event = PetRegisterEvent.builder()
                .petId(1L)
                .image(imageDto)
                .build();
        eventListener.uploadPetImage(event);

        // then
        then(petRepository).should(times(1)).findById(any(Long.class));
        then(imageService).should(times(1)).upload(any(ImageDto.class));
    }
}
