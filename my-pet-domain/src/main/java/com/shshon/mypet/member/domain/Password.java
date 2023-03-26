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
        validatePassword(password);
        return new Password(SHA256EncryptUtils.encrypt(password));
    }

    private static void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("패스워드는 필수값입니다");
        }
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
