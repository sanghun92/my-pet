package com.shshon.mypet.member.domain;

import com.shshon.mypet.member.exception.AlreadyCertificatedMemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MemberCertification {

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private final UUID certificationCode;

    private final LocalDateTime certificatedAt;

    protected MemberCertification() {
        this.certificationCode = null;
        this.certificatedAt = null;
    }

    private MemberCertification(UUID certificationCode, LocalDateTime certificatedAt) {
        this.certificationCode = certificationCode;
        this.certificatedAt = certificatedAt;
    }

    public static MemberCertification randomCode() {
        return new MemberCertification(UUID.randomUUID(), null);
    }

    private static MemberCertification certificate(UUID code) {
        return new MemberCertification(code, LocalDateTime.now());
    }

    public String getCode() {
        return this.certificationCode.toString();
    }

    public MemberCertification onCertificate() {
        if(isCertificated()) {
            throw new AlreadyCertificatedMemberException();
        }
        return certificate(this.certificationCode);
    }

    public boolean isCertificated() {
        return this.certificatedAt != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberCertification that = (MemberCertification) o;
        return Objects.equals(certificationCode, that.certificationCode)
                && Objects.equals(certificatedAt, that.certificatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificationCode, certificatedAt);
    }

    @Override
    public String toString() {
        return "MemberCertification{" +
                "certificationCode=" + certificationCode +
                ", certificatedAt=" + certificatedAt +
                '}';
    }
}
