package com.example.walletpayment.controller;


import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.service.BankAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "BankAccount")
@RestController
@Slf4j
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/list")
    @ApiOperation("获取全部银行账户")
    public ResponseResult list() {
        return ResponseResult.e(ResponseCode.OK, bankAccountService.list());
    }
}
