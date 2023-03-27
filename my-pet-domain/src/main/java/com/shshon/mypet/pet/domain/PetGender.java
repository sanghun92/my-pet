package com.shshon.mypet.pet.domain;

import com.shshon.mypet.common.domain.EnumType;

public enum PetGender implements EnumType {
    MALE("수컷"),
    FEMALE("암컷");

    private final String text;

    PetGender(String text) {
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
