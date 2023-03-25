package com.shshon.mypet.auth.domain;

import com.shshon.mypet.auth.exception.AlreadyVerifiedEmailException;
import com.shshon.mypet.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID code;

    private LocalDateTime verifiedAt;

    private EmailVerification(String email,
                              UUID code,
                              LocalDateTime verifiedAt) {
        this.email = email;
        this.code = code;
        this.verifiedAt = verifiedAt;
    }

    public static EmailVerification nonCode(String email) {
        return new EmailVerification(email, null, null);
    }

    public void changeCertificationCode() {
        this.code = UUID.randomUUID();
    }

    public String getCode() {
        return this.code.toString();
    }

    public void verify() {
        if (isVerified()) {
            throw new AlreadyVerifiedEmailException();
        }
        this.verifiedAt = LocalDateTime.now();
    }

    public boolean isVerified() {
        return this.verifiedAt != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailVerification that = (EmailVerification) o;
        return Objects.equals(id, that.id)
                && Objects.equals(email, that.email)
                && Objects.equals(code, that.code)
                && Objects.equals(verifiedAt, that.verifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, code, verifiedAt);
    }
}
