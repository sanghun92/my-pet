package com.shshon.mypet.petcategory.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import com.shshon.mypet.pet.domain.PetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PetCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6, nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_pet_category_parent"))
    private PetCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<PetCategory> child = new ArrayList<>();

    @Builder
    public PetCategory(PetType type, String name, PetCategory parent) {
        this.type = type;
        this.name = name;
        this.parent = parent;
    }

    public static PetCategory createParent(PetType type, String name) {
        return PetCategory.builder()
                .type(type)
                .name(name)
                .build();
    }

    public void addChildCategory(String name) {
        PetCategory child = PetCategory.builder()
                .type(this.type)
                .name(name)
                .parent(this)
                .build();
        this.child.add(child);
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetCategory that = (PetCategory) o;
        return Objects.equals(id, that.id)
                && type == that.type
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name);
    }

    @Override
    public String toString() {
        return "PetCategory{" +
                "memberId=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
