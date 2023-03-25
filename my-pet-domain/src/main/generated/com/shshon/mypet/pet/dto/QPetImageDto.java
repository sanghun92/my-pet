package com.shshon.mypet.pet.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.shshon.mypet.pet.dto.QPetImageDto is a Querydsl Projection type for PetImageDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPetImageDto extends ConstructorExpression<PetImageDto> {

    private static final long serialVersionUID = 1492152754L;

    public QPetImageDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<? extends com.shshon.mypet.image.domain.ImageMetaData> imageMetaData) {
        super(PetImageDto.class, new Class<?>[]{long.class, com.shshon.mypet.image.domain.ImageMetaData.class}, id, imageMetaData);
    }

}

