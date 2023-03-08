package com.shshon.mypet.pet.domain;

import com.shshon.mypet.pet.dto.PetDto;

import java.util.Optional;

public interface PetRepositoryCustom {

    Optional<PetDto> findByMemberId(Long memberId);
}
