package com.example.walletpayment.controller;


import com.example.walletpayment.common.req.BankAccountAddReq;
import com.example.walletpayment.common.req.BankAccountDeleteReq;
import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.entity.ReUserBank;
import com.example.walletpayment.service.BankAccountService;
import com.example.walletpayment.service.ReUserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "BankAccount")
@RequestMapping("/bankAccount")
@RestController
@Slf4j
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private ReUserBankService reUserBankService;

    @GetMapping("/get")
    @ApiOperation("获取单个银行账户")
    public ResponseResult get(@RequestParam Integer id) {
        return ResponseResult.e(ResponseCode.OK, bankAccountService.getById(id));
    }

    @GetMapping("/list")
    @ApiOperation("获取全部银行账户")
    public ResponseResult list() {
        return ResponseResult.e(ResponseCode.OK, bankAccountService.list());
    }

    @GetMapping("/user-list")
    @ApiOperation("获取用户的银行账户")
    public ResponseResult userList(@RequestParam Integer userId) {
        List<Integer> bankAccountIds = reUserBankService.listByUserId(userId);
        return ResponseResult.e(ResponseCode.OK, bankAccountService.listByIds(bankAccountIds));
    }

    @PostMapping("/add")
    @ApiOperation("增加用户的银行账户")
    public ResponseResult add(@RequestBody BankAccountAddReq req) {
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(req, bankAccount);
        BankAccount checkAccount = bankAccountService.getByBankNumber(req.getBankNumber());
        if (checkAccount == null) {
            bankAccount.setBalance(200.0);
            boolean res = bankAccountService.saveOrUpdate(bankAccount);

            ReUserBank reUserBank = new ReUserBank();
            reUserBank.setUserId(req.getUserId());
            reUserBank.setBankAccountId(bankAccountService.getByBankNumber(bankAccount.getAccountNumber()).getAccountId());
            res &= reUserBankService.saveOrUpdate(reUserBank);
            return res ? ResponseResult.e(ResponseCode.OK, bankAccount) : ResponseResult.e(ResponseCode.FAIL);
        }
        else {
            ReUserBank reUserBank = new ReUserBank();
            reUserBank.setUserId(req.getUserId());
            reUserBank.setBankAccountId(checkAccount.getAccountId());
            boolean res = reUserBankService.save(reUserBank);
            return res ? ResponseResult.e(ResponseCode.OK, bankAccount) : ResponseResult.e(ResponseCode.FAIL);
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户的银行账户")
    public ResponseResult delete(@RequestParam BankAccountDeleteReq req) {
        boolean res = reUserBankService.deleteUserBankAccountId(req.getUserId(), req.getBankAccountId());
        return res ? ResponseResult.e(ResponseCode.OK) : ResponseResult.e(ResponseCode.FAIL);
    }
}
