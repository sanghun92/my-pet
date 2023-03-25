package com.shshon.mypet.pet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPet is a Querydsl query type for Pet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPet extends EntityPathBase<Pet> {

    private static final long serialVersionUID = -1646489737L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPet pet = new QPet("pet");

    public final com.shshon.mypet.common.domain.QBaseTimeEntity _super = new com.shshon.mypet.common.domain.QBaseTimeEntity(this);

    public final DatePath<java.time.LocalDate> birthDay = createDate("birthDay", java.time.LocalDate.class);

    public final EnumPath<PetBodyType> bodyType = createEnum("bodyType", PetBodyType.class);

    public final NumberPath<Integer> bodyWeight = createNumber("bodyWeight", Integer.class);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.shshon.mypet.common.domain.QDeleteHistory deleteHistory;

    public final EnumPath<PetGender> gender = createEnum("gender", PetGender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final QPetImage petImage;

    public QPet(String variable) {
        this(Pet.class, forVariable(variable), INITS);
    }

    public QPet(Path<? extends Pet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPet(PathMetadata metadata, PathInits inits) {
        this(Pet.class, metadata, inits);
    }

    public QPet(Class<? extends Pet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deleteHistory = inits.isInitialized("deleteHistory") ? new com.shshon.mypet.common.domain.QDeleteHistory(forProperty("deleteHistory")) : null;
        this.petImage = inits.isInitialized("petImage") ? new QPetImage(forProperty("petImage"), inits.get("petImage")) : null;
    }

}

