package com.moneytransfer.api.service.validation;

import com.moneytransfer.api.domain.TransactionRequest;
import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.service.AccountService;
import com.moneytransfer.api.service.exception.TransactionNotAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionRequestValidator {

    private final AccountService accountService;

    public void validateTransactionRequest(TransactionRequest request) {
        Account sourceAccount = accountService.getAccount(request.getSourceAccountNo());
        Account targetAccount = accountService.getAccount(request.getTargetAccountNo());
        checkSourceAccountHasEnoughBalance(sourceAccount,request.getAmount());
    }

    public void checkSourceAccountHasEnoughBalance(Account sourceAccount, BigDecimal amount) {
        if (sourceAccount.getBalance().compareTo(amount) == -1) {
            throw new TransactionNotAllowed("Source Account Does not have enough balance");
        }
    }
}
