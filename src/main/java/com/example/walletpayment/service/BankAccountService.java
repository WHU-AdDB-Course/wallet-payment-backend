package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService extends IService<BankAccount> {
    BankAccount getByBankNumber(String bankNumber);

    Boolean checkBalance(Integer bankAccountId, Double price);

    Boolean purchase(Integer bankAccountId, BigDecimal sumPrice);
}
