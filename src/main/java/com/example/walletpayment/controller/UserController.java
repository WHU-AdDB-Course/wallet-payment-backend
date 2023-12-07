package com.example.walletpayment.controller;

import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "User")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping ("/login")
    public ResponseResult login(@RequestParam String phone, @RequestParam String password){
        User user = userService.getById(phone);
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
        return ResponseResult.e(ResponseCode.OK, userService.getById(id));
    }

    @ApiOperation("获取所有用户")
    @GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.e(ResponseCode.OK, userService.list());
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


}
