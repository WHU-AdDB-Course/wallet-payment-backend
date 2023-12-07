package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @TableId
    private String postCode;

    private String country;

    private String province;

    private String city;

    private String street;
}
