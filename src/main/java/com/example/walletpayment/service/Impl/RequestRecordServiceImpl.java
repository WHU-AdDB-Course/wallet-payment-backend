package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.mybatis.mapper.RequestRecordMapper;
import com.example.walletpayment.service.BankAccountService;
import com.example.walletpayment.service.RequestRecordService;
import com.example.walletpayment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RequestRecordServiceImpl extends ServiceImpl<RequestRecordMapper, RequestRecord> implements RequestRecordService {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @Override
    public Boolean RequestMoney(RequestRecord requestRecord, Integer requestBank, List<Integer> targetBanks) {
        targetBanks.stream().forEach(s -> {

            Integer targetId = bankAccountService.getById(s).getUserId();

            RequestRecord requestRecord1 = new RequestRecord();

            requestRecord1.setRequesterId(requestRecord.getRequesterId());
            requestRecord1.setTargeterId(targetId);
            requestRecord1.setRemark(requestRecord.getRemark());
            requestRecord1.setValue(requestRecord.getValue());
            requestRecord1.setCreateTime(new Date());
            requestRecord1.setStatus(0);
            requestRecord1.setFinishTime(requestRecord.getFinishTime());
            requestRecord1.setRequestBankId(requestBank);
            requestRecord1.setTargetBankId(s);

            this.save(requestRecord1);
        });
        return Boolean.TRUE;
    }

    @Override
    public List<RequestRecord> ListRequestRecordOut(Integer requestId) {
        QueryWrapper<RequestRecord> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(RequestRecord::getRequesterId, requestId);
        List<RequestRecord> requestRecords = this.list(wrapper);
        return requestRecords;
    }

    @Override
    public List<RequestRecord> ListRequestRecordIn(Integer targeterId) {
        QueryWrapper<RequestRecord> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(RequestRecord::getTargeterId, targeterId);
        List<RequestRecord> requestRecords = this.list(wrapper);
        return requestRecords;
    }

    @Override
    public Boolean verifyRequestRecord(RequestRecord requestRecord) {
        QueryWrapper<BankAccount> BAwrapper1 = new QueryWrapper<>();
        BAwrapper1.lambda().eq(BankAccount::getAccountId, requestRecord.getTargetBankId());
        BankAccount targetBank = bankAccountService.list(BAwrapper1).get(0);

        if (targetBank.getBalance() < requestRecord.getValue()){
            return Boolean.FALSE;
        }

        targetBank.setBalance(targetBank.getBalance()-requestRecord.getValue());
        bankAccountService.saveOrUpdate(targetBank);

        QueryWrapper<BankAccount> BAwrapper2 = new QueryWrapper<>();
        BAwrapper2.lambda().eq(BankAccount::getAccountId, requestRecord.getRequestBankId());
        BankAccount requestBank = bankAccountService.list(BAwrapper2).get(0);
        requestBank.setBalance(requestBank.getBalance()+requestRecord.getValue());
        bankAccountService.saveOrUpdate(requestBank);

        requestRecord.setStatus(1);

        this.saveOrUpdate(requestRecord);

        return Boolean.TRUE;
    }
}
