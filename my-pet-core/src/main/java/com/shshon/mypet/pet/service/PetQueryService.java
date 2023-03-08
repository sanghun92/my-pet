package com.shshon.mypet.pet.service;

import com.shshon.mypet.pet.domain.PetRepository;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.exception.PetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetQueryService {

    private final PetRepository petRepository;

    public PetDto findMyPet(Long memberId) {
        return petRepository.findByMemberId(memberId)
                .orElseThrow(PetNotFoundException::new);
    }
}
