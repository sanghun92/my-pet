package com.shshon.mypet.pet.domain;

import com.shshon.mypet.image.domain.ImageMetaData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Embedded
    private ImageMetaData imageMetaData;

    @Transient
    private InputStream contents;

    @Builder
    public PetImage(Pet pet, ImageMetaData imageMetaData, InputStream contents) {
        this.pet = pet;
        this.imageMetaData = imageMetaData;
        this.contents = contents;
    }
}
