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

    @PostMapping("/addOrder/{commodityId}")
    public ResponseResult addOffer(@RequestHeader String token, @PathVariable Integer commodityId) {
        return buyerService.addOrder(token, commodityId);
    }

    @GetMapping("/getOrder")
    public ResponseResult getOrder(@RequestHeader String token) {
        return buyerService.getOrder(token);
    }

//    @PostMapping("/delOrder")
//    public ResponseResult delOrder() {
//
//    }

}
