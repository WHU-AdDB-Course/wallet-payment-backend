package com.example.walletpayment.controller;

import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.service.DealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Deal")
@RequestMapping("/deal")
@RestController
@Slf4j
public class DealController {
    @Autowired
    private DealService dealService;

    @GetMapping("/get")
    @ApiOperation("获取单个订单")
    public ResponseResult get(@RequestParam Integer dealId) {
        return ResponseResult.e(ResponseCode.OK, dealService.getById(dealId));
    }

    @GetMapping("/list")
    @ApiOperation("获取所有订单")
    public ResponseResult list() {
        return ResponseResult.e(ResponseCode.OK, dealService.list());
    }

    @GetMapping("/user-list")
    @ApiOperation("获取用户所有订单")
    public ResponseResult userList(@RequestParam Integer userId) {
        return ResponseResult.e(ResponseCode.OK, dealService.listByUserId(userId));
    }

    @PostMapping("/delete")
    @ApiOperation("删除单个订单")
    public ResponseResult delete(@RequestParam Integer dealId) {
        boolean res = dealService.removeById(dealId);
        return res ? ResponseResult.e(ResponseCode.OK) : ResponseResult.e(ResponseCode.FAIL);
    }
}
