package com.example.walletpayment.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.mybatis.mapper.UserMapper;
import com.example.walletpayment.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, phone);
        List<User> list = this.list(wrapper);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User getBySsn(String ssn) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getSsn, ssn);
        List<User> list = this.list(wrapper);
        return list == null ? null : list.get(0);
    }

}
