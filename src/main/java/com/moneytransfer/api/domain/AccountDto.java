package com.moneytransfer.api.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@RequiredArgsConstructor
@Getter
public class AccountDto {

    private final Long accountNumber;

    private final BigDecimal accountBalance;

    private List<TransactionResponse> transactions;

}