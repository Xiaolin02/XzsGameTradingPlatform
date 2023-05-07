package com.lin.service;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;

/**
 * @author 林炳昌
 * @desc 卖家模块
 * @date 2023年05月07日 16:35
 */
public interface SellerService {

    public ResponseResult release(String token, ReleaseDTO releaseDTO);

}
