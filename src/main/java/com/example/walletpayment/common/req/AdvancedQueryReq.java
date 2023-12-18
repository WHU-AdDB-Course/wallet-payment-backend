package com.example.walletpayment.common.req;/**
 * @Author: WuHao
 * @Date: 2023/12/18 20:55
 * @Description:
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Howoow
 * @date 2023/12/18 20:55
 **/
@Data
public class AdvancedQueryReq {
    @ApiModelProperty("查询人id")
    @NotNull
    private Integer userId;
    @NotNull
    @ApiModelProperty("查找字段")
    private String field;
    @NotNull
    @ApiModelProperty("查找字段")
    private String value;
}
