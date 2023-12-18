package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.ReUserBank;

import java.util.List;

/**
 * @author: WuHao
 * @date: 2023/12/12 16:46
 * @description:
 */
public interface ReUserBankService extends IService<ReUserBank> {
    List<Integer> listByUserId(Integer userId);

    Boolean deleteUserBankAccountId(Integer userId, Integer bankAccountId);
}
