package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    private String commodityName;

    private Integer bankAccountId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
