package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.mapper.BankAccountMapper;
import com.example.walletpayment.service.BankAccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountServiceImpl extends ServiceImpl<BankAccountMapper, BankAccount> implements BankAccountService {
    @Override
    public List<BankAccount> listByUser(Integer userId) {
        QueryWrapper<BankAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BankAccount::getUserId, userId);
        return this.list(wrapper);
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
