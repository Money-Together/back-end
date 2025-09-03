package com.example.moneytogether1.dto;

import com.example.moneytogether1.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String firebaseUid;
    private String nickname;
    private String email;
    private String image;
    private Boolean useYn;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .firebaseUid(member.getFirebaseUid())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .image(member.getImage())
                .useYn(member.getUseYn())
                .build();
    }
}

