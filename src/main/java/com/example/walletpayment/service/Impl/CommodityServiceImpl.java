package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.Commodity;
import com.example.walletpayment.mybatis.mapper.CommodityMapper;
import com.example.walletpayment.service.CommodityService;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService{
}
