package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.mybatis.mapper.SendRecordMapper;
import com.example.walletpayment.service.SendRecordService;
import org.springframework.stereotype.Service;

@Service
public class SendRecordServiceImpl extends ServiceImpl<SendRecordMapper, SendRecord> implements SendRecordService {
}
