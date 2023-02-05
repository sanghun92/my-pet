package com.shshon.mypet.member.domain;

import com.shshon.mypet.member.exception.AuthorizationException;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "members",
        uniqueConstraints = @UniqueConstraint(name = "idx_member_email", columnNames = "email")
)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private Password password;

    @Column(nullable = false)
    private String nickname;

    protected Member() {}

    private Member(String email, Password password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static Member of(String email, String password, String nickname) {
        return new Member(email, Password.of(password), nickname);
    }

    public void authenticate(String password) {
        if(!this.password.matches(password)) {
            throw new AuthorizationException();
        }
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
