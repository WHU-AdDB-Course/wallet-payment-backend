package com.example.walletpayment.controller;


import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.service.BankAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "BankAccount")
@RequestMapping("/bankAccount")
@RestController
@Slf4j
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

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
        return ResponseResult.e(ResponseCode.OK, bankAccountService.listByUser(userId));
    }

    @PostMapping("/add")
    @ApiOperation("增加用户的银行账户")
    public ResponseResult add(@RequestBody BankAccount bankAccount) {
        boolean res = bankAccountService.save(bankAccount);
        return res ? ResponseResult.e(ResponseCode.OK, bankAccount) : ResponseResult.e(ResponseCode.FAIL);
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户的银行账户")
    public ResponseResult delete(@RequestParam Integer id) {
        boolean res = bankAccountService.removeById(id);
        return res ? ResponseResult.e(ResponseCode.OK) : ResponseResult.e(ResponseCode.FAIL);
    }
}
