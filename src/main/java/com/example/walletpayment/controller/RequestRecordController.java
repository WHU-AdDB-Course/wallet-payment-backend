package com.example.walletpayment.controller;

import com.example.walletpayment.common.req.AddRequestReq;
import com.example.walletpayment.common.vo.RequestRecordVO;
import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.service.RequestRecordService;
import com.example.walletpayment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "RequestRecord")
@RequestMapping("/requestRecord")
@RestController
@Slf4j
public class RequestRecordController {

    @Autowired
    private RequestRecordService requestRecordService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation("增加收钱任务")
    public ResponseResult requestMoney(@RequestBody AddRequestReq req){
        RequestRecord requestRecord = new RequestRecord();
        BeanUtils.copyProperties(req, requestRecord);
        List<String> phoneAndEmails = req.getPhoneAndEmails();
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
        List<RequestRecord> requestRecordList = requestRecordService.ListRequestRecordIn(targeterId);
        List<RequestRecordVO> res = new ArrayList<>();
        for (RequestRecord requestRecord : requestRecordList) {
            res.add(new RequestRecordVO(userService, requestRecord));
        }
        return ResponseResult.e(ResponseCode.OK, res);
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
