package com.example.walletpayment.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {

    @TableId(type = IdType.AUTO)
    private Integer commodityId;

    @NotBlank(message = "商品名称不能为空")
    private String commodityName;

    @NotNull(message = "商品价值不能为空")
    private Double value;
}
