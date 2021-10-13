package com.moneytransfer.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneytransfer.api.MoneytransferApplication;
import com.moneytransfer.api.domain.TransactionResponse;
import com.moneytransfer.api.model.Account;
import com.moneytransfer.api.repository.AccountRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoneytransferApplication.class)
@AutoConfigureMockMvc
public class EndToEndTransactionTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionController transactionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateTransaction() throws Exception {
        accountRepository.save(getAccount1());
        accountRepository.save(getAccount2());
        String uri = "/api/transaction";
        String payload = "{\n" +
                "    \"amount\": \"1\",\n" +
                "    \"details\": \"dummyDetails\",\n" +
                "    \"referenceNo\": \"123\",\n" +
                "    \"sourceAccountNo\": \"1\",\n" +
                "    \"targetAccountNo\": \"2\"\n" +
                "}";

        MvcResult result = mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode responseJSON = mapper.readTree(result.getResponse().getContentAsString());
        TransactionResponse actualTransactionResponse = mapper.readValue(responseJSON.toString(), new TypeReference<TransactionResponse>() {});
        Assertions.assertThat(getExpectedTransactionResponse())
                .usingRecursiveComparison()
                .ignoringFields("time","details","referenceNo")
                .isEqualTo(actualTransactionResponse);
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

    TransactionResponse getExpectedTransactionResponse(){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(new BigDecimal(1));
        transactionResponse.setSourceAccountNo(1L);
        transactionResponse.setTargetAccountNo(2L);
        return transactionResponse;
    }
}