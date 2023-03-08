package com.shshon.mypet.image.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class ImageMetaData extends BaseTimeEntity {

    @Column(nullable = false, unique = true)
    private final String path;

    @Column(length = 50, nullable = false)
    private final String name;

    @Column(length = 20, nullable = false)
    private final String contentType;

    @Column(nullable = false)
    private final Long size;

    protected ImageMetaData() {
        this.path = null;
        this.name = null;
        this.contentType = null;
        this.size = null;
    }

    @Builder
    public ImageMetaData(String path,
                          String name,
                          String contentType,
                          Long size) {
        this.path = path;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageMetaData that = (ImageMetaData) o;
        return Objects.equals(name, that.name) && Objects.equals(contentType, that.contentType) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contentType, size);
    }
}
