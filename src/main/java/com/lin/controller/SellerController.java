package com.lin.controller;

import com.lin.common.CodeConstants;
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
//
//
//    @Autowired
//    SellerServiceImpl sellerService;
//
//    /**
//     * @desc 发布商品
//     * @date 2023/5/7 16:37
//     */
//    @PostMapping("/release")
//    public ResponseResult release(@RequestHeader String token, @RequestParam("files") MultipartFile[] files, ReleaseDTO releaseDTO) {
//        return sellerService.release(token, releaseDTO, files);
//    }
//
//    /**
//     * @desc 查看发布
//     * @date 2023/5/19 14:16
//     */
//    @GetMapping("/released/view")
//    public ResponseResult view(@RequestHeader String token) {
//        return sellerService.view(token);
//    }
//
//    /**
//     * @desc 修改价格
//     * @date 2023/5/28 20:02
//     */
//    @PostMapping("/commodity/price/{commodityId}/{newPrice}")
//    public ResponseResult newPrice(@PathVariable Integer newPrice, @RequestHeader String token, @PathVariable Integer commodityId) {
//        if(newPrice <= 0) {
//            return new ResponseResult(CodeConstants.CODE_PARAMETER_ERROR, "价格小于等于0");
//        }
//        return sellerService.newPrice(commodityId, newPrice, token);
//    }
//
//    /**
//     * @desc 查看订单
//     * @date 2023/5/28 21:08
//     */
//    @GetMapping("/order/view")
//    public ResponseResult viewOrder(@RequestHeader String token) {
//        // TODO 先null，不然我运行报错，写好了把这个删掉
//        return null;
//    }
//
//

}
