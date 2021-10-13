package com.moneytransfer.api.service;

import com.moneytransfer.api.domain.AccountAssembler;
import com.moneytransfer.api.domain.AccountDto;
import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.repository.AccountRepository;
import com.moneytransfer.api.service.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;

    public AccountDto getAccountInfo(Long accountId) {
        Account account = getAccount(accountId);
        return accountAssembler.toModel(account);
    }

    public Account getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoDataFoundException("Account not found with AccountNo: " + accountId));
        return account;
    }

}
