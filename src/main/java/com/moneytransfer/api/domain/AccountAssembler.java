package com.moneytransfer.api.domain;


import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAssembler {

    private final TransactionsAssembler transactionsAssembler;
    private final TransactionRepository transactionRepository;

    public AccountDto toModel(Account entity) {
        // TODO: add rounding for upto two decimal places for balance
        AccountDto dto = new AccountDto(entity.getId(), entity.getBalance());
        dto.setTransactions(transactionsAssembler.toModel(transactionRepository.findAllForAccount(entity.getId())));
        return dto;
    }
}
