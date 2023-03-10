package com.shshon.mypet.pet.domain;

import com.shshon.mypet.image.domain.ImageMetaData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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

    public byte[] getContentBytes() {
        if(this.contents == null) {
            return new byte[0];
        }

        try {
            return this.contents.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetImage petImage = (PetImage) o;
        return Objects.equals(id, petImage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
