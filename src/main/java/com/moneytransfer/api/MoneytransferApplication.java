package com.moneytransfer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MoneytransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneytransferApplication.class, args);
    }

}
