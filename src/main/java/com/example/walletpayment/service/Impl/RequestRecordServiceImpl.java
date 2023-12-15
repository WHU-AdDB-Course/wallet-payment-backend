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
    public Boolean RequestMoney(RequestRecord requestRecord, List<String> phoneAndEmails) {
        phoneAndEmails.stream().forEach(s -> {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            String phone = s.split("-")[0];
            String email = s.split("-")[1];
            wrapper.lambda().eq(User::getPhone, phone);
            wrapper.lambda().like(User::getEmail, email);
            User target = userService.list(wrapper).get(0);

            RequestRecord requestRecord1 = new RequestRecord();

            requestRecord1.setRequesterId(requestRecord.getRequesterId());
            requestRecord1.setTargeterId(target.getUserId());
            requestRecord1.setRemark(requestRecord.getRemark());
            requestRecord1.setValue(requestRecord.getValue());
            requestRecord1.setCreateTime(new Date());
            requestRecord1.setStatus(0);
            requestRecord1.setFinishTime(requestRecord.getFinishTime());

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
        BAwrapper1.lambda().eq(BankAccount::getUserId, requestRecord.getTargeterId());
        BankAccount bankAccount1 = bankAccountService.list(BAwrapper1).get(0);
        bankAccount1.setBalance(bankAccount1.getBalance()-requestRecord.getValue());
        bankAccountService.saveOrUpdate(bankAccount1);

        QueryWrapper<BankAccount> BAwrapper2 = new QueryWrapper<>();
        BAwrapper2.lambda().eq(BankAccount::getUserId, requestRecord.getRequesterId());
        BankAccount bankAccount2 = bankAccountService.list(BAwrapper2).get(0);
        bankAccount2.setBalance(bankAccount2.getBalance()+requestRecord.getValue());
        bankAccountService.saveOrUpdate(bankAccount2);

        requestRecord.setStatus(1);

        this.saveOrUpdate(requestRecord);

        return Boolean.TRUE;
    }
}
