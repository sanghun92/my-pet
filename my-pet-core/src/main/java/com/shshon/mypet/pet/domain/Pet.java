package com.shshon.mypet.pet.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import com.shshon.mypet.image.domain.ImageMetaData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pets")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE pet SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_pet_pet_category"))
    private PetCategory category;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_image_id", foreignKey = @ForeignKey(name = "fk_pet_image"))
    private ImageMetaData petImageMetaData;

    private Boolean isDeleted = Boolean.FALSE;
    @Builder
    public Pet(Long memberId,
               PetCategory category,
               String name,
               LocalDate birthDay,
               PetGender gender,
               Integer bodyWeight,
               PetBodyType bodyType) {
        this.memberId = memberId;
        this.category = category;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.bodyWeight = bodyWeight;
        this.bodyType = bodyType;
    }

    public void changePetImage(ImageMetaData imageMetaData) {
        this.petImageMetaData = imageMetaData;
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
                ", isDeleted=" + isDeleted +
                '}';
    }
}
