package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.SendRecord;

import java.util.List;

public interface SendRecordService extends IService<SendRecord> {
    Boolean SendMoney(SendRecord sendRecord);

    List<SendRecord> ListSendRecordOut(Integer senderId);

    List<SendRecord> ListSendRecordIn(Integer targeterId);
}
