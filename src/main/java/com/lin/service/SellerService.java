package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 林炳昌
 * @desc 卖家模块
 * @date 2023年05月07日 16:35
 */
public interface SellerService {

    ResponseResult release(String token, ReleaseDTO releaseDTO, MultipartFile[] files);

    ResponseResult view(String token);

    ResponseResult newPrice(Integer commodityId, Integer newPrice, String token);

    ResponseResult viewOrder(String token);
}
