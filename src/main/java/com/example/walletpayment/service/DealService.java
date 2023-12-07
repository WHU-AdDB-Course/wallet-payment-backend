package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.Deal;

import java.util.List;

public interface DealService extends IService<Deal> {
    List<Deal> listByUserId(Integer userId);
}
