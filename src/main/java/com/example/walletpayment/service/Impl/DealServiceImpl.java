package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.Deal;
import com.example.walletpayment.mybatis.mapper.DealMapper;
import com.example.walletpayment.service.DealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealServiceImpl extends ServiceImpl<DealMapper, Deal> implements DealService {
    @Override
    public List<Deal> listByUserId(Integer userId) {
        QueryWrapper<Deal> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Deal::getUserId, userId);
        return this.list(wrapper);
    }
}
