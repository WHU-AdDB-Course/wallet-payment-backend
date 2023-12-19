package com.example.walletpayment.common.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author: Maple
 * Date: 2023/12/19 17:25
 */
@Data
public class AddRequestReq {
    private Integer requestRecordId;

    private Integer requesterId;

    private Integer targeterId;

    private Double value;

    private Integer status;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    private List<String> phoneAndEmails;
}
