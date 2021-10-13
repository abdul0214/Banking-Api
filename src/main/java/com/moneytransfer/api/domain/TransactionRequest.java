package com.moneytransfer.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


@Getter
@Setter
public class TransactionRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    private String details;

    private Long referenceNo;

    @NotNull
    private Long sourceAccountNo;

    @NotNull
    private Long targetAccountNo;

}
