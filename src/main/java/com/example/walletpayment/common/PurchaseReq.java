package com.example.walletpayment.common;

import lombok.Data;

@Data
public class PurchaseReq {
    private Integer userId;

    private Integer bankAccountId;

    private Integer commodityId;

    private Integer commodityNum;
}
