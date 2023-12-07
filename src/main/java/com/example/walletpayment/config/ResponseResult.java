package com.example.walletpayment.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseResult<T> {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("返回处理消息")
    private String message = "操作成功";
    @ApiModelProperty("返回代码")
    private Integer code = 200;
    @ApiModelProperty("返回数据对象")
    private T data;
    @ApiModelProperty("时间戳")
    private long timestamp = System.currentTimeMillis();

    public ResponseResult() {}

    public static synchronized <T> ResponseResult<T> ok(String message) {
        ResponseResult<T> res = new ResponseResult();
        res.setCode(ResponseCode.OK.code);
        res.setMessage(message);
        res.setData(null);
        return res;
    }

    public static synchronized <T> ResponseResult<T> error(String message) {
        ResponseResult<T> res = new ResponseResult();
        res.setCode(ResponseCode.FAIL.code);
        res.setMessage(message);
        res.setData(null);
        return res;
    }

    public static synchronized <T> ResponseResult<T> e(ResponseCode statusEnum, T data) {
        ResponseResult<T> res = new ResponseResult();
        res.setCode(statusEnum.code);
        res.setMessage(statusEnum.msg);
        res.setData(data);
        return res;
    }

    public static synchronized <T> ResponseResult<T> e(ResponseCode statusEnum) {
        return e(statusEnum, null);
    }
}
