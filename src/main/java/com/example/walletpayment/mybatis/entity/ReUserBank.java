package com.example.walletpayment.mybatis.entity;
/**
 * @author: WuHao
 * @date: 2023/12/12 16:29
 * @description: 
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @author WuHao
 * @date 2023/12/12 16:29
 **/
@Data
@TableName(value = "re_user_bank")
public class ReUserBank {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer userId;
    Integer bankAccountId;
}
