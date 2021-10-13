package com.moneytransfer.api.controller;


import com.moneytransfer.api.domain.TransactionRequest;
import com.moneytransfer.api.domain.TransactionResponse;
import com.moneytransfer.api.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "Transaction")
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ApiOperation(value = "Make a transaction")
    public TransactionResponse updateBalance(@RequestBody @Valid TransactionRequest request) throws Exception {
        TransactionResponse transactionDto = transactionService.makeTransaction(request);
        return transactionDto;
    }
}