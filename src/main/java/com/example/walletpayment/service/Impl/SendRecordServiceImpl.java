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
    public Boolean SendMoney(SendRecord sendRecord, Integer senderBank, Integer targetBank) {
        User targetUser = userService.getById(sendRecord.getTargeterId());
        sendRecord.setEmail(targetUser.getEmail());
        sendRecord.setPhone(targetUser.getPhone());
        Date now = new Date();
        sendRecord.setCreateTime(now);

        BankAccount sender = bankAccountService.getById(senderBank);
        BankAccount target = bankAccountService.getById(targetBank);

        if (sender.getBalance()-sendRecord.getValue() < 0){
            return Boolean.FALSE;
        }

        sender.setBalance(sender.getBalance() - sendRecord.getValue());
        target.setBalance(target.getBalance() + sendRecord.getValue());
        try {
            this.save(sendRecord);
            bankAccountService.saveOrUpdate(sender);
            bankAccountService.saveOrUpdate(target);
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
