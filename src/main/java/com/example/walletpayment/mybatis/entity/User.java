package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@TableName(value = "user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String email;

    @NotBlank(message = "ssn不能为空")
    private String ssn;

    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @NotBlank(message = "密码不能为空")
    private String password;
}
