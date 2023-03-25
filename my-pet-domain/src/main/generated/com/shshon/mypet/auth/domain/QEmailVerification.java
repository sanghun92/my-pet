package com.shshon.mypet.auth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailVerification is a Querydsl query type for EmailVerification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailVerification extends EntityPathBase<EmailVerification> {

    private static final long serialVersionUID = 573096896L;

    public static final QEmailVerification emailVerification = new QEmailVerification("emailVerification");

    public final com.shshon.mypet.common.domain.QBaseTimeEntity _super = new com.shshon.mypet.common.domain.QBaseTimeEntity(this);

    public final ComparablePath<java.util.UUID> code = createComparable("code", java.util.UUID.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final DateTimePath<java.time.LocalDateTime> verifiedAt = createDateTime("verifiedAt", java.time.LocalDateTime.class);

    public QEmailVerification(String variable) {
        super(EmailVerification.class, forVariable(variable));
    }

    public QEmailVerification(Path<? extends EmailVerification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailVerification(PathMetadata metadata) {
        super(EmailVerification.class, metadata);
    }

}

