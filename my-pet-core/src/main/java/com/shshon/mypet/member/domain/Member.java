package com.shshon.mypet.member.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import com.shshon.mypet.member.exception.AuthorizationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    @Getter
    private String email;

    @Embedded
    private Password password;

    @Column(length = 30, nullable = false)
    @Getter
    private String nickname;

    @Embedded
    private MemberCertification certification;

    private Member(String email,
                   Password password,
                   String nickname,
                   MemberCertification certification) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.certification = certification;
    }

    public static Member createMember(String email,
                                      String password,
                                      String nickname) {
        return new Member(
                email,
                Password.of(password),
                nickname,
                MemberCertification.randomCode()
        );
    }

    public void authenticate(String password) {
        if(!this.password.matches(password)) {
            throw new AuthorizationException();
        }
    }

    public String getCertificationCode() {
        return this.certification.getCode();
    }

    public boolean isCertificated() {
        return this.certification.isCertificated();
    }

    public void onCertificate() {
        this.certification = this.certification.onCertificate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id)
                && Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
