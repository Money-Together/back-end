package com.example.moneytogether1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor  // JPA 필수
@AllArgsConstructor // Builder 사용 시 필요
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String walletName;

    private String walletExplain;

    private String walletMoneyType;

    private String cashboxAmount;

    private String cashboxBank;

    private Long startday;

    private Boolean cashboxUseYn;
}
