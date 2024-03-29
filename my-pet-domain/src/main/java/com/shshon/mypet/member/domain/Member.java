package com.shshon.mypet.member.domain;

import com.shshon.mypet.common.domain.BaseTimeEntity;
import com.shshon.mypet.common.domain.DeleteHistory;
import com.shshon.mypet.common.domain.Deleteable;
import com.shshon.mypet.member.exception.AuthorizationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "members")
@Where(clause = "is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity
        implements Deleteable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Embedded
    private Password password;

    @Column(length = 30, nullable = false)
    private String nickname;

    private LocalDate birthDay;

    @Column(length = 13)
    private String phoneNumber;

    private DeleteHistory deleteHistory = DeleteHistory.nonDeleted();

    @Builder
    public Member(String email,
                  String password,
                  String nickname,
                  LocalDate birthDay,
                  String phoneNumber) {
        this.email = email;
        this.password = Password.of(password);
        this.nickname = nickname;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
    }

    public void authenticate(String password) {
        if (!this.password.matches(password)) {
            throw new AuthorizationException();
        }
    }

    public void changePassword(String password) {
        this.password = Password.of(password);
    }

    public void edit(String nickname, LocalDate birthDay, String phoneNumber) {
        this.nickname = nickname;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void delete() {
        this.deleteHistory.writeDeleteHistory();
    }

    public boolean isDeleted() {
        return this.deleteHistory.isDeleted();
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
