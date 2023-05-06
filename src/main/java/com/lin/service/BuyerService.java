package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.OfferDTO;

/**
 * @author 林炳昌
 * @desc 买家Service
 * @date 2023年04月20日 21:54
 */
public interface BuyerService {

    ResponseResult offer(String token, OfferDTO offerDTO);

    ResponseResult addOrder(String token, Integer commodityId);

    ResponseResult getOrder(String token);

}
