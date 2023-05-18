package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import com.lin.service.impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 林炳昌
 * @desc 卖家模块
 * @date 2023年05月07日 16:26
 */
@RestController
public class SellerController {


    @Autowired
    SellerServiceImpl sellerService;

    /**
     * @desc 发布商品
     * @date 2023/5/7 16:37
     */
    @PostMapping("/release")
    public ResponseResult release(@RequestHeader String token, @RequestParam("files") MultipartFile[] files, ReleaseDTO releaseDTO) {
        return sellerService.release(token, releaseDTO, files);
    }

    @GetMapping("/released/view")
    public ResponseResult view(@RequestHeader String token) {
        return sellerService.view(token);
    }

}
