package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.RequestRecord;

import java.util.List;

public interface RequestRecordService extends IService<RequestRecord> {

    Boolean RequestMoney(RequestRecord requestRecord, Integer requestBank, List<Integer> targetBanks);

    List<RequestRecord> ListRequestRecordOut(Integer requestId);

    List<RequestRecord> ListRequestRecordIn(Integer targeterId);

    Boolean verifyRequestRecord(RequestRecord requestRecord);

}
