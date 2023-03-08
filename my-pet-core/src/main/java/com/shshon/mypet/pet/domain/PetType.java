package com.shshon.mypet.pet.domain;

import com.shshon.mypet.common.domain.EnumType;

public enum PetType implements EnumType {
    DOG("강아지"),
    CAT("고양이");

    private final String text;

    PetType(String text) {
        this.text = text;
    }

    @Override
    public String getId() {
        return this.name();
    }

    @Override
    public String getText() {
        return this.text;
    }
}
