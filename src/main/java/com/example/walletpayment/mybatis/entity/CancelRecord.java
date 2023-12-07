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
public class CancelRecord {

    @TableId(type = IdType.AUTO)
    private Integer cancelRecordId;

    private Integer userId;

    private Integer sendRecordId;

    private String remark;

    private Date createTime;

}
