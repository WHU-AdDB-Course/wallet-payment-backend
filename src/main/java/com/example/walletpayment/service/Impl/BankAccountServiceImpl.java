package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.mapper.BankAccountMapper;
import com.example.walletpayment.service.BankAccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountServiceImpl extends ServiceImpl<BankAccountMapper, BankAccount> implements BankAccountService {

    @Override
    public BankAccount getByBankNumber(String bankNumber) {
        QueryWrapper<BankAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BankAccount::getAccountNumber, bankNumber);
        return this.list(wrapper).isEmpty() ? null : this.list(wrapper).get(0);
    }

    @Override
    public Boolean checkBalance(Integer bankAccountId, Double price) {
        BankAccount bankAccount = this.getById(bankAccountId);
        if (bankAccount == null) return false;
        return bankAccount.getBalance() >= price;
    }

    @Override
    public Boolean purchase(Integer accountId, BigDecimal sumPrice) {
        QueryWrapper<BankAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BankAccount::getAccountId, accountId);
        BankAccount bankAccount = this.getOne(wrapper);
        BigDecimal balance = BigDecimal.valueOf(bankAccount.getBalance());
        bankAccount.setBalance(balance.subtract(sumPrice).doubleValue());
        return this.updateById(bankAccount);
    }
}
