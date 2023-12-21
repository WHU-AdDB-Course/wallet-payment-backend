package com.example.walletpayment.common.vo;
/**
 * @author: WuHao
 * @date: 2023/12/21 12:52
 * @description: 
 */

import com.example.walletpayment.mybatis.entity.RequestRecord;
import com.example.walletpayment.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 
 * @author WuHao
 * @date 2023/12/21 12:52
 **/
@Data
@NoArgsConstructor
public class RequestRecordVO {
    private Integer requestRecordId;

    private Integer requesterId;

    private String requesterName;

    private Integer targeterId;

    private String targeterName;

    private Double value;

    private Integer status;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    public RequestRecordVO(UserService userService, RequestRecord requestRecord) {
        BeanUtils.copyProperties(requestRecord, this);
        this.requesterName = userService.getById(requesterId).getName();
        this.targeterName = userService.getById(targeterId).getName();
    }

}
