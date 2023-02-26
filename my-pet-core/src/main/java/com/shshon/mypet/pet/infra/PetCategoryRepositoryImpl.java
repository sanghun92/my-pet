package com.shshon.mypet.pet.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shshon.mypet.pet.domain.PetCategory;
import com.shshon.mypet.pet.domain.PetCategoryRepositoryCustom;
import com.shshon.mypet.pet.domain.QPetCategory;

import java.util.List;

public class PetCategoryRepositoryImpl implements PetCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PetCategoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<PetCategory> findByName(String name) {
        QPetCategory parent = new QPetCategory("parent");
        QPetCategory child = new QPetCategory("child");
        return queryFactory
                .selectFrom(parent)
                .from(parent)
                .innerJoin(parent.child, child)
                .fetchJoin()
                .where(
                        parent.parent.isNull(),
                        parent.name.eq(name)
                )
                .orderBy(parent.id.asc(), child.id.asc())
                .fetch();
    }
}
