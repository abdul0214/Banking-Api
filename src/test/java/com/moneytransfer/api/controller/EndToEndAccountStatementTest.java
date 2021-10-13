package com.moneytransfer.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneytransfer.api.MoneytransferApplication;
import com.moneytransfer.api.domain.AccountDto;
import com.moneytransfer.api.domain.TransactionResponse;
import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.model.Transaction;
import com.moneytransfer.api.repository.AccountRepository;
import com.moneytransfer.api.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoneytransferApplication.class)
@AutoConfigureMockMvc
public class EndToEndAccountStatementTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionController transactionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldProvideAccountStatement() throws Exception {
        accountRepository.save(getAccount1());
        accountRepository.save(getAccount2());
        transactionRepository.save(getTransaction());
        String uri = "/api/account";
        MvcResult result = mockMvc.perform(get(uri + "/" + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode responseJSON = mapper.readTree(result.getResponse().getContentAsString());
        AccountDto responseAccountStatement = mapper.readValue(responseJSON.toString(), new TypeReference<AccountDto>(){});
        Assertions.assertThat(responseAccountStatement.getAccountNumber()).isEqualTo(1L);
        Assertions.assertThat(responseAccountStatement.getAccountBalance().compareTo(getExpectedAccountStatement().getAccountBalance()) == 0);
        Assertions.assertThat(responseAccountStatement.getTransactions().size()).isEqualTo(1);
        Assertions.assertThat(responseAccountStatement.getTransactions().get(0).getAmount().compareTo(getExpectedAccountStatement().getTransactions().get(0).getAmount())).isEqualTo(0);
        Assertions.assertThat(responseAccountStatement.getTransactions().get(0).getSourceAccountNo()).isEqualTo(getExpectedAccountStatement().getTransactions().get(0).getSourceAccountNo());
        Assertions.assertThat(responseAccountStatement.getTransactions().get(0).getTargetAccountNo()).isEqualTo(getExpectedAccountStatement().getTransactions().get(0).getTargetAccountNo());
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
        account2.setBalance(new BigDecimal("2000"));
        return account2;
    }

    AccountDto getExpectedAccountStatement(){
        AccountDto accountDto = new AccountDto(1L, new BigDecimal(1000));
        List<TransactionResponse> transactions = new ArrayList<>();
        transactions.add(getExpectedTransactionResponse());
        accountDto.setTransactions(transactions);
        return accountDto;
    }

    TransactionResponse getExpectedTransactionResponse(){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(new BigDecimal(1));
        transactionResponse.setSourceAccountNo(1L);
        transactionResponse.setTargetAccountNo(2L);
        return transactionResponse;
    }

    Transaction getTransaction(){
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1));
        transaction.setSourceAccount(getAccount1());
        transaction.setTargetAccount(getAccount2());
        return transaction;
    }

}