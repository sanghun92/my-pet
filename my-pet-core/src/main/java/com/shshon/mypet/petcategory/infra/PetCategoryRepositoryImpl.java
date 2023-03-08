package com.shshon.mypet.petcategory.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shshon.mypet.pet.domain.PetType;
import com.shshon.mypet.petcategory.domain.PetCategory;
import com.shshon.mypet.petcategory.domain.PetCategoryRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.shshon.mypet.petcategory.domain.QPetCategory.petCategory;

@Repository
@RequiredArgsConstructor
public class PetCategoryRepositoryImpl implements PetCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PetCategory> findByPetType(PetType type) {
        List<PetCategory> categories = queryFactory
                .selectFrom(petCategory)
                .where(
                        petCategory.type.eq(type)
                )
                .orderBy(petCategory.id.asc())
                .fetch();

        return categories.stream()
                .filter(PetCategory::hasParent)
                .collect(Collectors.toList());
    }
}
