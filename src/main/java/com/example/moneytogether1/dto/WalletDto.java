package com.example.moneytogether1.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WalletDto {
    private String walletName;
    private String walletExplain;
    private String walletMoneyType;
    private String cashboxAmount;
    private String cashboxBank;
    private int startday;
    private Boolean cashboxUseYn;
}