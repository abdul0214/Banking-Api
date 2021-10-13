package com.moneytransfer.api.domain;

import com.moneytransfer.api.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsAssembler {

    public TransactionResponse toModel(Transaction entity) {
        TransactionResponse dto = new TransactionResponse();
        dto.setAmount(entity.getAmount());
        dto.setTime(entity.getTime());
        dto.setReferenceNo(entity.getReferenceNo());
        dto.setSourceAccountNo(entity.getSourceAccount().getId());
        dto.setTargetAccountNo(entity.getTargetAccount().getId());
        dto.setDetails(entity.getDetails());
        return dto;
    }

    public List<TransactionResponse> toModel(List<Transaction> entities) {
        if (entities == null) {
            return null;
        }

        List<TransactionResponse> list = new ArrayList<TransactionResponse>(entities.size());
        for (Transaction transaction : entities) {
            list.add(toModel(transaction));
        }
        return list;
    }
}


