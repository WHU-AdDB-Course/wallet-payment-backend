package com.example.walletpayment.controller;

import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Api(tags = "User")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.e(ResponseCode.OK, userService.list());
    }
}
