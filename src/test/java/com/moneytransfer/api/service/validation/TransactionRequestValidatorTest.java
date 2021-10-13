package com.moneytransfer.api.service.validation;

import com.moneytransfer.api.domain.AccountAssembler;
import com.moneytransfer.api.domain.TransactionRequest;
import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.repository.AccountRepository;
import com.moneytransfer.api.service.AccountService;
import com.moneytransfer.api.service.exception.NoDataFoundException;
import com.moneytransfer.api.service.exception.TransactionNotAllowed;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionRequestValidatorTest {

    private final AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private final AccountAssembler accountAssembler = Mockito.mock(AccountAssembler.class);
    private final AccountService accountService = new AccountService(accountAssembler,accountRepository);
    private final TransactionRequestValidator transactionRequestValidator = new TransactionRequestValidator(accountService);

    @Test
    public void shouldThrowTargetAccountNotFoundException() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(getAccount1()));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(NoDataFoundException.class, () -> {
            transactionRequestValidator.validateTransactionRequest(getTransactionRequest1());
        });
        String expectedMessage = "Account not found with AccountNo: 2";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void shouldThrowSourceAccountNotFoundException() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.ofNullable(getAccount2()));
        Exception exception = assertThrows(NoDataFoundException.class, () -> {
            transactionRequestValidator.validateTransactionRequest(getTransactionRequest1());
        });
        String expectedMessage = "Account not found with AccountNo: 1";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void shouldThrowNotEnoughBalanceException() {
        TransactionRequest transactionRequest = getTransactionRequest1();
        transactionRequest.setAmount(new BigDecimal(1001));
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(getAccount1()));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.ofNullable(getAccount2()));
        Exception exception = assertThrows(TransactionNotAllowed.class, () -> {
            transactionRequestValidator.validateTransactionRequest(transactionRequest);
        });
        String expectedMessage = "Source Account Does not have enough balance";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    Account getAccount1() {
        Account account1 = new Account();
        account1.setId(1L);
        account1.setBalance(new BigDecimal("1000"));
        return account1;
    }


    Account getAccount2() {
        Account account2 = new Account();
        account2.setId(2L);
        account2.setBalance(new BigDecimal("1000"));
        return account2;
    }

    TransactionRequest getTransactionRequest1() {
        TransactionRequest transactionRequest1 = new TransactionRequest();
        transactionRequest1.setAmount(new BigDecimal(150));
        transactionRequest1.setSourceAccountNo(1L);
        transactionRequest1.setTargetAccountNo(2L);
        return transactionRequest1;
    }

}