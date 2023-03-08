package com.shshon.mypet.pet.event;

import com.shshon.mypet.image.application.ImageService;
import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.pet.domain.Pet;
import com.shshon.mypet.pet.domain.PetRepository;
import com.shshon.mypet.pet.exception.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PetRegisterEventListener {

    private final PetRepository petRepository;
    private final ImageService imageService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void uploadPetImage(PetRegisterEvent event) {
        if(isInValidUploadPetImageEvent(event)) {
            return;
        }

        Pet pet = petRepository.findById(event.petId())
                .orElseThrow(PetNotFoundException::new);
        ImageMetaData imageMetaData = imageService.upload(event.image());
        pet.changePetImage(imageMetaData);
    }

    private boolean isInValidUploadPetImageEvent(PetRegisterEvent event) {
        if(!event.hasImage()) {
            return true;
        }

        return false;
    }
}
