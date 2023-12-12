package com.example.walletpayment.common.req;
/**
 * @author: WuHao
 * @date: 2023/12/12 15:46
 * @description: 
 */

import lombok.Data;

/**
 * 
 * @author WuHao
 * @date 2023/12/12 15:46
 **/
@Data
public class UserLoginReq {
    private String phone;
    private String password;
}
