package com.example.walletpayment.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.walletpayment.mybatis.entity.BankAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BankAccountMapper extends BaseMapper<BankAccount> {
}
