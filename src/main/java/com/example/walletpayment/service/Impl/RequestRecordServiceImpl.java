package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.mybatis.mapper.RequestRecordMapper;
import com.example.walletpayment.service.RequestRecordService;
import org.springframework.stereotype.Service;

@Service
public class RequestRecordServiceImpl extends ServiceImpl<RequestRecordMapper, RequestRecord> implements RequestRecordService {
}
