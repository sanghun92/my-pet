package com.shshon.mypet.member.domain;

import com.shshon.mypet.util.SHA256EncryptUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Password {

    @Column(nullable = false)
    private final String password;

    protected Password() {
        this.password = null;
    }

    private Password(String password) {
        this.password = password;
    }

    public static Password of(String password) {
        return new Password(SHA256EncryptUtils.encrypt(password));
    }

    public boolean matches(String otherPassword) {
        return SHA256EncryptUtils.match(otherPassword, this.password);
    }

    public String getValue() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return password;
    }
}
