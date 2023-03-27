package com.shshon.mypet.pet.domain;

import com.shshon.mypet.common.domain.EnumType;

public enum PetBodyType implements EnumType {
    SLIM("슬림"),
    NORMAL("일반"),
    CHUBBY("통통");

    private final String text;

    PetBodyType(String text) {
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
