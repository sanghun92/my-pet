package com.shshon.mypet.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    @Query("select m from Member m where m.certification.certificationCode =:certificationCode ")
    Optional<Member> findByCertificationCode(UUID certificationCode);
}
