package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "deal")
public class Deal {
    @TableId(type = IdType.AUTO)
    private Integer dealId;

    private Integer userId;

    private Double sumPrice;

    private Integer commodityNum;

    private Integer commodityId;

    private Integer bankAccountId;
}
