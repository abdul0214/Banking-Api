package com.moneytransfer.api.service;

import com.moneytransfer.api.domain.TransactionRequest;
import com.moneytransfer.api.domain.TransactionResponse;
import com.moneytransfer.api.domain.TransactionsAssembler;
import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.model.Transaction;
import com.moneytransfer.api.repository.TransactionRepository;
import com.moneytransfer.api.service.validation.TransactionRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionRequestValidator transactionRequestValidator;
    private final TransactionsAssembler transactionsAssembler;
    private final AccountService accountService;


    public TransactionResponse makeTransaction(TransactionRequest request) throws Exception {
        transactionRequestValidator.validateTransactionRequest(request);
        Account sourceAccount = accountService.getAccount(request.getSourceAccountNo());
        Account targetAccount = accountService.getAccount(request.getTargetAccountNo());
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(request.getAmount()));
        return transactionsAssembler.toModel(createTransaction(request,sourceAccount,targetAccount));
    }

    public Transaction createTransaction(TransactionRequest transactionRequest, Account sourceAccount, Account targetAccount){
        Transaction newTransaction = new Transaction();
        newTransaction.setSourceAccount(sourceAccount); //take in function parameter to avoid DB conn
        newTransaction.setTargetAccount(targetAccount); //take in function parameter to avoid DB conn
        newTransaction.setAmount(transactionRequest.getAmount());
        newTransaction.setReferenceNo(transactionRequest.getReferenceNo());
        newTransaction.setDetails(transactionRequest.getDetails());
        transactionRepository.save(newTransaction);
        return newTransaction;
    }

}
