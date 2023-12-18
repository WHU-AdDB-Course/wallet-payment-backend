package com.example.walletpayment.common.req;

import lombok.Data;

@Data
public class BankAccountDeleteReq {
    Integer userId;

    Integer bankAccountId;
}
