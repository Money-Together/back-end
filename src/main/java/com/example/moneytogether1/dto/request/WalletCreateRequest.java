package com.example.moneytogether1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalletCreateRequest {

    @NotBlank(message = "가게 이름(storeName)은 필수입니다.")
    private String storeName;

    private String storeExplain;

    private String storeImage;
}