package com.shshon.mypet.pet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPetImage is a Querydsl query type for PetImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetImage extends EntityPathBase<PetImage> {

    private static final long serialVersionUID = 2146253860L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPetImage petImage = new QPetImage("petImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.shshon.mypet.image.domain.QImageMetaData imageMetaData;

    public final QPet pet;

    public QPetImage(String variable) {
        this(PetImage.class, forVariable(variable), INITS);
    }

    public QPetImage(Path<? extends PetImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPetImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPetImage(PathMetadata metadata, PathInits inits) {
        this(PetImage.class, metadata, inits);
    }

    public QPetImage(Class<? extends PetImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.imageMetaData = inits.isInitialized("imageMetaData") ? new com.shshon.mypet.image.domain.QImageMetaData(forProperty("imageMetaData")) : null;
        this.pet = inits.isInitialized("pet") ? new QPet(forProperty("pet"), inits.get("pet")) : null;
    }

}

