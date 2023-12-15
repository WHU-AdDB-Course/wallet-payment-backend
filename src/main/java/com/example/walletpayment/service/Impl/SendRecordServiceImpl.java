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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (sendRecord.getPhone() != null && !sendRecord.getPhone().equals("")) {
            wrapper.lambda().eq(User::getPhone, sendRecord.getPhone());
            User target = userService.list(wrapper).get(0);
            sendRecord.setTargeterId(target.getUserId());
            sendRecord.setEmail(target.getEmail());
        } else {
            wrapper.lambda().like(User::getEmail, sendRecord.getEmail());
            User target = userService.list(wrapper).get(0);
            sendRecord.setTargeterId(target.getUserId());
            sendRecord.setPhone(target.getPhone());
        }
        Date now = new Date();
        sendRecord.setCreateTime(now);
//        sendRecord.setStatus(0);
        try {
            this.save(sendRecord);
        } catch (Exception e){
            return Boolean.FALSE;
        }

        QueryWrapper<BankAccount> BAwrapper1 = new QueryWrapper<>();
        BAwrapper1.lambda().eq(BankAccount::getUserId, sendRecord.getSenderId());
        BankAccount bankAccount1 = bankAccountService.list(BAwrapper1).get(0);
        bankAccount1.setBalance(bankAccount1.getBalance()-sendRecord.getValue());
        bankAccountService.saveOrUpdate(bankAccount1);

        QueryWrapper<BankAccount> BAwrapper2 = new QueryWrapper<>();
        BAwrapper2.lambda().eq(BankAccount::getUserId, sendRecord.getTargeterId());
        BankAccount bankAccount2 = bankAccountService.list(BAwrapper2).get(0);
        bankAccount2.setBalance(bankAccount2.getBalance()+sendRecord.getValue());
        bankAccountService.saveOrUpdate(bankAccount2);

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
