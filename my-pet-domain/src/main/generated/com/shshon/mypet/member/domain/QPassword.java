package com.shshon.mypet.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPassword is a Querydsl query type for Password
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPassword extends BeanPath<Password> {

    private static final long serialVersionUID = 238219460L;

    public static final QPassword password1 = new QPassword("password1");

    public final StringPath password = createString("password");

    public QPassword(String variable) {
        super(Password.class, forVariable(variable));
    }

    public QPassword(Path<? extends Password> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPassword(PathMetadata metadata) {
        super(Password.class, metadata);
    }

}

