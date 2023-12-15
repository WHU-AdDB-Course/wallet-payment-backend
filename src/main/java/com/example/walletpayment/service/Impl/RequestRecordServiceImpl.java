package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.mybatis.mapper.RequestRecordMapper;
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
    private UserService userService;

    @Override
    public Boolean RequestMoney(RequestRecord requestRecord, List<String> phoneAndEmails) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        phoneAndEmails.stream().forEach(s -> {
            wrapper.lambda().eq(User::getPhone, s.split("-")[0]);
            wrapper.lambda().eq(User::getEmail, s.split("-")[1]);
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
}
