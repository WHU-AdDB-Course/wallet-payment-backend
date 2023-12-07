package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendRecord {

    @TableId(type = IdType.AUTO)
    private Integer sendRecordId;

    private Integer senderId;

    private Integer targeterId;

    private Integer value;

    private Integer status;

    private String remark;

    private Date createTime;

    private String phone;

    private String email;
}
