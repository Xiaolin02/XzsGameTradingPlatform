package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import com.lin.controller.VO.ViewOrderVO;
import com.lin.controller.VO.ViewReleasedVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * @author 林炳昌
 * @desc 卖家模块
 * @date 2023年05月07日 16:35
 */
public interface SellerService {

    ResponseResult<NullData> release(String token, ReleaseDTO releaseDTO, MultipartFile[] files);

    ResponseResult<ArrayList<ViewReleasedVO>> view(String token);

    ResponseResult<NullData> newPrice(Integer commodityId, Integer newPrice, String token);

    ResponseResult<ArrayList<ViewOrderVO>> viewOrder(String token);

    ResponseResult<NullData> delOrder(String token, String orderId);

    ResponseResult deliverOrder(String token, String orderId);
}
