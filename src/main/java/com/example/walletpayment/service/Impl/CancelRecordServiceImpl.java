package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.CancelRecord;
import com.example.walletpayment.mybatis.mapper.CancelRecordMapper;
import com.example.walletpayment.service.CancelRecordService;
import org.springframework.stereotype.Service;

@Service
public class CancelRecordServiceImpl extends ServiceImpl<CancelRecordMapper, CancelRecord> implements CancelRecordService {
}
