package com.example.walletpayment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.walletpayment.common.req.AdvancedQueryReq;
import com.example.walletpayment.common.req.SetDefaultAccountReq;
import com.example.walletpayment.common.req.UserLoginReq;
import com.example.walletpayment.common.vo.AdvancedQueryVO;
import com.example.walletpayment.common.vo.UserVO;
import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.BankAccount;
import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.service.BankAccountService;
import com.example.walletpayment.service.RequestRecordService;
import com.example.walletpayment.service.SendRecordService;
import com.example.walletpayment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Api(tags = "User")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private SendRecordService sendRecordService;

    @Autowired
    private RequestRecordService requestRecordService;


    public UserVO assembleVO(User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        List<String> email = List.of(user.getEmail().split(","));
        vo.setEmail(email);
        return vo;
    }

    @ApiOperation("用户登录")
    @PostMapping ("/login")
    public ResponseResult login(@RequestBody UserLoginReq req){
        String phone = req.getPhone();
        String password = req.getPassword();
        User user = userService.getByPhone(phone);
        if (user == null) {
            return ResponseResult.error("用户手机号不存在");
        }
        if (user.getPassword().equals(password)) {
            return ResponseResult.e(ResponseCode.OK, user);
        }
        return ResponseResult.error("用户密码不正确");
    }

    @ApiOperation("获取单个用户")
    @GetMapping("/get")
    public ResponseResult get(@RequestParam Integer id){
        return ResponseResult.e(ResponseCode.OK, assembleVO(userService.getById(id)));
    }

    @ApiOperation("获取所有用户")
    @GetMapping("/list")
    public ResponseResult list(){
        List<UserVO> res = new ArrayList<>();
        userService.list().forEach(user -> {
            res.add(assembleVO(user));
        });
        return ResponseResult.e(ResponseCode.OK, res);
    }

    @ApiOperation("新增用户")
    @PostMapping ("/add")
    public ResponseResult add(@RequestBody User user){
        if (user.getPhone() == null || user.getPhone().length() != 11) {
            return ResponseResult.error("手机号码格式不正确");
        }
        return ResponseResult.e(ResponseCode.OK, userService.save(user));
    }

    @ApiOperation("更新用户信息")
    @PostMapping ("/update")
    public ResponseResult get(@RequestBody User user){
        return ResponseResult.e(ResponseCode.OK, userService.updateById(user));
    }

    @ApiOperation("设置默认银行账户")
    @PostMapping("/setDefaultAccount")
    public ResponseResult setDefaultAccount(@RequestBody @Valid SetDefaultAccountReq req) {
        User user = userService.getById(req.getUserId());
        BankAccount bankAccount = bankAccountService.getById(req.getBankAccountId());
        try {
            user.setDefaultAccountId(bankAccount.getAccountId());
            userService.updateById(user);
        } catch (Exception e) {
            return ResponseResult.error("用户或银行账户不存在");
        }
        return ResponseResult.e(ResponseCode.OK);
    }

    @ApiOperation("获取高级查找字段")
    @GetMapping("/getAdvancedQueryFields")
    public ResponseResult getAdvancedQueryFields() {
        List<String> fields = Stream.of("SSN", "邮箱", "手机号", "交易类型").collect(Collectors.toList());
        return ResponseResult.e(ResponseCode.OK, fields);
    }

    private List<AdvancedQueryVO> getAdvancedQueryVOList(Integer searcherId, User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        List<AdvancedQueryVO> res = new ArrayList<>();
        QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
        sendRecordQueryWrapper.lambda()
                .eq(SendRecord::getSenderId, searcherId)
                .eq(SendRecord::getTargeterId, user.getUserId())
                .or()
                .eq(SendRecord::getSenderId, user.getUserId())
                .eq(SendRecord::getTargeterId, searcherId);
        List<SendRecord> sendRecordList = sendRecordService.list(sendRecordQueryWrapper);
        for (SendRecord sendRecord : sendRecordList) {
            res.add(new AdvancedQueryVO(userService, sendRecord));
        }
        QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
        requestRecordQueryWrapper.lambda()
                .eq(RequestRecord::getStatus, 1)
                .eq(RequestRecord::getRequesterId, searcherId)
                .eq(RequestRecord::getTargeterId, user.getUserId())
                .or()
                .eq(RequestRecord::getStatus, 1)
                .eq(RequestRecord::getRequesterId, user.getUserId())
                .eq(RequestRecord::getTargeterId, searcherId);
        List<RequestRecord> requestRecordList = requestRecordService.list(requestRecordQueryWrapper);
        for (RequestRecord requestRecord : requestRecordList) {
            res.add(new AdvancedQueryVO(userService, requestRecord));
        }
        return res;
    }

    private List<AdvancedQueryVO> getAdvancedQueryVOList(Integer searcherId, String type) {
        List<AdvancedQueryVO> res = new ArrayList<>();
        if (type.equals("支出")) {
            QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
            sendRecordQueryWrapper.lambda()
                    .eq(SendRecord::getTargeterId, searcherId);
            List<SendRecord> sendRecordList = sendRecordService.list(sendRecordQueryWrapper);
            for (SendRecord sendRecord : sendRecordList) {
                res.add(new AdvancedQueryVO(userService, sendRecord));
            }
            QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
            requestRecordQueryWrapper.lambda()
                    .eq(RequestRecord::getStatus, 1)
                    .eq(RequestRecord::getTargeterId, searcherId);
            List<RequestRecord> requestRecordList = requestRecordService.list(requestRecordQueryWrapper);
            for (RequestRecord requestRecord : requestRecordList) {
                res.add(new AdvancedQueryVO(userService, requestRecord));
            }
        }
        else if (type.equals("收入")) {
            QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
            sendRecordQueryWrapper.lambda()
                    .eq(SendRecord::getSendRecordId, searcherId);
            List<SendRecord> sendRecordList = sendRecordService.list(sendRecordQueryWrapper);
            for (SendRecord sendRecord : sendRecordList) {
                res.add(new AdvancedQueryVO(userService, sendRecord));
            }
            QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
            requestRecordQueryWrapper.lambda()
                    .eq(RequestRecord::getStatus, 1)
                    .eq(RequestRecord::getRequestRecordId, searcherId);
            List<RequestRecord> requestRecordList = requestRecordService.list(requestRecordQueryWrapper);
            for (RequestRecord requestRecord : requestRecordList) {
                res.add(new AdvancedQueryVO(userService, requestRecord));
            }
        }
        else {
            return new ArrayList<>();
        }
        return res;
    }

    @ApiOperation("高级查找")
    @PostMapping("/advancedQuery")
    public ResponseResult advancedQuery(@RequestBody @Valid AdvancedQueryReq req) {
        String field = req.getField();
        String value = req.getValue();
        List<AdvancedQueryVO> advancedQueryVOList = new ArrayList<>();
        switch (field) {
            case "SSN": {
                User user = userService.getBySsn(value);
                if (Objects.equals(req.getUserId(), user.getUserId())) {
                    return ResponseResult.error("信息与查询人重复！");
                }
                advancedQueryVOList = getAdvancedQueryVOList(req.getUserId(), user);
                break;
            }

            case "邮箱": {
                User user = userService.getByEmail(value);
                if (Objects.equals(req.getUserId(), user.getUserId())) {
                    return ResponseResult.error("信息与查询人重复！");
                }
                if (user != null) {
                    advancedQueryVOList = getAdvancedQueryVOList(req.getUserId(), user);
                }
                break;
            }

            case "手机号": {
                User user = userService.getByPhone(value);
                if (Objects.equals(req.getUserId(), user.getUserId())) {
                    return ResponseResult.error("信息与查询人重复！");
                }
                if (user != null) {
                    advancedQueryVOList = getAdvancedQueryVOList(req.getUserId(), user);
                }
                break;
            }

            case "交易类型": {
                if (value.equals("支出")) {
                    advancedQueryVOList = getAdvancedQueryVOList(req.getUserId(), value);
                }
                else {
                    advancedQueryVOList = getAdvancedQueryVOList(req.getUserId(), value);
                }
                break;
            }

            default: {
                return ResponseResult.error("高级查找字段不存在");
            }
        }

        return ResponseResult.e(ResponseCode.OK, advancedQueryVOList);
    }

    @ApiOperation("获取当月收入总和")
    @GetMapping("/income-amount")
    public ResponseResult incomeAmount(@RequestParam Integer userId){

        double amount = 0.0;

        QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
        sendRecordQueryWrapper.lambda().eq(SendRecord::getTargeterId, userId);
        List<SendRecord> sendRecords = sendRecordService.list(sendRecordQueryWrapper);
        for (SendRecord tmp : sendRecords){
            amount += tmp.getValue();
        }

        QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getRequesterId, userId);
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getStatus, 1);
        List<RequestRecord> requestRecords = requestRecordService.list(requestRecordQueryWrapper);
        for (RequestRecord tmp : requestRecords){
            amount += tmp.getValue();
        }

        return ResponseResult.e(ResponseCode.OK, amount);
    }

    @ApiOperation("获取当月支出总和")
    @GetMapping("/expenses-amount")
    public ResponseResult expenseAmount(@RequestParam Integer userId){

        double amount = 0.0;

        QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
        sendRecordQueryWrapper.lambda().eq(SendRecord::getSenderId, userId);
        List<SendRecord> sendRecords = sendRecordService.list(sendRecordQueryWrapper);
        for (SendRecord tmp : sendRecords){
            amount += tmp.getValue();
        }

        QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getTargeterId, userId);
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getStatus, 1);
        List<RequestRecord> requestRecords = requestRecordService.list(requestRecordQueryWrapper);
        for (RequestRecord tmp : requestRecords){
            amount += tmp.getValue();
        }

        return ResponseResult.e(ResponseCode.OK, amount);
    }

    @ApiOperation("获取当月最大收入交易")
    @GetMapping("/income-transaction")
    public ResponseResult incomeTransaction(@RequestParam Integer userId){

        QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
        sendRecordQueryWrapper.lambda().eq(SendRecord::getTargeterId, userId);
        List<SendRecord> sendRecords = sendRecordService.list(sendRecordQueryWrapper);
        SendRecord maxS = new SendRecord();
        maxS.setValue(-1.0);
        for (SendRecord tmp : sendRecords){
            maxS = (tmp.getValue() > maxS.getValue()) ? tmp : maxS;
        }

        QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getRequesterId, userId);
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getStatus, 1);
        List<RequestRecord> requestRecords = requestRecordService.list(requestRecordQueryWrapper);
        RequestRecord maxR = new RequestRecord();
        maxR.setValue(-1.0);
        for (RequestRecord tmp : requestRecords){
            maxR = (tmp.getValue() > maxR.getValue()) ? tmp : maxR;
        }

        if (maxS.getValue() > maxR.getValue()){
            AdvancedQueryVO advancedQueryVO = new AdvancedQueryVO(userService, maxS);
            return ResponseResult.e(ResponseCode.OK, advancedQueryVO);
        }
        else {
            AdvancedQueryVO advancedQueryVO = new AdvancedQueryVO(userService, maxR);
            return ResponseResult.e(ResponseCode.OK, advancedQueryVO);
        }

    }

    @ApiOperation("获取当月最大支出交易")
    @GetMapping("/expenses-transaction")
    public ResponseResult expensesTransaction(@RequestParam Integer userId){

        QueryWrapper<SendRecord> sendRecordQueryWrapper = new QueryWrapper<>();
        sendRecordQueryWrapper.lambda().eq(SendRecord::getSenderId, userId);
        List<SendRecord> sendRecords = sendRecordService.list(sendRecordQueryWrapper);
        SendRecord maxS = new SendRecord();
        maxS.setValue(-1.0);
        for (SendRecord tmp : sendRecords){
            maxS = (tmp.getValue() > maxS.getValue()) ? tmp : maxS;
        }

        QueryWrapper<RequestRecord> requestRecordQueryWrapper = new QueryWrapper<>();
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getTargeterId, userId);
        requestRecordQueryWrapper.lambda().eq(RequestRecord::getStatus, 1);
        List<RequestRecord> requestRecords = requestRecordService.list(requestRecordQueryWrapper);
        RequestRecord maxR = new RequestRecord();
        maxR.setValue(-1.0);
        for (RequestRecord tmp : requestRecords){
            maxR = (tmp.getValue() > maxR.getValue()) ? tmp : maxR;
        }

        if (maxS.getValue() > maxR.getValue()){
            AdvancedQueryVO advancedQueryVO = new AdvancedQueryVO(userService, maxS);
            return ResponseResult.e(ResponseCode.OK, advancedQueryVO);
        }
        else {
            AdvancedQueryVO advancedQueryVO = new AdvancedQueryVO(userService, maxR);
            return ResponseResult.e(ResponseCode.OK, advancedQueryVO);
        }

    }

}
