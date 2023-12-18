package com.example.walletpayment.controller;

import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.service.RequestRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
    @ApiImplicitParam(name = "phoneAndEmails", value = "phoneAndEmails", allowMultiple = true, dataTypeClass = List.class, paramType = "query")
    public ResponseResult requestMoney(@RequestBody RequestRecord requestRecord, @RequestParam("phoneAndEmails") List<String> phoneAndEmails){
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

    @PostMapping("/verify")
    @ApiOperation("确认付钱")
    public ResponseResult verifyRequestRecord(@RequestBody RequestRecord requestRecord){
        if (requestRecordService.verifyRequestRecord(requestRecord)){
            return ResponseResult.e(ResponseCode.OK, Boolean.TRUE);
        }
        else {
            return ResponseResult.error("余额不足以支付");
        }
    }

}
