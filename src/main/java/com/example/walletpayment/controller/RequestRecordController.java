package com.example.walletpayment.controller;

import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.service.RequestRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RequestRecord")
@RequestMapping("/requestRecord")
@RestController
@Slf4j
public class RequestRecordController {

    @Autowired
    private RequestRecordService requestRecordService;

    @PostMapping("/add")
    @ApiOperation("增加收钱任务")
    public ResponseResult requestMoney(@RequestBody RequestRecord requestRecord, List<String> phoneAndEmails){
        return ResponseResult.e(ResponseCode.OK, requestRecordService.RequestMoney(requestRecord, phoneAndEmails));
    }

    @GetMapping("/listOut")
    @ApiOperation("查询收钱任务(收钱的查)")
    public ResponseResult listRequestRecordOut(@RequestParam Integer requestId){
        return ResponseResult.e(ResponseCode.OK, requestRecordService.ListRequestRecordOut(requestId));
    }

    @GetMapping("/listIn")
    @ApiOperation("查询收钱任务(付钱的查)")
    public ResponseResult listRequestRecordIn(@RequestParam Integer targeterId){
        return ResponseResult.e(ResponseCode.OK, requestRecordService.ListRequestRecordIn(targeterId));
    }
}