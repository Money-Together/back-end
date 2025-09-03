package com.example.moneytogether1.repository;

import com.example.moneytogether1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByFirebaseUid(String firebaseUid);

    Optional<Member> findByFirebaseUidAndUseYnTrue(String firebaseUid);

}


