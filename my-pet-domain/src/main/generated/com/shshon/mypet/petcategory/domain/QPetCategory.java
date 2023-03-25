package com.shshon.mypet.petcategory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPetCategory is a Querydsl query type for PetCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetCategory extends EntityPathBase<PetCategory> {

    private static final long serialVersionUID = 1464568791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPetCategory petCategory = new QPetCategory("petCategory");

    public final com.shshon.mypet.common.domain.QBaseTimeEntity _super = new com.shshon.mypet.common.domain.QBaseTimeEntity(this);

    public final ListPath<PetCategory, QPetCategory> child = this.<PetCategory, QPetCategory>createList("child", PetCategory.class, QPetCategory.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final QPetCategory parent;

    public final EnumPath<com.shshon.mypet.pet.domain.PetType> type = createEnum("type", com.shshon.mypet.pet.domain.PetType.class);

    public QPetCategory(String variable) {
        this(PetCategory.class, forVariable(variable), INITS);
    }

    public QPetCategory(Path<? extends PetCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPetCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPetCategory(PathMetadata metadata, PathInits inits) {
        this(PetCategory.class, metadata, inits);
    }

    public QPetCategory(Class<? extends PetCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QPetCategory(forProperty("parent"), inits.get("parent")) : null;
    }

}

