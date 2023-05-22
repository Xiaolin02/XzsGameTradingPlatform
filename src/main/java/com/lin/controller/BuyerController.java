package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.OfferDTO;
import com.lin.service.impl.BuyerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 林炳昌
 * @desc 买家模块
 * @date 2023年04月20日 21:53
 */
@RestController
public class BuyerController {

    @Autowired
    BuyerServiceImpl buyerService;

    /**
     * @desc 出价
     * @date 2023/5/5 20:00
     */
    @PostMapping("/offer")
    public ResponseResult offer(@RequestHeader String token, @RequestBody OfferDTO offerDTO) {
        return buyerService.offer(token, offerDTO);
    }

    /**
     * @desc 提交订单
     * @date 2023/5/7 15:22
     */
    @PostMapping("/order/add/{commodityId}")
    public ResponseResult addOffer(@RequestHeader String token, @PathVariable Integer commodityId) {
        return buyerService.addOrder(token, commodityId);
    }

    /**
     * @desc 查看订单
     * @date 2023/5/7 15:22
     */
    @GetMapping("/order/view")
    public ResponseResult getOrder(@RequestHeader String token) {
        return buyerService.getOrder(token);
    }

    /**
     * @desc 取消订单(订单状态置为 2)
     * @date 2023/5/7 15:22
     */
    @PostMapping("/order/del/{orderId}")
    public ResponseResult delOrder(@RequestHeader String token, @PathVariable Integer orderId) {
        return buyerService.delOrder(token, orderId);
    }

    /**
     * @desc 付款(订单状态置为 1)
     * @date 2023/5/7 15:23
     */
    @PostMapping("/order/pay/{orderId}")
    public ResponseResult payOrder(@RequestHeader String token, @PathVariable Integer orderId) {
        return buyerService.payOrder(token, orderId);
    }

}
