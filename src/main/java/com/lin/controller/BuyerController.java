package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.OfferDTO;
import com.lin.service.impl.BuyerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林炳昌
 * @desc 买家模块
 * @date 2023年04月20日 21:53
 */
@RestController
public class BuyerController {

    @Autowired
    BuyerServiceImpl buyerService;

    @PostMapping("/offer")
    public ResponseResult offer(@RequestHeader String token, @RequestBody OfferDTO offerDTO) {
        return buyerService.offer(token, offerDTO);
    }

}
