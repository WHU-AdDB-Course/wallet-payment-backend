package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.mapper.BankAccountMapper;
import com.example.walletpayment.service.BankAccountService;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl extends ServiceImpl<BankAccountMapper, BankAccount> implements BankAccountService {
}
