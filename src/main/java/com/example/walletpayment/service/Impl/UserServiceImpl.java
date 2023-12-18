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
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User getByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(User::getEmail, email);
        List<User> list = this.list(wrapper);
        return list.isEmpty() ? null : list.get(0);
    }

    /* 转钱功能需求 */
    @Override
    public User getByPhoneOrEmail(String phone, String email) {
        User target = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (phone != null && !phone.equals("")) {
            wrapper.lambda().eq(User::getPhone, phone);
            target = this.list(wrapper).get(0);
        } else {
            wrapper.lambda().like(User::getEmail, email);
            target = this.list(wrapper).get(0);
        }
        return target;
    }

    /* 收钱功能需求 */
    @Override
    public User getByPhoneAndEmail(String phone, String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone, phone);
        wrapper.lambda().like(User::getEmail, email);
        User target = this.list(wrapper).get(0);
        return target;
    }


}
