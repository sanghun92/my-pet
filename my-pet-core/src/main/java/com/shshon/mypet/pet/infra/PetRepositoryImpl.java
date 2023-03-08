package com.shshon.mypet.pet.infra;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shshon.mypet.image.domain.ImageMetaData;
import com.shshon.mypet.pet.domain.PetRepositoryCustom;
import com.shshon.mypet.pet.dto.PetDto;
import com.shshon.mypet.pet.dto.PetImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.shshon.mypet.image.domain.QImageMetaData.imageMetaData;
import static com.shshon.mypet.pet.domain.QPet.pet;
import static com.shshon.mypet.pet.domain.QPetImage.petImage;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PetDto> findByMemberId(Long memberId) {
        PetDto petDto = queryFactory
                .select(Projections.constructor(PetDto.class,
                        pet.id,
                        pet.memberId,
                        pet.categoryId,
                        Projections.constructor(PetImageDto.class,
                                petImage.id,
                                Projections.constructor(ImageMetaData.class,
                                    imageMetaData.path,
                                    imageMetaData.name,
                                    imageMetaData.contentType,
                                    imageMetaData.size
                                )
                        ),
                        pet.name,
                        pet.birthDay,
                        pet.gender,
                        pet.bodyWeight,
                        pet.bodyType
                ))
                .from(pet)
                .leftJoin(pet.petImage, petImage)
                .where(
                        pet.memberId.eq(memberId)
                )
                .fetchOne();
        return Optional.ofNullable(petDto);
    }
}
