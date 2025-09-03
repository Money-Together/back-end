package com.example.moneytogether1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor  // JPA 필수
@AllArgsConstructor // Builder 사용 시 필요
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String firebaseUid;

    private String nickname;

    private String email;

    private String image;

    @Column(name = "use_yn")
    private Boolean useYn;
}
