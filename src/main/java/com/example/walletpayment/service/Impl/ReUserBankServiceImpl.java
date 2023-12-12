package com.example.walletpayment.service.Impl;
/**
 * @author: WuHao
 * @date: 2023/12/12 16:47
 * @description: 
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.ReUserBank;
import com.example.walletpayment.mybatis.mapper.ReUserBankMapper;
import com.example.walletpayment.service.ReUserBankService;
import org.springframework.stereotype.Service;

/**
 * 
 * @author WuHao
 * @date 2023/12/12 16:47
 **/
@Service
public class ReUserBankServiceImpl extends ServiceImpl<ReUserBankMapper, ReUserBank> implements ReUserBankService {
}
