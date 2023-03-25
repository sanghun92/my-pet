package com.shshon.mypet.image.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageMetaData is a Querydsl query type for ImageMetaData
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QImageMetaData extends BeanPath<ImageMetaData> {

    private static final long serialVersionUID = 1633232550L;

    public static final QImageMetaData imageMetaData = new QImageMetaData("imageMetaData");

    public final com.shshon.mypet.common.domain.QBaseTimeEntity _super = new com.shshon.mypet.common.domain.QBaseTimeEntity(this);

    public final StringPath contentType = createString("contentType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public QImageMetaData(String variable) {
        super(ImageMetaData.class, forVariable(variable));
    }

    public QImageMetaData(Path<? extends ImageMetaData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageMetaData(PathMetadata metadata) {
        super(ImageMetaData.class, metadata);
    }

}

