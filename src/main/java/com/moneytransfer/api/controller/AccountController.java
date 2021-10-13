package com.moneytransfer.api.controller;

import com.moneytransfer.api.domain.AccountDto;
import com.moneytransfer.api.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "Account")
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountNo}")
    @ApiOperation(value = "Get Account Statement")
    public AccountDto getAccountInfo(@PathVariable Long accountNo) throws Exception {
        return accountService.getAccountInfo(accountNo);
    }
}