package com.example.walletpayment.common.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BankAccountAddReq {
    @NotNull
    private Integer userId;

    @NotBlank
    private String bankNumber;

    private String bankName;

    private String accountType;
}
