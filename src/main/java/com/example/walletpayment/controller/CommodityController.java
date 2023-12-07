package com.example.walletpayment.controller;

import com.example.walletpayment.common.PurchaseReq;
import com.example.walletpayment.config.ResponseCode;
import com.example.walletpayment.config.ResponseResult;
import com.example.walletpayment.mybatis.entity.Commodity;
import com.example.walletpayment.mybatis.entity.Deal;
import com.example.walletpayment.service.BankAccountService;
import com.example.walletpayment.service.CommodityService;
import com.example.walletpayment.service.DealService;
import com.example.walletpayment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@Api(tags = "Commodity")
@RequestMapping("commodity")
@RestController
@Slf4j
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private DealService dealService;

    @ApiOperation("获取单个商品")
    @GetMapping("/get-commodity")
    public ResponseResult getCommodity(@RequestParam Integer commodityId){
        return ResponseResult.e(ResponseCode.OK, commodityService.getById(commodityId));
    }

    @ApiOperation("增加商品")
    @PostMapping("/add-commodity")
    public ResponseResult addCommodity(@RequestBody Commodity commodity){
        if (commodity.getValue() < 0) {
            return ResponseResult.error("商品价值必须为正数");
        }
        boolean res =  commodityService.save(commodity);
        return res ? ResponseResult.e(ResponseCode.OK, commodity) : ResponseResult.e(ResponseCode.FAIL);
    }

    @ApiOperation("获取所有商品")
    @GetMapping("/list-commodity")
    public ResponseResult listCommodity(){
        return ResponseResult.e(ResponseCode.OK, commodityService.list());
    }

    @ApiOperation("删除单个商品")
    @GetMapping("/delete-commodity")
    public ResponseResult deleteCommodity(@RequestParam Integer commodityId){
        boolean res =  commodityService.removeById(commodityId);
        return res ? ResponseResult.e(ResponseCode.OK) : ResponseResult.e(ResponseCode.FAIL);
    }

    @ApiOperation("购买商品")
    @PostMapping("/purchase")
    public ResponseResult purchase(@RequestBody PurchaseReq purchaseReq){
        Deal deal = new Deal();
        Commodity commodity = commodityService.getById(purchaseReq.getCommodityId());
        BeanUtils.copyProperties(purchaseReq, deal);
        if (commodity == null) {
            return ResponseResult.error("商品id不存在");
        }
        BigDecimal sumPrice = BigDecimal.valueOf(commodity.getValue() * purchaseReq.getCommodityNum());
        deal.setSumPrice(sumPrice.doubleValue());
        deal.setCreateTime(new Date());
        boolean res =  dealService.save(deal);
        if (res) {
            return bankAccountService.purchase(purchaseReq.getBankAccountId(), sumPrice) ? ResponseResult.e(ResponseCode.OK, deal) : ResponseResult.e(ResponseCode.FAIL);
        }
        return ResponseResult.error("订单保存失败");
    }

}
