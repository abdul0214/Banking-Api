package com.moneytransfer.api.service.exception;

public class TransactionNotAllowed extends RuntimeException {

    public TransactionNotAllowed(String message) {
        super(message);
    }
}