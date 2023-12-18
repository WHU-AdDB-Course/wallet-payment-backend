package com.example.walletpayment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.walletpayment.mybatis.entity.User;

public interface UserService extends IService<User> {
    User getByPhone(String phone);
    User getBySsn(String ssn);
    User getByEmail(String email);
    User getByPhoneOrEmail(String phone, String email);
    User getByPhoneAndEmail(String phone, String email);
}
