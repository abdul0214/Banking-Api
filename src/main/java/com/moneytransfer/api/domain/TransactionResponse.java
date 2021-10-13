package com.moneytransfer.api.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Setter
@Getter
public class TransactionResponse {

    private OffsetDateTime time;

    private BigDecimal amount;

    private String details;

    private Long referenceNo;

    private Long sourceAccountNo;

    private Long targetAccountNo;

}