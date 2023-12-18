package com.example.walletpayment.common.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SetDefaultAccountReq {
    @NotBlank
    private Integer userId;

    @NotBlank
    private Integer bankAccountId;
}
