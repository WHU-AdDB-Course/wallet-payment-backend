package com.example.walletpayment.common.vo;
/**
 * @author: WuHao
 * @date: 2023/12/12 16:40
 * @description: 
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 
 * @author WuHao
 * @date 2023/12/12 16:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Integer userId;
    private String name;
    private List<String> email;
    private String ssn;
    private String phone;
    private String password;
    private Integer defaultAccountId;
}
