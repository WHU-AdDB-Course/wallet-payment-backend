package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String name;

    private String email;

    private String ssn;

    private String phone;
}
