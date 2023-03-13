package com.shshon.mypet.pet.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import com.shshon.mypet.common.domain.DeleteHistory;
import com.shshon.mypet.common.domain.Deleteable;
import com.shshon.mypet.image.domain.ImageMetaData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pets")
@Where(clause = "is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pet extends BaseTimeEntity
        implements Deleteable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column
    private LocalDate birthDay;

    @Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
    private PetGender gender;

    @Column(nullable = false)
    private Integer bodyWeight;

    @Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
    private PetBodyType bodyType;

    private DeleteHistory deleteHistory = DeleteHistory.nonDeleted();

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private PetImage petImage;

    @Builder
    public Pet(Long memberId,
               Long categoryId,
               PetImage petImage,
               String name,
               LocalDate birthDay,
               PetGender gender,
               Integer bodyWeight,
               PetBodyType bodyType) {
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.petImage = petImage;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.bodyWeight = bodyWeight;
        this.bodyType = bodyType;
    }

    public void changePetImage(ImageMetaData imageMetaData) {
        this.petImage = PetImage.builder()
                .pet(this)
                .imageMetaData(imageMetaData)
                .build();
    }

    @Override
    public void delete() {
        this.deleteHistory.writeDeleteHistory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", gender=" + gender +
                ", bodyWeight=" + bodyWeight +
                ", bodyType=" + bodyType +
                '}';
    }
}
