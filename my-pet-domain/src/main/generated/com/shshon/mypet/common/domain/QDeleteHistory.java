package com.shshon.mypet.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeleteHistory is a Querydsl query type for DeleteHistory
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeleteHistory extends BeanPath<DeleteHistory> {

    private static final long serialVersionUID = 1743214895L;

    public static final QDeleteHistory deleteHistory = new QDeleteHistory("deleteHistory");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public QDeleteHistory(String variable) {
        super(DeleteHistory.class, forVariable(variable));
    }

    public QDeleteHistory(Path<? extends DeleteHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeleteHistory(PathMetadata metadata) {
        super(DeleteHistory.class, metadata);
    }

}

