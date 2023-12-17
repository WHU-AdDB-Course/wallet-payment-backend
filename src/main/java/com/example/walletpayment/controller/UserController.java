package com.example.walletpayment.controller;

import com.example.walletpayment.common.req.UserLoginReq;
import com.example.walletpayment.common.vo.UserVO;
import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.User;
import com.example.walletpayment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "User")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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

    @ApiOperation("根据邮箱或电话获取用户")
    @GetMapping("/get-or")
    public ResponseResult getOr(@RequestParam(required = false) String phone, @RequestParam(required = false) String email){
        if (phone == null && email == null){
            return ResponseResult.error("电话和邮箱不能都为空");
        }
        return ResponseResult.e(ResponseCode.OK, userService.getByPhoneOrEmail(phone, email));
    }

    @ApiOperation("根据邮箱和电话获取用户")
    @GetMapping("/get-and")
    public ResponseResult getAnd(@RequestParam(required = true) String phone, @RequestParam(required = true) String email){
        if (phone == null || email == null){
            return ResponseResult.error("电话和邮箱都不能都为空");
        }
        return ResponseResult.e(ResponseCode.OK, userService.getByPhoneAndEmail(phone, email));
    }


}
