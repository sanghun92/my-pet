package com.shshon.mypet.image.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageMetaData extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID imageKey;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Builder
    public ImageMetaData(String imageKey, String name, String contentType, Long size) {
        this.imageKey = UUID.fromString(imageKey);
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }
}
