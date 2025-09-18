package com.example.moneytogether1.dto.response;

import com.example.moneytogether1.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private String image;

    // Member 엔티티 → DTO 변환
    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .image(member.getImage())
                .build();
    }
}

