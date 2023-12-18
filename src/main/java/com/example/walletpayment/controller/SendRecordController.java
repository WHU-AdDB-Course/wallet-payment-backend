package com.example.walletpayment.controller;

import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.service.SendRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "SendRecord")
@RequestMapping("/sendRecord")
@RestController
@Slf4j
public class SendRecordController {
    @Autowired
    SendRecordService sendRecordService;


    @ApiOperation("增加新的转钱任务")
    @PostMapping("/add")
    public ResponseResult sendMoney(@RequestBody SendRecord sendRecord, Integer senderBank, Integer targetBank){
        if (sendRecordService.SendMoney(sendRecord, senderBank, targetBank)){
            return ResponseResult.e(ResponseCode.OK, Boolean.TRUE);
        }
        else {
            return ResponseResult.error("账户余额不足或存在其他错误");
        }
    }

    @ApiOperation("查询转钱记录（转出）")
    @GetMapping("/listOut")
    public ResponseResult listSendRecordOut(@RequestParam Integer senderId){
        return ResponseResult.e(ResponseCode.OK, sendRecordService.ListSendRecordOut(senderId));
    }

    @ApiOperation("查询转钱记录（转入）")
    @GetMapping("/listIn")
    public ResponseResult listSendRecordIn(@RequestParam Integer targeterId){
        return ResponseResult.e(ResponseCode.OK, sendRecordService.ListSendRecordIn(targeterId));
    }



}
