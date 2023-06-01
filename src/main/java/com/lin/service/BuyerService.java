package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.OfferDTO;
import com.lin.controller.VO.GetOrderVO;

import java.util.ArrayList;

/**
 * @author 林炳昌
 * @desc 买家Service
 * @date 2023年04月20日 21:54
 */
public interface BuyerService {

    ResponseResult<NullData> offer(String token, OfferDTO offerDTO);

    ResponseResult<NullData> addOrder(String token, Integer commodityId);

    ResponseResult<ArrayList<GetOrderVO>> getOrder(String token);

    ResponseResult<NullData> delOrder(String token, Integer orderId);

    ResponseResult<NullData> payOrder(String token, Integer orderId);
}
