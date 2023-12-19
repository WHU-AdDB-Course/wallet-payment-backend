package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName(value = "bank_account")
public class BankAccount {
    @TableId(type = IdType.AUTO)
    private Integer accountId;

    @NotBlank
    private String accountNumber;

    private String bankName;

    private Double balance;

    private String accountType;
}
