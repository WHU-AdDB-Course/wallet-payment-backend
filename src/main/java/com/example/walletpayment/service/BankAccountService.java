package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.BankAccount;

import java.util.List;

public interface BankAccountService extends IService<BankAccount> {
    List<BankAccount> listByUser(Integer userId);
}
