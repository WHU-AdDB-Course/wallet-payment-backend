package com.example.walletpayment.common.vo;/**
 * @Author: WuHao
 * @Date: 2023/12/18 21:10
 * @Description:
 */

import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.mybatis.entity.SendRecord;
import com.example.walletpayment.service.RequestRecordService;
import com.example.walletpayment.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * @author Howoow
 * @date 2023/12/18 21:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedQueryVO {

    private String senderName;

    private String receiverName;

    private Double value;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public AdvancedQueryVO(UserService userService, SendRecord sendRecord) {
        this.senderName = userService.getById(sendRecord.getSenderId()).getName();
        this.receiverName = userService.getById(sendRecord.getTargeterId()).getName();
        this.value = sendRecord.getValue();
        this.time = sendRecord.getCreateTime();
    }

    public AdvancedQueryVO(UserService userService, RequestRecord requestRecord) {
        this.senderName = userService.getById(requestRecord.getTargeterId()).getName();
        this.receiverName = userService.getById(requestRecord.getRequesterId()).getName();
        this.value = requestRecord.getValue();
        this.time = requestRecord.getFinishTime();
    }
}
