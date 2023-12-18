package com.example.walletpayment.service.Impl;
/**
 * @author: WuHao
 * @date: 2023/12/12 16:47
 * @description: 
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.walletpayment.mybatis.entity.ReUserBank;
import com.example.walletpayment.mybatis.mapper.ReUserBankMapper;
import com.example.walletpayment.service.ReUserBankService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author WuHao
 * @date 2023/12/12 16:47
 **/
@Service
public class ReUserBankServiceImpl extends ServiceImpl<ReUserBankMapper, ReUserBank> implements ReUserBankService {
    @Override
    public List<Integer> listByUserId(Integer userId) {
        QueryWrapper<ReUserBank> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ReUserBank::getUserId, userId);
        List<ReUserBank> res = this.list(wrapper);
        return res.isEmpty() ? new ArrayList<>() : res.stream().map(ReUserBank::getBankAccountId).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteUserBankAccountId(Integer userId, Integer bankAccountId) {
        QueryWrapper<ReUserBank> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ReUserBank::getUserId, userId).eq(ReUserBank::getBankAccountId, bankAccountId);
        List<ReUserBank> res = this.list(wrapper);
        return res.isEmpty() ? Boolean.TRUE : this.removeByIds(res.stream().map(ReUserBank::getUserId).collect(Collectors.toList()));
    }
}
