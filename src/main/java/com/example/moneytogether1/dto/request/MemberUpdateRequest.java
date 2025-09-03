package com.example.moneytogether1.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequest {
    private String nickname;
    private String image;
}
