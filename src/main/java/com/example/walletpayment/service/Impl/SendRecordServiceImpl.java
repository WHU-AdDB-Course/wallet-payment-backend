package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.entity.ReUserBank;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.mybatis.mapper.SendRecordMapper;
import com.example.walletpayment.mybatis.mapper.UserMapper;
import com.example.walletpayment.service.BankAccountService;
import com.example.walletpayment.service.ReUserBankService;
import com.example.walletpayment.service.SendRecordService;
import com.example.walletpayment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendRecordServiceImpl extends ServiceImpl<SendRecordMapper, SendRecord> implements SendRecordService {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @Override
    public Boolean SendMoney(SendRecord sendRecord) {
        User sender = userService.getById(sendRecord.getSenderId());
        User targeter;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (sendRecord.getPhone() != null && !sendRecord.getPhone().equals("")) {
            wrapper.lambda().eq(User::getPhone, sendRecord.getPhone());
            targeter = userService.list(wrapper).get(0);
            sendRecord.setTargeterId(targeter.getUserId());
            sendRecord.setEmail(targeter.getEmail());
        } else {
            wrapper.lambda().like(User::getEmail, sendRecord.getEmail());
            targeter = userService.list(wrapper).get(0);
            sendRecord.setTargeterId(targeter.getUserId());
            sendRecord.setPhone(targeter.getPhone());
        }
        Date now = new Date();
        sendRecord.setCreateTime(now);
//        sendRecord.setStatus(0);

        QueryWrapper<BankAccount> sendWrapper = new QueryWrapper<>();
        sendWrapper.lambda().eq(BankAccount::getAccountId, sender.getDefaultAccountId());
        BankAccount senderAccount = bankAccountService.list(sendWrapper).get(0);

        if (senderAccount.getBalance() - sendRecord.getValue() < 0){
            return Boolean.FALSE;
        }

        senderAccount.setBalance(senderAccount.getBalance()-sendRecord.getValue());
        bankAccountService.saveOrUpdate(senderAccount);

        QueryWrapper<BankAccount> targetWrapper = new QueryWrapper<>();
        targetWrapper.lambda().eq(BankAccount::getAccountId, targeter.getDefaultAccountId());
        BankAccount targetAccount = bankAccountService.list(targetWrapper).get(0);
        targetAccount.setBalance(targetAccount.getBalance()+sendRecord.getValue());
        bankAccountService.saveOrUpdate(targetAccount);

        try {
            this.save(sendRecord);
        } catch (Exception e){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public List<SendRecord> ListSendRecordOut(Integer senderId) {
        QueryWrapper<SendRecord> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(SendRecord::getSendRecordId, senderId);
        List<SendRecord> sendRecords = this.list(wrapper);
        return sendRecords;
    }

    @Override
    public List<SendRecord> ListSendRecordIn(Integer targeterId) {
        QueryWrapper<SendRecord> wrapper = new QueryWrapper<>();

        wrapper.lambda().eq(SendRecord::getTargeterId, targeterId);
        List<SendRecord> sendRecords = this.list(wrapper);
        return sendRecords;
    }
}
